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
import com.luizabrahao.msc.ants.agent.WorkerAntType;
import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.render.ExploredSpaceRenderer;
import com.luizabrahao.msc.ants.render.NodeHistoryRenderer;
import com.luizabrahao.msc.ants.render.PheromoneRenderer;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class AntTaskUtilTest {
	private void setIntensity(int startLine, int finishLine, int startColum, int finishColumn, double intensity, PheromoneNode[][] grid) {
		for (int l = startLine; l < finishLine; l++) {
			for (int c = startColum; c < finishColumn; c++) {
				ChemicalCommStimulus s = grid[l][c].getCommunicationStimulus(ForageStimulusType.TYPE); 
				s.setIntensity(intensity);
			}
		}
	}
	
	@Test
	public void montecarloRouletteTest() {		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		
		AntAgent a = new AntAgent("a01", WorkerAntType.TYPE, grid[1][1], false);
		
		a.setMovingDirection(Direction.SOUTH);
		(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(1);
		Direction d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE); 
		Node n = a.getCurrentNode().getNeighbour(d);
		
		assertTrue(n.getId().equals("n2,1"));
		
		a.setMovingDirection(Direction.WEST);
		(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(0);
		(grid[1][0].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(1);
		d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE); 
		n = a.getCurrentNode().getNeighbour(d);
		assertTrue(n.getId().equals("n1,0"));
		
		a.setMovingDirection(Direction.NORTH);
		(grid[1][0].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(0);
		(grid[0][1].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(1);
		d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE); 
		n = a.getCurrentNode().getNeighbour(d);
		assertTrue(n.getId().equals("n0,1"));
		
		a.setMovingDirection(Direction.EAST);
		grid[1][2].addCommunicationStimulus(new ChemicalCommStimulus(ForageStimulusType.TYPE));
		(grid[0][1].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(0);
		(grid[1][2].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(1);
		d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE); 
		n = a.getCurrentNode().getNeighbour(d);
		assertTrue(n.getId().equals("n1,2"));
	}
	
	@Test
	public void pheromoneDepositTest() {
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		
		final AntAgent a = new AntAgent("a01", WorkerAntType.TYPE, grid[1][1], false);
		a.setMovingDirection(Direction.SOUTH);
		
		(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE)).setIntensity(0.1);
		Direction d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE);
		Node n = a.getCurrentNode().getNeighbour(d);
		assertTrue(n.getId().equals("n2,1"));
		
		a.incrementStimulusIntensity(ForageStimulusType.TYPE);
		n.addAgent(a);
		
		ChemicalCommStimulus s = (ChemicalCommStimulus) grid[1][1].getCommunicationStimulus(ForageStimulusType.TYPE);
		assertTrue(s.getIntensity() == WorkerAntType.TYPE.getStimulusIncrement(ForageStimulusType.TYPE.getName()));
	}
	
	@Test(expected=CancellationException.class)
	public void followPheromoneTest() throws InterruptedException {
		final int nLines = 300;
		final int nColumns = 200;
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(50);
		List<Callable<Void>> agentsTasks = new ArrayList<Callable<Void>>();
		
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(nLines, nColumns);
		
		this.setIntensity(0, nLines, 0, nColumns, WorkerAntType.TYPE.getStimulusIncrement(ForageStimulusType.TYPE.getName()) * 100, grid);
//		this.setIntensity(10, 30, 25, 175, 0.2, grid);
//		this.setIntensity(30, 80, 50, 150, 0.4, grid);
//		this.setIntensity(80, 130, 75, 125, 0.6, grid);
//		this.setIntensity(130, 180, 80, 115, 0.8, grid);
//		this.setIntensity(180, 270, 90, 105, 1, grid);
		
		final AntAgent a01 = new AntAgent("a01", WorkerAntType.TYPE, grid[0][10], true);
		final AntAgent a02 = new AntAgent("a02", WorkerAntType.TYPE, grid[0][20], true);
		final AntAgent a03 = new AntAgent("a03", WorkerAntType.TYPE, grid[0][30], true);
		final AntAgent a04 = new AntAgent("a04", WorkerAntType.TYPE, grid[0][40], true);
		final AntAgent a05 = new AntAgent("a05", WorkerAntType.TYPE, grid[0][50], true);
		final AntAgent a06 = new AntAgent("a06", WorkerAntType.TYPE, grid[0][60], true);
		final AntAgent a07 = new AntAgent("a07", WorkerAntType.TYPE, grid[0][70], true);
		final AntAgent a08 = new AntAgent("a08", WorkerAntType.TYPE, grid[0][80], true);
		final AntAgent a09 = new AntAgent("a09", WorkerAntType.TYPE, grid[0][90], true);
		final AntAgent a10 = new AntAgent("a10", WorkerAntType.TYPE, grid[0][100], true);
		
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
		
		agentsTasks.add(a01);
		agentsTasks.add(a02);
		agentsTasks.add(a03);
		agentsTasks.add(a04);
		agentsTasks.add(a05);
		agentsTasks.add(a06);
		agentsTasks.add(a07);
		agentsTasks.add(a08);
		agentsTasks.add(a09);
		agentsTasks.add(a10);

		executor.submit(new PheromoneRenderer(grid, "target/forage-pheromone-start.png", nColumns, nLines, ForageStimulusType.TYPE));
		final List<Future<Void>> futures = executor.invokeAll(agentsTasks, 21, TimeUnit.SECONDS);
		
		executor.submit(new NodeHistoryRenderer(a01, "target/forage-history-ag01.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a02, "target/forage-history-ag02.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a03, "target/forage-history-ag03.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a04, "target/forage-history-ag04.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a05, "target/forage-history-ag05.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a06, "target/forage-history-ag06.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a07, "target/forage-history-ag07.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a08, "target/forage-history-ag08.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a09, "target/forage-history-ag09.png", nColumns, nLines));
		executor.submit(new NodeHistoryRenderer(a10, "target/forage-history-ag10.png", nColumns, nLines));
		executor.submit(new ExploredSpaceRenderer(grid, "target/forage-space-explored.png", nColumns, nLines));
		executor.submit(new PheromoneRenderer(grid, "target/forage-pheromone-end.png", nColumns, nLines, ForageStimulusType.TYPE));
		
		try {
			for (Future<Void> f : futures) {
				f.get();
			}
			
		} catch (ExecutionException ex) {
		    ex.getCause().printStackTrace();
		}
		
	}
}
