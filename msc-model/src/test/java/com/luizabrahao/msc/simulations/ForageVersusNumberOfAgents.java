package com.luizabrahao.msc.simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.AntAgentFactory;
import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.ants.test.TestUtil;

public class ForageVersusNumberOfAgents {
	private static final Logger logger = LoggerFactory.getLogger(ForageVersusNumberOfAgents.class);
	
	private final int nLines = 500;
	private final int nColumns = 500;
	private final int maximumNumberOfThreads = 210;
	private final long secondsToRun = 30;
	private final long secondsToRender = 10;
	private final int nAgents = 200;
	
	@Test
	public void simulation() throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, 0.005, grid);
		
		final AntNestAgent nest = new AntNestAgent("nest", grid[0][Integer.valueOf(nColumns / 2)]);
		final List<FoodSourceAgent> foodSources = AntEnvironmentFactory.placeRowOfFoodSources(grid[nLines - 100][Integer.valueOf(nColumns / 2) - 100], 200, 100);	
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(nAgents, "worker", grid[0][Integer.valueOf(nColumns / 2)]);
		
		List<Future<Void>> agentsFutures = executor.invokeAll(agents, secondsToRun, TimeUnit.SECONDS);
		
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
		
		renderers.add(new ExploredSpaceRenderer(grid, "target/forageVsNumberOfAgents - explored - " + nAgents + " agents.png", nColumns, nLines));
		renderers.add(new PheromoneRenderer(grid, "target/forageVsNumberOfAgents - forage - " + nAgents + " agents.png", nColumns, nLines, ForageStimulusType.TYPE));
				
		List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
		
		for (Future<Void> future : renderersFutures) {
			try {
				future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			future.cancel(true);
		}
		
		logger.info("A total of '{}' food was collected.", nest.getAmountOfFoodHeld());
	}
}
