package com.luizabrahao.msc.ants.env;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

import com.luizabrahao.msc.ants.agent.StaticPheromoneUpdaterAgent;
import com.luizabrahao.msc.model.env.EnvironmentFactory;
import com.luizabrahao.msc.model.env.Node;

public class StaticPheromoneUpdaterTest {
	private double getForagePheromoneIntensity(Node node) {
		return ((ChemicalCommStimulus) node.getCommunicationStimulus(ForageStimulusType.getInstance())).getIntensity();
	}
	
	
	@Test @SuppressWarnings("unused")
	public void updateTest() throws InterruptedException {
		int nLines = 4;
		int nColumns = 3;
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
		StaticPheromoneUpdaterAgent u = new StaticPheromoneUpdaterAgent("updater-01", grid[0][0], 2);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus s = (ChemicalCommStimulus) grid[l][c].getCommunicationStimulus(ForageStimulusType.getInstance());
				s.setIntensity(1);
			}
		}
		
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		tasks.add(u);

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final List<Future<Void>> futures = executor.invokeAll(tasks);
		
		assertTrue(this.getForagePheromoneIntensity(grid[0][0]) == 0.9);
		assertTrue(this.getForagePheromoneIntensity(grid[0][2]) == 0.9);
		
		assertTrue(this.getForagePheromoneIntensity(grid[2][2]) == 1);
	}
	
	@Test @SuppressWarnings("unused")
	public void throughputTest() throws InterruptedException {
		int nLines = 250;
		int nColumns = 200;
		
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
		StaticPheromoneUpdaterAgent u = new StaticPheromoneUpdaterAgent("updater-02", grid[0][0], 250);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus s = (ChemicalCommStimulus) grid[l][c].getCommunicationStimulus(ForageStimulusType.getInstance());
				s.setIntensity(1);
			}
		}
		
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		tasks.add(u);

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final List<Future<Void>> futures = executor.invokeAll(tasks);
	}
}
