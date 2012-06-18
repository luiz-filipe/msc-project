package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class RenderAgentFactory {
	public static Runnable getExploredSpaceRenderer(final String imagePath, final PheromoneNode[][] grid, final int nLines, final int nColumns) {
		return new Runnable() {
			@Override
			public void run() {
				BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
				
				for (int l = 0; l < nLines; l++) {
					for (int c = 0; c < nColumns; c++) {
						if (grid[l][c].getAgents() == null) {
							g2d.setColor(Color.white);
						
						} else {
							g2d.setColor(Color.black);
						}
						
						g2d.fillRect(c, l, c + 1, l + 1);
					}
				}
				
				g2d.dispose();
				
				try {
					File file = new File(imagePath);
				    ImageIO.write(bufferedImage, "png", file);
				
				} catch (IOException e) {
					// TODO add logging
				}
				
			}
		};
	}
	
	public static Runnable getPopulationRenderer(final String imagePath, final List<Agent> agents, final int nLines, final int nColumns) {
		return new Runnable() {
			
			@Override
			public void run() {
				List<Node> nodes = new ArrayList<Node>();
				
				for (Agent agent : agents) {
					nodes.add(agent.getCurrentNode());
				}
				
				BufferedImage image = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = image.createGraphics();
				
				g2d.setColor(Color.white);
				g2d.fillRect(0, 0, nColumns, nLines);
				g2d.setColor(Color.black);
				
				for (Node node : nodes) {
					int line = 0;
					int column = 0;
					
					Node currentNode = node.getNeighbour(Direction.NORTH);
					
					while (currentNode != null) {
						line++;
						currentNode = currentNode.getNeighbour(Direction.NORTH);
					}
					
					currentNode = node.getNeighbour(Direction.WEST);
					
					while (currentNode != null) {
						column++;
						currentNode = currentNode.getNeighbour(Direction.WEST);
					}
					
					image.setRGB(column, line, 0);
				}
				
				g2d.dispose();
				nodes = null;
				
				try {
					File file = new File(imagePath);
				    ImageIO.write(image, "png", file);
				
				} catch (IOException e) {
					// TODO add logging
					e.printStackTrace();
				}
			}
		};
	}
	
	public static Runnable getPheromoneRenderer(final String imagePath, final PheromoneNode[][] grid, final int nLines, final int nColumns) {
		return new Runnable() {
			
			@Override
			public void run() {
				BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
			
				final Color c00 = new Color(0,176,240);
				final Color c01 = new Color(34,183,226);
				final Color c02 = new Color(68,191,212);
				final Color c03 = new Color(102,199,197);
				final Color c04 = new Color(136,207,183);
				final Color c05 = new Color(170,215,168);
				final Color c06 = new Color(204,223,154);
				final Color c07 = new Color(238,231,140);
				final Color c08 = new Color(251,220,124);
				final Color c09 = new Color(243,189,106);
				final Color c10 = new Color(235,157,89);
				final Color c11 = new Color(226,126,71);
				final Color c12 = new Color(218,94,53);
				final Color c13 = new Color(209,63,36);
				final Color c14 = new Color(201,32,18);
				
				double pheromoneIntensity = 0;
				
				for (int l = 0; l < nLines; l++) {
					for (int c = 0; c < nColumns; c++) {
						pheromoneIntensity = grid[l][c].getPheromoneIntensity();
						
						if (pheromoneIntensity <= 0.0666) {
							g2d.setColor(c00);
						
						} else if ((pheromoneIntensity > 0.0666) && (pheromoneIntensity <= 0.1332)) {
							g2d.setColor(c01);
							
						} else if ((pheromoneIntensity > 0.1332) && (pheromoneIntensity <= 0.1998)) {
							g2d.setColor(c02);
						
						} else if ((pheromoneIntensity > 0.1998) && (pheromoneIntensity <= 0.2664)) {
							g2d.setColor(c03);
						
						} else if ((pheromoneIntensity > 0.2664) && (pheromoneIntensity <= 0.333)) {
							g2d.setColor(c04);
						
						} else if ((pheromoneIntensity > 0.333) && (pheromoneIntensity <= 0.3996)) {
							g2d.setColor(c05);
						
						} else if ((pheromoneIntensity > 0.3996) && (pheromoneIntensity <= 0.4662)) {
							g2d.setColor(c06);
						
						} else if ((pheromoneIntensity > 0.4662) && (pheromoneIntensity <= 0.5328)) {
							g2d.setColor(c07);
						
						} else if ((pheromoneIntensity > 0.5328) && (pheromoneIntensity <= 0.5994)) {
							g2d.setColor(c08);
						
						} else if ((pheromoneIntensity > 0.5994) && (pheromoneIntensity <= 0.666)) {
							g2d.setColor(c09);
						
						} else if ((pheromoneIntensity > 0.666) && (pheromoneIntensity <= 0.7326)) {
							g2d.setColor(c10);
						
						} else if ((pheromoneIntensity > 0.7326) && (pheromoneIntensity <= 0.7992)) {
							g2d.setColor(c11);
						
						} else if ((pheromoneIntensity > 0.7992) && (pheromoneIntensity <= 0.8658)) {
							g2d.setColor(c12);
						
						} else if ((pheromoneIntensity > 0.8658) && (pheromoneIntensity <= 0.9324)) {
							g2d.setColor(c13);
						
						} else if ((pheromoneIntensity > 0.9324) && (pheromoneIntensity <= 1)) {
							g2d.setColor(c14);
						}
						
						g2d.fillRect(c, l, c + 1, l + 1);
					}
				}
				
				g2d.dispose();
				
				try {
					File file = new File(imagePath);
				    ImageIO.write(bufferedImage, "png", file);
				
				} catch (IOException e) {
					// TODO add logging
				}
			}
		};
	}

	public static Runnable createNodesHistoryRenderer(final String imagePath, final AntAgent agent, final int nLines, final int nColumns) {
		return new Runnable() {
			
			@Override
			public void run() {
				List<Node> nodes = agent.getNodesVisited();

				BufferedImage image = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = image.createGraphics();
				
				g2d.setColor(Color.white);
				g2d.fillRect(0, 0, nColumns, nLines);
				g2d.setColor(Color.black);
				
				for (Node node : nodes) {
					int line = 0;
					int column = 0;
					
					Node currentNode = node.getNeighbour(Direction.NORTH);
					
					while (currentNode != null) {
						line++;
						currentNode = currentNode.getNeighbour(Direction.NORTH);
					}
					
					currentNode = node.getNeighbour(Direction.WEST);
					
					while (currentNode != null) {
						column++;
						currentNode = currentNode.getNeighbour(Direction.WEST);
					}
					
					image.setRGB(column, line, 0);
				}
				
				g2d.dispose();
				nodes = null;
				
				try {
					File file = new File(imagePath);
				    ImageIO.write(image, "png", file);
				
				} catch (IOException e) {
					// TODO add logging
					e.printStackTrace();
				}
			}
		};
	}
}
