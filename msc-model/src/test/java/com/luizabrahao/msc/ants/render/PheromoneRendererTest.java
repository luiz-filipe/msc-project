package com.luizabrahao.msc.ants.render;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.PheromoneNode;

public class PheromoneRendererTest {
	private void setIntensity(int startLine, int numberOfLines, int nColumns, double intensity, PheromoneNode[][] grid) {
		for (int l = startLine; l < startLine + numberOfLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				(grid[l][c]).setPheromoneIntensity(intensity);
			}
		}
	}
	
	@Test @SuppressWarnings("unused")
	public void renderPheromoneMap() throws InterruptedException {
		final int nLines = 150;
		final int nColumns = 100;
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
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
		tasks.add(new PheromoneRenderer(grid, "target/space-pheromone.png", nColumns, nLines));

		final List<Future<Void>> futures = executor.invokeAll(tasks);
	}
	
	
	/*
	@Test
	public void pheromoneDecayTest() {
		int nLines = 250;
		int nColumns = 200;
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				(grid[l][c]).setPheromoneIntensity(0.1);
			}
		}
		
		for (int l = 15; l < 30; l++) {
			for (int c = 25; c < 175; c++) {
				(grid[l][c]).setPheromoneIntensity(0.2);
			}
		}
		
		for (int l = 30; l < 45; l++) {
			for (int c = 50; c < 150; c++) {
				(grid[l][c]).setPheromoneIntensity(0.4);
			}
		}
		
		for (int l = 45; l < 60; l++) {
			for (int c = 75; c < 125; c++) {
				(grid[l][c]).setPheromoneIntensity(0.6);
			}
		}		
		
		StaticPheromoneUpdater u = new StaticPheromoneUpdater("updater-02", PheromoneUpdaterAgentType.getInstance(), grid[0][0], 250);
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(15);
		
		final ScheduledFuture<?> futureDecayAgent = executor.schedule(u, 1000, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> futureRender = executor.schedule(RenderAgentFactory.getPheromoneRenderer("target/pheromone-update.png", grid, nLines, nColumns), 1200, TimeUnit.MILLISECONDS);
		
		executor.schedule(new Runnable(){
		     public void run(){
		        futureDecayAgent.cancel(true);
		        futureRender.cancel(true);
		     }      
		 }, 6000, TimeUnit.MILLISECONDS);
		
	}
	*/
}
