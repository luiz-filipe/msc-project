package com.luizabrahao.msc.simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.AntAgentFactory;
import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.ants.agent.StaticPheromoneUpdaterAgent;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.env.WarningStimulusType;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.ants.task.FindAndHideInNest;
import com.luizabrahao.msc.ants.test.TestUtil;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.sim.util.CallableAdapter;

public class ReactionToWarning {
	private static final Logger logger = LoggerFactory.getLogger(ReactionToWarning.class);
	
	private final int nLines = 500;
	private final int nColumns = 500;
	private final int maximumNumberOfThreads = 150;
	private final long secondsToRun = 30;
	private final long secondsToRender = 10;
	private final double initialConcentration = 0.01;	
	private final long secondsToRunDecayAgent = 3;
	
	@Test
	public void run() throws InterruptedException {
		this.experiment(1);
	}
	
	@SuppressWarnings("unused")
	public void experiment(int executionNumber) throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		final List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, initialConcentration, grid);
		
		final AntNestAgent nest = new AntNestAgent("nest", grid[0][Integer.valueOf(nColumns / 2)]);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfTactileWorkers(100, "t-worker", nest.getCurrentNode());
		
		final StaticPheromoneUpdaterAgent pheromoneUpdater1 = new StaticPheromoneUpdaterAgent("pheromone-updater-1", grid[0][0], nLines / 2);
		final StaticPheromoneUpdaterAgent pheromoneUpdater2 = new StaticPheromoneUpdaterAgent("pheromone-updater-2", grid[250][0], nLines / 2);
		final ScheduledFuture<?> updater1Future = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater1), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		final ScheduledFuture<?> upderer2Future = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater2), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> warningPlacer = executor.schedule(new Runnable() {
			@Override
			public void run() {
				TestUtil.setIntensity(WarningStimulusType.TYPE, nLines - 100, nLines - 95, 150, nColumns - 150, 0.8, grid);
			}
			
		}, secondsToRun / 4, TimeUnit.SECONDS);
		
//		final ScheduledFuture<?> agentsCounter = executor.scheduleWithFixedDelay(new Runnable() {
//			@Override
//			public void run() {
//				int nAgentsInNest = 0;
//				
//				for (Agent agent : agents) {
//					if (agent.getCurrentNode() == nest.getCurrentNode()) {
//						nAgentsInNest++;
//					}
//				}
//				
//				logger.info("Number of agents in nest; {}", nAgentsInNest);
//			}
//		}, 500, 500, TimeUnit.MILLISECONDS);
		
		final ScheduledFuture<?> taskCounter = executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				int nAgentsIHidding = 0;
				
				for (Agent agent : agents) {
					if (((TaskAgent) agent).getCurrentTask().getName().equals(FindAndHideInNest.NAME)) {
						nAgentsIHidding++;
					}
				}
				
				logger.info("Number of agents executing hidding task; {}", nAgentsIHidding);
			}
		}, 500, 500, TimeUnit.MILLISECONDS);
		
		
		
		final List<Future<Void>> agentsFutures = executor.invokeAll(agents, secondsToRun, TimeUnit.SECONDS);
		
		for (Future<Void> future : agentsFutures) {
			try {
				future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (CancellationException e) {
				// nothing to do...
			}
			
			future.cancel(true);
		}
		
		renderers.add(new PheromoneRenderer(grid, "target/reaction - setup.png", nColumns, nLines, WarningStimulusType.TYPE));
		renderers.add(new PheromoneRenderer(grid, "target/reaction - hide-only - " + executionNumber + " - forage.png", nColumns, nLines, ForageStimulusType.TYPE));
		renderers.add(new PheromoneRenderer(grid, "target/reaction - hide-only - " + executionNumber + " - warning.png", nColumns, nLines, WarningStimulusType.TYPE));
		
		final List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
		
		for (Future<Void> future : renderersFutures) {
			try {
				future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (CancellationException e) {
				// nothing to do here.
			}
			
			future.cancel(true);
		}

	}
}
