package com.luizabrahao.msc.ants.render;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.BasicTaskAgentType;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.env.Node;

public class RenderAgentFactoryTest {
	private class MockTaskAgent extends TaskAgent {
		public MockTaskAgent(String id, TaskAgentType agentType, Node currentNode) {
			super(id, agentType, currentNode);
		}

		@Override
		public void run() {
			this.getTaskList().get(0).execute(this);
		}
	}
	
	
	@Test @SuppressWarnings("unused")
	public void exploredSpaceTest() throws InterruptedException {
		int nLines = 250;
		int nColumns = 200;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		
		TaskAgent a01 = new MockTaskAgent("a-01", BasicTaskAgentType.getIntance(), grid[0][0]);
		TaskAgent a02 = new MockTaskAgent("a-02", BasicTaskAgentType.getIntance(), grid[0][10]);
		TaskAgent a03 = new MockTaskAgent("a-03", BasicTaskAgentType.getIntance(), grid[0][20]);
		TaskAgent a04 = new MockTaskAgent("a-04", BasicTaskAgentType.getIntance(), grid[0][30]);
		TaskAgent a05 = new MockTaskAgent("a-05", BasicTaskAgentType.getIntance(), grid[0][40]);
		TaskAgent a06 = new MockTaskAgent("a-06", BasicTaskAgentType.getIntance(), grid[0][50]);
		TaskAgent a07 = new MockTaskAgent("a-07", BasicTaskAgentType.getIntance(), grid[0][60]);
		TaskAgent a08 = new MockTaskAgent("a-08", BasicTaskAgentType.getIntance(), grid[0][70]);
		TaskAgent a09 = new MockTaskAgent("a-09", BasicTaskAgentType.getIntance(), grid[0][80]);
		TaskAgent a10 = new MockTaskAgent("a-10", BasicTaskAgentType.getIntance(), grid[0][90]);
		
		
		ExecutorService executor = Executors.newFixedThreadPool(15);
		executor.execute(a01);
		executor.execute(a02);
		executor.execute(a03);
		executor.execute(a04);
		executor.execute(a05);
		executor.execute(a06);
		executor.execute(a07);
		executor.execute(a08);
		executor.execute(a09);
		executor.execute(a10);
		
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(RenderAgentFactory.getExploredSpaceRenderer("target/explored-space.png", grid, nLines, nColumns)));
		
		final List<Future<Object>> futures = executor.invokeAll(tasks);
	}
	
	
	@Test @SuppressWarnings("unused")
	public void populationTest() throws InterruptedException {
		int nLines = 250;
		int nColumns = 200;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		
		TaskAgent a01 = new MockTaskAgent("a-01", BasicTaskAgentType.getIntance(), grid[0][0]);
		TaskAgent a02 = new MockTaskAgent("a-02", BasicTaskAgentType.getIntance(), grid[0][10]);
		TaskAgent a03 = new MockTaskAgent("a-03", BasicTaskAgentType.getIntance(), grid[0][20]);
		TaskAgent a04 = new MockTaskAgent("a-04", BasicTaskAgentType.getIntance(), grid[0][30]);
		TaskAgent a05 = new MockTaskAgent("a-05", BasicTaskAgentType.getIntance(), grid[0][40]);
		TaskAgent a06 = new MockTaskAgent("a-06", BasicTaskAgentType.getIntance(), grid[0][50]);
		TaskAgent a07 = new MockTaskAgent("a-07", BasicTaskAgentType.getIntance(), grid[0][60]);
		TaskAgent a08 = new MockTaskAgent("a-08", BasicTaskAgentType.getIntance(), grid[0][70]);
		TaskAgent a09 = new MockTaskAgent("a-09", BasicTaskAgentType.getIntance(), grid[0][80]);
		TaskAgent a10 = new MockTaskAgent("a-10", BasicTaskAgentType.getIntance(), grid[0][90]);
		
		ExecutorService executor = Executors.newFixedThreadPool(15);
		executor.execute(a01);
		executor.execute(a02);
		executor.execute(a03);
		executor.execute(a04);
		executor.execute(a05);
		executor.execute(a06);
		executor.execute(a07);
		executor.execute(a08);
		executor.execute(a09);
		executor.execute(a10);
		
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(RenderAgentFactory.getPopulationRenderer("target/population.png", grid, nLines, nColumns)));
		
		ExecutorService renderExecutor = Executors.newFixedThreadPool(1);
		final List<Future<Object>> futures = renderExecutor.invokeAll(tasks);
	}
	
	@Test @SuppressWarnings("unused")
	public void pheromoneTest() throws InterruptedException {
		int nLines = 250;
		int nColumns = 200;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
					
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(RenderAgentFactory.getPheromoneRenderer("target/pheromone-low.png", grid, nLines, nColumns)));

		ExecutorService renderExecutor = Executors.newFixedThreadPool(1);
		List<Future<Object>> futures = renderExecutor.invokeAll(tasks);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				(grid[l][c]).setPheromoneIntensity(1);
			}
		}
		
		tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(RenderAgentFactory.getPheromoneRenderer("target/pheromone-high.png", grid, nLines, nColumns)));
		futures = renderExecutor.invokeAll(tasks);
	}
}
