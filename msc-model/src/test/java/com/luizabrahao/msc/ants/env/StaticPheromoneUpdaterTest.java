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

public class StaticPheromoneUpdaterTest {
	private double getForagePheromoneIntensity(PheromoneNode node) {
		ChemicalCommStimulus s = node.getCommunicationStimulus(ForageStimulusType.TYPE);
		return s.getIntensity();
	}
	
	
	@Test @SuppressWarnings("unused")
	public void updateTest() throws InterruptedException {
		int nLines = 4;
		int nColumns = 3;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		StaticPheromoneUpdaterAgent u = new StaticPheromoneUpdaterAgent("updater-01", grid[0][0], 2);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus s = grid[l][c].getCommunicationStimulus(ForageStimulusType.TYPE);
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
		
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		StaticPheromoneUpdaterAgent u = new StaticPheromoneUpdaterAgent("updater-02", grid[0][0], 250);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus s = grid[l][c].getCommunicationStimulus(ForageStimulusType.TYPE);
				s.setIntensity(1);
			}
		}
		
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		tasks.add(u);

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final List<Future<Void>> futures = executor.invokeAll(tasks);
	}
}
