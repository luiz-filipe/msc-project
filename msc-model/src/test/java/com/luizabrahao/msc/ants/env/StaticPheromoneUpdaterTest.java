package com.luizabrahao.msc.ants.env;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luizabrahao.msc.model.env.Node;

public class StaticPheromoneUpdaterTest {
	
	@Test
	public void updateTest() {
		int nLines = 250;
		int nColumns = 250;
		Node[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		StaticPheromoneUpdater u = new StaticPheromoneUpdater("updater-01", PheromoneUpdaterAgentType.getInstance(), (PheromoneNode) grid[0][0], nLines, nColumns);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				((PheromoneNode) grid[l][c]).setPheromoneIntensity(1);
			}
		}
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final Future<?> handler = executor.submit(u);
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				handler.cancel(true);
				
			}
		}, 30, TimeUnit.SECONDS);
	}

}
