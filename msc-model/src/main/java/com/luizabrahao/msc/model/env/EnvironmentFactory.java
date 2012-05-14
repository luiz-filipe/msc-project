package com.luizabrahao.msc.model.env;

public class EnvironmentFactory {
	
	public static Node[][] createBasicNodeGrid(int nLines, int nColunms) {
		Node[][] nodes = new Node[nLines][nColunms];
		
		for(int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColunms; c++) {
				nodes[l][c] = new BasicNode("n" + l + c);
				
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
