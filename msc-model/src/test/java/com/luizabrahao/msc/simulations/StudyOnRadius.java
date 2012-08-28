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
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.test.TestUtil;
import com.luizabrahao.msc.sim.util.CallableAdapter;

public class StudyOnRadius {
	private static final Logger logger = LoggerFactory.getLogger(StudyOnRadius.class);
	
	private final int nLines = 500;
	private final int nColumns = 500;
	private final int maximumNumberOfThreads = 60;
	private final long secondsToRun = 30;
	private final long secondsToRender = 10;
	private final double initialConcentration = 0.01;	
	private final long secondsToRunDecayAgent = 3;
	
	@Test
	public void run() throws InterruptedException {
		for (int i = 0; i < 25; i++) {
			this.executeExperiment(i);
		}
	}
	
	@SuppressWarnings("unused")
	public void executeExperiment(int executionNumber) throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, initialConcentration, grid);
		
		final AntNestAgent nest = new AntNestAgent("nest", grid[0][Integer.valueOf(nColumns / 2)]);
		final List<FoodSourceAgent> foodSources = AntEnvironmentFactory.placeRowOfFoodSources(grid[nLines - 100][Integer.valueOf(nColumns / 2) - 100], 200, 30);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(50, "worker", grid[0][Integer.valueOf(nColumns / 2)]);
		
		final StaticPheromoneUpdaterAgent pheromoneUpdater1 = new StaticPheromoneUpdaterAgent("pheromone-updater-1", grid[0][0], nLines / 2);
		final StaticPheromoneUpdaterAgent pheromoneUpdater2 = new StaticPheromoneUpdaterAgent("pheromone-updater-2", grid[250][0], nLines / 2);
		
		ScheduledFuture<?> rf1 = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater1), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		ScheduledFuture<?> rf2 = executor.scheduleWithFixedDelay(CallableAdapter.runnable(pheromoneUpdater2), secondsToRunDecayAgent, secondsToRunDecayAgent, TimeUnit.SECONDS);
		
		List<Future<Void>> agentsFutures = executor.invokeAll(agents, secondsToRun, TimeUnit.SECONDS);
		
		rf1.cancel(true);
		rf2.cancel(true);
		
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
		
//		renderers.add(new ExploredSpaceRenderer(grid, "target/studyOnRadius - space - radius = " + ForageStimulusType.TYPE.getRadius() + ".png", nColumns, nLines));
//		renderers.add(new PheromoneRenderer(grid, "target/studyradius - " + ForageStimulusType.TYPE.getRadius() + " - " + executionNumber + ".png", nColumns, nLines, ForageStimulusType.TYPE));
//				
//		List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
//		
//		for (Future<Void> future : renderersFutures) {
//			try {
//				future.get();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//			
//			future.cancel(true);
//		}
		
		logger.info("Amount of food collected; {}", nest.getAmountOfFoodHeld());
	}
}
