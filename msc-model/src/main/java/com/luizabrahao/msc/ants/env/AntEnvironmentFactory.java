package com.luizabrahao.msc.ants.env;

import com.luizabrahao.msc.model.env.Direction;

public class AntEnvironmentFactory {
	
	/**
	 * Initialises an environment based on PheromoneNode objects. This
	 * environment has rectangular shape and each node are assigned an
	 * identifier following the pattern: "n+lineNumber+colunmNumber",
	 * e.g. "n32" corresponds to the node at the third line and second column.
	 * 
	 * @param nLines number of lines the grid will contain
	 * @param nColunms number of column the grid will contain.
	 * @return Node[][] A two dimensional array of interconnected BasicNode objects.
	 */
	public static PheromoneNode[][] createPheromoneNodeGrid(int nLines, int nColunms) {
		PheromoneNode[][] nodes = new PheromoneNode[nLines][nColunms];
		
		for(int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColunms; c++) {
				nodes[l][c] = new PheromoneNode("n" + l + c);
				
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
}
