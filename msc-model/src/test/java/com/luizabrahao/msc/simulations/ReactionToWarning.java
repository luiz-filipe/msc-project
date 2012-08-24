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
import com.luizabrahao.msc.ants.agent.WorkerAntType;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.env.WarningStimulusType;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
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
		for (int i = 0; i < 1; i++) {
			this.experiment(i);
			logger.info("---------");
		}
	}
	
	@SuppressWarnings("unused")
	public void experiment(int executionNumber) throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		final List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, initialConcentration, grid);
		TestUtil.setIntensity(0, nLines - 150, nColumns / 2, nColumns / 2 + 1, 1.0, grid);
		
		final AntNestAgent nest = new AntNestAgent("nest", grid[0][Integer.valueOf(nColumns / 2)]);
		final List<FoodSourceAgent> foodSources = AntEnvironmentFactory.placeRowOfFoodSources(grid[nLines - 150][nColumns / 2 - 1], 2, 100);
		
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(50, "worker", nest.getCurrentNode());
		
		final StaticPheromoneUpdaterAgent pheromoneUpdater1 = new StaticPheromoneUpdaterAgent("pheromone-updater-1", grid[0][0], nLines / 2);
		final StaticPheromoneUpdaterAgent pheromoneUpdater2 = new StaticPheromoneUpdaterAgent("pheromone-updater-2", grid[250][0], nLines / 2);
		final ScheduledFuture<?> updater1Future = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater1), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		final ScheduledFuture<?> upderer2Future = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater2), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> warningPlacer = executor.schedule(new Runnable() {
			@Override
			public void run() {
				TestUtil.setIntensity(WarningStimulusType.TYPE, nLines - 160, nLines - 100, nColumns / 2 - 10, nColumns / 2 + 10, 1, grid);
				logger.info("Warning stimulus added placed");
			}
			
		}, secondsToRun / 4, TimeUnit.SECONDS);
		
		
		final ScheduledFuture<?> taskCounter = executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				int nAgentsIHidding = 0;

				for (Agent agent : agents) {
					if (((TaskAgent) agent).getCurrentTask() != null) {
						if (((TaskAgent) agent).getCurrentTask().getName().equals(FindAndHideInNest.NAME)) {
							nAgentsIHidding++;
						}
					}
				}
				
				logger.warn("Number of agents executing hidding task; {}", nAgentsIHidding);
			}
		}, 500, 500, TimeUnit.MILLISECONDS);
		
		
		final ScheduledFuture<?> agents01 = executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					executor.submit(agents.get(i));
				}
				logger.debug("Deplyment 1");
			}
		}, 3, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> agents02 = executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (int i = 10; i < 20; i++) {
					executor.submit(agents.get(i));
				}
				logger.debug("Deplyment 2");
			}
		}, 6, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> agents03 = executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (int i = 20; i < 30; i++) {
					executor.submit(agents.get(i));
				}
				logger.debug("Deplyment 3");
			}
		}, 9, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> agents04 = executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (int i = 30; i < 40; i++) {
					executor.submit(agents.get(i));
				}
				logger.debug("Deplyment 4");
			}
		}, 12, TimeUnit.SECONDS);
		
		final ScheduledFuture<?> agents05 = executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (int i = 40; i < 50; i++) {
					executor.submit(agents.get(i));
				}
				logger.debug("Deplyment 5");
			}
		}, 15, TimeUnit.SECONDS);

		List<AntAgent> stopperList = new ArrayList<AntAgent>();
		stopperList.add(new AntAgent("stopper-worker", WorkerAntType.TYPE, nest.getCurrentNode(), false));
		
		executor.invokeAll(stopperList, secondsToRun, TimeUnit.SECONDS);
		
//		executor.invokeAll(agents, secondsToRun, TimeUnit.SECONDS);
		
		renderers.add(new ExploredSpaceRenderer(grid, "target/reaction - space.png", nColumns, nLines));
		renderers.add(new PheromoneRenderer(grid, "target/reaction - setup.png", nColumns, nLines, WarningStimulusType.TYPE));
		renderers.add(new PheromoneRenderer(grid, "target/reaction - hide-only - " + executionNumber + " - forage.png", nColumns, nLines, ForageStimulusType.TYPE));
//		renderers.add(new PheromoneRenderer(grid, "target/reaction - hide-only - " + executionNumber + " - warning.png", nColumns, nLines, WarningStimulusType.TYPE));
//		
		final List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
//		
//		for (Future<Void> future : renderersFutures) {
//			try {
//				future.get();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			} catch (CancellationException e) {
//				// nothing to do here.
//			}
//			
//			future.cancel(true);
//		}

	}
}
