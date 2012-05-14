package com.luizabrahao.msc.model.env;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnvironmentFactoryTest {

	@Test
	public void basicNodeGrid() {
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(5, 5);
		
		assertTrue(grid[0][0].getId().equals("n00"));
		assertTrue(grid[3][3].getId().equals("n33"));
		
		assertTrue(grid[0][0].getNeighbour(Direction.NORTH) == null);	
		assertTrue(grid[0][0].getNeighbour(Direction.SOUTH) == grid[1][0]);
		assertTrue(grid[1][0].getNeighbour(Direction.NORTH) == grid[0][0]);
				
		assertTrue(grid[4][4].getNeighbour(Direction.SOUTH) == null);
		assertTrue(grid[4][4].getNeighbour(Direction.NORTH) == grid[3][4]);
		
		assertTrue(grid[4][4].getNeighbour(Direction.EAST) == null);
		assertTrue(grid[4][4].getNeighbour(Direction.WEST) == grid[4][3]);
		
		assertTrue(grid[4][0].getNeighbour(Direction.WEST) == null);
		assertTrue(grid[4][0].getNeighbour(Direction.EAST) == grid[4][1]);
	}
}
