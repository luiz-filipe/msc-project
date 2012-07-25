package com.luizabrahao.msc.ants.agent;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.Node;

public class AntAgentFactoryTest {
	private void setIntensity(int startLine, int finishLine, int startColum, int finishColumn, double intensity, PheromoneNode[][] grid) {
		for (int l = startLine; l < finishLine; l++) {
			for (int c = startColum; c < finishColumn; c++) {
				ChemicalCommStimulus s = grid[l][c].getCommunicationStimulus(ForageStimulusType.TYPE);
				s.setIntensity(intensity);
			}
		}
	}
	
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
	
	@Test @SuppressWarnings("unused")
	public void executionTest() throws InterruptedException {
		int nColumns = 200;
		int nLines = 300;
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(60);
		List<Callable<Void>> renderers = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		final List<AntAgent> agents = AntAgentFactory.produceBunchOfWorkers(50, "a", grid, 0, 0, 48, 10);
		
		this.setIntensity(0, nLines, 0, nColumns, WorkerAntType.TYPE.getStimulusIncrement(ForageStimulusType.TYPE.getName()) * 2, grid);
		
		final List<Future<Void>> futures = executor.invokeAll(agents, 20, TimeUnit.SECONDS);
		
		renderers.add(new PheromoneRenderer(grid, "target/forage-pheromone-end.png", nColumns, nLines, ForageStimulusType.TYPE));
		renderers.add(new ExploredSpaceRenderer(grid, "target/forage-space-explored.png", nColumns, nLines));
		
		final List<Future<Void>> renderersFuture = executor.invokeAll(renderers,  5, TimeUnit.SECONDS);
		
		try {
			for (Future<Void> f : renderersFuture) {
				f.get();
			}
			
		} catch (ExecutionException ex) {
		    ex.getCause().printStackTrace();
		}
	}
}
