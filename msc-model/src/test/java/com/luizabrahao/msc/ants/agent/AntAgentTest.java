package com.luizabrahao.msc.ants.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class AntAgentTest {
	@Test
	public void getNeighbourInRelationToAgentOrientation() {
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		AntAgent a = new AntAgent("a01", WorkerType.getInstance(), grid[0][0], false);

		a.setMovingDirection(Direction.NORTH);
		
		Node n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n == null);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n0,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n1,0"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n == null);

		
		a.setMovingDirection(Direction.SOUTH);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n1,0"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n == null);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n == null);

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n0,1"));
		
		
		a.setMovingDirection(Direction.EAST);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n0,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n1,0"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n == null);

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n == null);
		
		a.setMovingDirection(Direction.WEST);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n == null);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n == null);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n0,1"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n1,0"));
		
		
		
		grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		a = new AntAgent("a01", WorkerType.getInstance(), grid[1][1], false);
		
		a.setMovingDirection(Direction.SOUTH);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n2,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n1,0"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n0,1"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n1,2"));
		
		a.setMovingDirection(Direction.EAST);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n1,2"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n2,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n1,0"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n0,1"));
		
		a.setMovingDirection(Direction.WEST);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n1,0"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n0,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n1,2"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n2,1"));
		
		a.setMovingDirection(Direction.NORTH);
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
		assertTrue(n.getId().equals("n0,1"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		assertTrue(n.getId().equals("n1,2"));
		
		n = a.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		assertTrue(n.getId().equals("n2,1"));

		n = a.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		assertTrue(n.getId().equals("n1,0"));
	}
}
