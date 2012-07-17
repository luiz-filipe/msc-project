package com.luizabrahao.msc.ants.render;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.env.EnvironmentFactory;
import com.luizabrahao.msc.model.env.Node;

public class PheromoneRendererTest {
	private void setIntensity(int startLine, int numberOfLines, int nColumns, double intensity, Node[][] grid) {
		for (int l = startLine; l < startLine + numberOfLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus s = (ChemicalCommStimulus) grid[l][c].getCommunicationStimulus(ForageStimulusType.getInstance());
				s.setIntensity(intensity);
			}
		}
	}
	
	@Test @SuppressWarnings("unused")
	public void renderPheromoneMap() throws InterruptedException {
		final int nLines = 150;
		final int nColumns = 100;
		final Node[][] grid = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		
		this.setIntensity(00, 10, nColumns, 0.06, grid);
		this.setIntensity(10, 10, nColumns, 0.12, grid);
		this.setIntensity(20, 10, nColumns, 0.18, grid);
		this.setIntensity(30, 10, nColumns, 0.24, grid);
		this.setIntensity(40, 10, nColumns, 0.30, grid);
		this.setIntensity(50, 10, nColumns, 0.36, grid);
		this.setIntensity(60, 10, nColumns, 0.42, grid);
		this.setIntensity(70, 10, nColumns, 0.48, grid);
		this.setIntensity(80, 10, nColumns, 0.54, grid);
		this.setIntensity(90, 10, nColumns, 0.60, grid);
		this.setIntensity(100, 10, nColumns, 0.68, grid);
		this.setIntensity(110, 10, nColumns, 0.74, grid);
		this.setIntensity(120, 10, nColumns, 0.80, grid);
		this.setIntensity(130, 10, nColumns, 0.87, grid);
		this.setIntensity(140, 10, nColumns, 0.96, grid);
		
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		tasks.add(new PheromoneRenderer(grid, "target/space-pheromone.png", nColumns, nLines, ForageStimulusType.getInstance()));

		final List<Future<Void>> futures = executor.invokeAll(tasks);
	}
}
