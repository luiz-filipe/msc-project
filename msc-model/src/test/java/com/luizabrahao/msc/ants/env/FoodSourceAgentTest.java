package com.luizabrahao.msc.ants.env;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luizabrahao.msc.model.env.Node;

public class FoodSourceAgentTest {

	@Test
	public void collectFoodTest() {
		Node[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		FoodSourceAgent foodSource = new FoodSourceAgent("food-source-1", grid[1][1], 10.0);
		
		double collection01 = foodSource.collectFood(3.0);
		assertTrue(collection01 == 3.0);
		
		double collection02 = foodSource.collectFood(6.0);
		assertTrue(collection02 == 6.0);
		
		double collection03 = foodSource.collectFood(3.0);
		assertTrue(collection03 == 1.0);
		
		double collection04 = foodSource.collectFood(3.0);
		assertTrue(collection04 == 0);
	}
}
