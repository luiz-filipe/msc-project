package com.luizabrahao.msc.simulations;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.ants.agent.WorkerType;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.NodeHistoryRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.ants.test.TestUtil;

public class PathSimulation {

	@Test
	public void preExistingPath() throws InterruptedException {
		int nColumns = 500;
		int nLines = 500;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		final AntNestAgent nest = new AntNestAgent("nest-01", grid[0][Integer.valueOf(nColumns / 2)]);
		final AntAgent a = new AntAgent("a-01", WorkerType.TYPE,  grid[0][Integer.valueOf(nColumns / 2)], true);
		
		TestUtil.setIntensity(0, nLines, 0, nColumns, 0.01, grid);
		
		nest.produceWorkers("worker", 50);
		nest.addAgent(a);
		
		final Future<Void> pheromoneRenderFuture = executor.schedule(new PheromoneRenderer(grid, "target/simulation-path-pheromone.png", nColumns, nLines, ForageStimulusType.TYPE),  10, TimeUnit.SECONDS);
		final Future<Void> exploredSpaceRenderFuture = executor.schedule(new ExploredSpaceRenderer(grid, "target/simulation-path-space explored.png", nColumns, nLines),  10, TimeUnit.SECONDS);
		final Future<Void> agent01NodeHistoryFuture = executor.schedule(new NodeHistoryRenderer(a, "target/simulation-path-history a-01.png", nColumns, nLines),  10, TimeUnit.SECONDS);
		
		try {
			pheromoneRenderFuture.get();
			exploredSpaceRenderFuture.get();
			agent01NodeHistoryFuture.get();
			
		} catch (ExecutionException ex) {
		    ex.getCause().printStackTrace();
		}
	}
}
