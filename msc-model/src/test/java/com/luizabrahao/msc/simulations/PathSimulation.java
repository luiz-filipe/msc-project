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

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.AntAgentFactory;
import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.ants.agent.WorkerAntType;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.ants.test.TestUtil;

public class PathSimulation {
	private final int nLines = 500;
	private final int nColumns = 500;
	private final int maximumNumberOfThreads = 60;
	private final long secondsToRun = 10;
	private final long secondsToRender = 10;
	private final double initialConcentration = 0.001;

	@Test @SuppressWarnings("unused")
	public void cancelTest() throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, initialConcentration, grid);
		
		
		final AntNestAgent nest = new AntNestAgent("nest", grid[0][Integer.valueOf(nColumns / 2)]);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(50, "worker", grid[0][Integer.valueOf(nColumns / 2)]);
		
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
		
		renderers.add(new ExploredSpaceRenderer(grid, "target/path - space explored - ic=" + initialConcentration + ", as=" + WorkerAntType.TYPE.getStimulusIncrement(ForageStimulusType.TYPE.getName()) + ".png", nColumns, nLines));
		renderers.add(new PheromoneRenderer(grid, "target/initial - ic=" + initialConcentration + ", as=" + WorkerAntType.TYPE.getStimulusIncrement(ForageStimulusType.TYPE.getName()) + ".png", nColumns, nLines, ForageStimulusType.TYPE));
				
		List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
		
		for (Future<Void> future : renderersFutures) {
			try {
				future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			future.cancel(true);
		}

	}
}

