package com.luizabrahao.msc.ants.agent;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.Node;

public class AntAgentFactoryTest {
	@Test
	public void producePopulationOneNode() {
		final Node n = new BasicNode("n");
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(10, "a", n);
		
		assertTrue(agents.size() == 10);
		assertTrue(agents.get(9).getId().equals("a-9"));
		assertTrue(agents.get(5).getCurrentNode() == n);
	}
	
	@Test
	public void produceSprangPopulation() {
		final Node[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(6, 9);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(18, "a", grid, 2, 0, 3, 6);
		
		assertTrue(agents.size() == 18);
		assertTrue(agents.get(9).getId().equals("a-9"));
		
		assertTrue(grid[2][0].getAgents().size() == 6);
		assertTrue(grid[2][4].getAgents().size() == 6);
		assertTrue(grid[2][8].getAgents().size() == 6);
	}
	
	@SuppressWarnings("unused")
	@Test (expected = RuntimeException.class)
	public void produceSprangPopulationWithNoEnoughColumns() {
		Node[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(6, 9);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(18, "a", grid, 2, 0, 4, 6);		
	}
}
