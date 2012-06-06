package com.luizabrahao.msc.ants.env;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

public class StaticPheromoneUpdaterTest {
	
	@Test @SuppressWarnings("unused")
	public void updateTest() throws InterruptedException {
		int nLines = 4;
		int nColumns = 3;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		StaticPheromoneUpdater u = new StaticPheromoneUpdater("updater-01", PheromoneUpdaterAgentType.getInstance(), grid[0][0], 2);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				(grid[l][c]).setPheromoneIntensity(1);
			}
		}
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(u));

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final List<Future<Object>> futures = executor.invokeAll(tasks);
		
		assertTrue(grid[0][0].getPheromoneIntensity() == 0.9);
		assertTrue(grid[0][2].getPheromoneIntensity() == 0.9);
		
		assertTrue(grid[2][2].getPheromoneIntensity() == 1);
		assertTrue(grid[2][2].getPheromoneIntensity() == 1);
	}
}
