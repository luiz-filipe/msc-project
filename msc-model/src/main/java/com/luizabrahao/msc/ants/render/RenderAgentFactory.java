package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.luizabrahao.msc.ants.env.PheromoneNode;

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
	
	public static Runnable getPopulationRenderer(final String imagePath, final PheromoneNode[][] grid, final int nLines, final int nColumns) {
		return new Runnable() {
			
			@Override
			public void run() {
				BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
			
				for (int l = 0; l < nLines; l++) {
					for (int c = 0; c < nColumns; c++) {
						if ((grid[l][c].getAgents() != null) && (grid[l][c].getAgents().size() != 0)) {
							g2d.setColor(Color.black);
						
						} else {
							g2d.setColor(Color.white);
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
	
	public static Runnable getPheromoneRenderer(final String imagePath, final PheromoneNode[][] grid, final int nLines, final int nColumns) {
		return new Runnable() {
			
			@Override
			public void run() {
				BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
			
				final Color lowLow = new Color(186, 255, 48);
				final Color low = new Color(255, 255, 47);
				final Color medium = new Color(255, 186, 47);
				final Color high = new Color(255, 116, 47);
				final Color highHigh = new Color(254, 48, 48);
				
				double pheromoneIntensity = 0;
				
				for (int l = 0; l < nLines; l++) {
					for (int c = 0; c < nColumns; c++) {
						pheromoneIntensity = grid[l][c].getPheromoneIntensity();
						
						if (pheromoneIntensity < 0.2) {
							g2d.setColor(lowLow);
						
						} else if ((pheromoneIntensity >= 0.2) && (pheromoneIntensity < 0.4)) {
							g2d.setColor(low);
						
						} else if ((pheromoneIntensity >= 0.4) && (pheromoneIntensity < 0.6)) {
							g2d.setColor(medium);
						
						} else if ((pheromoneIntensity >= 0.6) && (pheromoneIntensity < 0.8)) {
							g2d.setColor(high);
						
						} else if ((pheromoneIntensity >= 0.8) && (pheromoneIntensity <= 1)) {
							g2d.setColor(highHigh);
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
}
