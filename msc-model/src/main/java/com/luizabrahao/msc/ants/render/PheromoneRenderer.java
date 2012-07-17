package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.model.env.Node;


public class PheromoneRenderer implements Callable<Void> {
	private static final Logger logger = LoggerFactory.getLogger(PheromoneRenderer.class);
	
	private static final Color c00 = new Color(0,176,240);
	private static final Color c01 = new Color(34,183,226);
	private static final Color c02 = new Color(68,191,212);
	private static final Color c03 = new Color(102,199,197);
	private static final Color c04 = new Color(136,207,183);
	private static final Color c05 = new Color(170,215,168);
	private static final Color c06 = new Color(204,223,154);
	private static final Color c07 = new Color(238,231,140);
	private static final Color c08 = new Color(251,220,124);
	private static final Color c09 = new Color(243,189,106);
	private static final Color c10 = new Color(235,157,89);
	private static final Color c11 = new Color(226,126,71);
	private static final Color c12 = new Color(218,94,53);
	private static final Color c13 = new Color(209,63,36);
	private static final Color c14 = new Color(201,32,18);
	
	private final Node[][] grid;
	private final String imagePath;
	private final int nColumns;
	private final int nLines;
	private final ChemicalCommStimulusType chemicalCommStimulusType;
	
	public PheromoneRenderer(Node[][] grid, String imagePath, int nColumns, int nLines, ChemicalCommStimulusType chemicalCommStimulusType) {
		this.grid = grid;
		this.imagePath = imagePath;
		this.nColumns = nColumns;
		this.nLines = nLines;
		this.chemicalCommStimulusType = chemicalCommStimulusType;
	}
	
	@Override
	public Void call() throws Exception {
		BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
	
		double pheromoneIntensity = 0;
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				pheromoneIntensity = ((ChemicalCommStimulus) grid[l][c].getCommunicationStimulus(chemicalCommStimulusType)).getIntensity();
				
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
			logger.error("Error rendering pheromone.");
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
