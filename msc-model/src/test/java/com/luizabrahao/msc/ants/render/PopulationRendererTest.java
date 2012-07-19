package com.luizabrahao.msc.ants.render;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.agent.BasicTaskAgentType;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.env.EnvironmentFactory;
import com.luizabrahao.msc.model.env.Node;

public class PopulationRendererTest {
	private class MockTaskAgent extends TaskAgent {
		public MockTaskAgent(String id, TaskAgentType agentType, Node currentNode) {
			super(id, agentType, currentNode, false);
		}

		@Override
		public Void call() throws Exception {
			this.getTaskList().get(0).execute(this);
			return null;
		}
	}
	
	@Test @SuppressWarnings("unused")
	public void renderStaticPopulation() throws InterruptedException {
		final int nLines = 5;
		final int nColumns = 5;
		final Node[][] grid = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		TaskAgent a01 = new MockTaskAgent("a-01", BasicTaskAgentType.TYPE, grid[0][0]);
		TaskAgent a02 = new MockTaskAgent("a-02", BasicTaskAgentType.TYPE, grid[0][2]);
		TaskAgent a03 = new MockTaskAgent("a-03", BasicTaskAgentType.TYPE, grid[0][4]);
		TaskAgent a04 = new MockTaskAgent("a-04", BasicTaskAgentType.TYPE, grid[4][0]);
		TaskAgent a05 = new MockTaskAgent("a-05", BasicTaskAgentType.TYPE, grid[4][2]);
		TaskAgent a06 = new MockTaskAgent("a-06", BasicTaskAgentType.TYPE, grid[4][4]);
		
		List<Agent> agents = new ArrayList<Agent>();
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		
		agents.add(a01);
		agents.add(a02);
		agents.add(a03);
		agents.add(a04);
		agents.add(a05);
		agents.add(a06);
		
		tasks.add(new PheromoneRenderer(grid, "target/population-static.png", nColumns, nLines, ForageStimulusType.TYPE));
		
		final List<Future<Void>> futures = executor.invokeAll(tasks);
	}
	
	@Test @SuppressWarnings("unused")
	public void renderPopulation() throws InterruptedException {
		final int nLines = 250;
		final int nColumns = 200;
		final Node[][] grid = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		TaskAgent a01 = new MockTaskAgent("a-01", BasicTaskAgentType.TYPE, grid[0][0]);
		TaskAgent a02 = new MockTaskAgent("a-02", BasicTaskAgentType.TYPE, grid[0][10]);
		TaskAgent a03 = new MockTaskAgent("a-03", BasicTaskAgentType.TYPE, grid[0][20]);
		TaskAgent a04 = new MockTaskAgent("a-04", BasicTaskAgentType.TYPE, grid[0][30]);
		TaskAgent a05 = new MockTaskAgent("a-05", BasicTaskAgentType.TYPE, grid[0][40]);
		
		List<Agent> agents = new ArrayList<Agent>();
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		
		agents.add(a01);
		agents.add(a02);
		agents.add(a03);
		agents.add(a04);
		agents.add(a05);
		
		tasks.add(a01);
		tasks.add(a02);
		tasks.add(a03);
		tasks.add(a04);
		tasks.add(a05);
		
		executor.schedule(new PheromoneRenderer(grid, "target/population-dynamic.png", nColumns, nLines, ForageStimulusType.TYPE), 4, TimeUnit.SECONDS);
		
		final List<Future<Void>> futures = executor.invokeAll(tasks, 6, TimeUnit.SECONDS);
	}
}
