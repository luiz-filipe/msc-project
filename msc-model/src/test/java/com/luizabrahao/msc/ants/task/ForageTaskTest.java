package com.luizabrahao.msc.ants.task;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.WorkerType;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.NodeHistoryRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class ForageTaskTest {
	private void setIntensity(int startLine, int finishLine, int startColum, int finishColumn, double intensity, PheromoneNode[][] grid) {
		for (int l = startLine; l < finishLine; l++) {
			for (int c = startColum; c < finishColumn; c++) {
				(grid[l][c]).setPheromoneIntensity(intensity);
			}
		}
	}
	
	@Test
	public void montecarloRouletteTest() {		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		
		AntAgent a = new AntAgent("a01", WorkerType.getInstance(), grid[1][1], false);
		
		a.setMovingDirection(Direction.SOUTH);
		grid[2][1].setPheromoneIntensity(1);
		Node n = ForageTask.getNodeToMoveTo(a);
		assertTrue(n.getId().equals("n2,1"));
		
		a.setMovingDirection(Direction.WEST);
		grid[2][1].setPheromoneIntensity(0);
		grid[1][0].setPheromoneIntensity(1);
		n = ForageTask.getNodeToMoveTo(a);
		assertTrue(n.getId().equals("n1,0"));
		
		a.setMovingDirection(Direction.NORTH);
		grid[1][0].setPheromoneIntensity(0);
		grid[0][1].setPheromoneIntensity(1);
		n = ForageTask.getNodeToMoveTo(a);
		assertTrue(n.getId().equals("n0,1"));
		
		a.setMovingDirection(Direction.EAST);
		grid[0][1].setPheromoneIntensity(0);
		grid[1][2].setPheromoneIntensity(1);
		n = ForageTask.getNodeToMoveTo(a);
		assertTrue(n.getId().equals("n1,2"));
	}
		
	@Test(expected=CancellationException.class)
	public void followPheromoneTest() throws InterruptedException {
		final int nLines = 300;
		final int nColumns = 200;
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(35);
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		
		this.setIntensity(0, nLines, 0, nColumns, 0, grid);
		this.setIntensity(10, 30, 25, 175, 0.2, grid);
		this.setIntensity(30, 80, 50, 150, 0.4, grid);
		this.setIntensity(80, 130, 75, 125, 0.6, grid);
		this.setIntensity(130, 180, 80, 115, 0.8, grid);
		this.setIntensity(180, 270, 90, 105, 1, grid);
		
		final AntAgent a01 = new AntAgent("a01", WorkerType.getInstance(), grid[0][10], true);
		final AntAgent a02 = new AntAgent("a02", WorkerType.getInstance(), grid[0][20], true);
		final AntAgent a03 = new AntAgent("a03", WorkerType.getInstance(), grid[0][30], true);
		final AntAgent a04 = new AntAgent("a04", WorkerType.getInstance(), grid[0][40], true);
		final AntAgent a05 = new AntAgent("a05", WorkerType.getInstance(), grid[0][50], true);
		final AntAgent a06 = new AntAgent("a06", WorkerType.getInstance(), grid[0][60], true);
		final AntAgent a07 = new AntAgent("a07", WorkerType.getInstance(), grid[0][70], true);
		final AntAgent a08 = new AntAgent("a08", WorkerType.getInstance(), grid[0][80], true);
		final AntAgent a09 = new AntAgent("a09", WorkerType.getInstance(), grid[0][90], true);
		final AntAgent a10 = new AntAgent("a10", WorkerType.getInstance(), grid[0][100], true);
		
		List<Agent> agents = new ArrayList<Agent>();
		agents.add(a01);
		agents.add(a02);
		agents.add(a03);
		agents.add(a04);
		agents.add(a05);
		agents.add(a06);
		agents.add(a07);
		agents.add(a08);
		agents.add(a09);
		agents.add(a10);
		
		tasks.add(a01);
		tasks.add(a02);
		tasks.add(a03);
		tasks.add(a04);
		tasks.add(a05);
		tasks.add(a06);
		tasks.add(a07);
		tasks.add(a08);
		tasks.add(a09);
		tasks.add(a10);
		
		tasks.add(new PheromoneRenderer(grid, "target/forage-pheromone-test.png", nColumns, nLines));
		
		executor.schedule(new NodeHistoryRenderer(a01, "target/forage-history-ag01.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a02, "target/forage-history-ag02.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a03, "target/forage-history-ag03.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a04, "target/forage-history-ag04.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a05, "target/forage-history-ag05.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a06, "target/forage-history-ag06.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a07, "target/forage-history-ag07.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a08, "target/forage-history-ag08.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a09, "target/forage-history-ag09.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		executor.schedule(new NodeHistoryRenderer(a10, "target/forage-history-ag10.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		
		executor.schedule(new ExploredSpaceRenderer(grid, "target/forage-space-explored.png", nColumns, nLines), 10, TimeUnit.SECONDS);
		
		final List<Future<Void>> futures = executor.invokeAll(tasks, 11, TimeUnit.SECONDS);
		
		try {
			for (Future<Void> f : futures) {
				f.get();
			}
			
		} catch (ExecutionException ex) {
		    ex.getCause().printStackTrace();
		}
	}
}
