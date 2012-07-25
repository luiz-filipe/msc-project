package com.luizabrahao.msc.ants.env;

import java.util.ArrayList;
import java.util.List;

import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class AntEnvironmentFactory {
	private AntEnvironmentFactory() {}
	
	/**
	 * Initialises an environment based on PheromoneNode objects.
	 * This environment has rectangular shape and each node are assigned an 
	 * identifier following the pattern: "n+lineNumber,colunmNumber",
	 * e.g. "n3,2" corresponds to the node at the third line and second column.
	 * 
	 * @param nLines number of lines the grid will contain
	 * @param nColunms number of column the grid will contain.
	 * @return Pheromone[][] A two dimensional array of interconnected 
	 * 		   PheromoneNode objects.
	 */
	public static PheromoneNode[][] createPheromoneNodeGrid(int nLines, int nColumns) {
		PheromoneNode[][] nodes = new PheromoneNode[nLines][nColumns];
		
		for(int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				nodes[l][c] = new PheromoneNode("n" + l + "," + c);
				
				if (c != 0) {
					nodes[l][c].setNeighbours(Direction.WEST, nodes[l][c - 1]);
				}
				
				if (l != 0) {
					nodes[l][c].setNeighbours(Direction.NORTH, nodes[l - 1][c]);
				}
			}
		}
		
		return nodes;
	}

	
	public static List<FoodSourceAgent> placeRowOfFoodSources(Node initialNode, int numberOfSources, double amountOfFoodInEachSource) {
		List<FoodSourceAgent> foodSources = new ArrayList<FoodSourceAgent>();
		Node currentNode = initialNode;
		
		for (int i = 0; i < numberOfSources; i++) {
			foodSources.add(new FoodSourceAgent("food-source-" + i, currentNode, amountOfFoodInEachSource));
			
			currentNode = currentNode.getNeighbour(Direction.EAST);
			
			if (currentNode == null) {
				break;
			}
		}
		
		return foodSources;
	}

	public static double sumFoodCollected(List<AntNestAgent> nests) {
		double result = 0;
		
		for (AntNestAgent nest : nests) {
			result = result + nest.getAmountOfFoodHeld();
		}
		
		return result;
	}
}
