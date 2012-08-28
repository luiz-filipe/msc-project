package com.luizabrahao.msc.model.env;

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
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.env.WarningStimulusType;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.ants.test.TestUtil;

public class WarningStimulusTest {
	private static final Logger logger = LoggerFactory.getLogger(WarningStimulusTest.class);
	
	private final int nLines = 500;
	private final int nColumns = 100;
	private final int maximumNumberOfThreads = 100;
	private final long secondsToRun = 20;
	private final long secondsToRender = 20;
	
	@Test
	public void warningTest() throws InterruptedException {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(maximumNumberOfThreads);
		List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();

		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		TestUtil.setIntensity(0, nLines, 0, nColumns, 0.005, grid);

		final AntNestAgent nest = new AntNestAgent("nest", grid[10][Integer.valueOf(nColumns / 2)]);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(70, "worker", nest.getCurrentNode());

		for (int i = 0; i < nColumns; i++) {
			grid[nLines - 50][i].getCommunicationStimulus(WarningStimulusType.TYPE).setIntensity(0.8);	
		}
		
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
		
		renderers.add(new ExploredSpaceRenderer(grid, "target/warning-stimulus - space explored.png", nColumns, nLines));
		renderers.add(new PheromoneRenderer(grid, "target/warning-stimulus - forage pheoromone.png", nColumns, nLines, ForageStimulusType.TYPE));
		renderers.add(new PheromoneRenderer(grid, "target/warning-stimulus - warning pheromone.png", nColumns, nLines, WarningStimulusType.TYPE));
				
		List<Future<Void>> renderersFutures = executor.invokeAll(renderers, secondsToRender, TimeUnit.SECONDS);
		
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
