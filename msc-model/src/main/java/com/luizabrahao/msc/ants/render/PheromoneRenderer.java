package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.model.env.Node;

/**
 * Renders an image based on the concentration of a chemical communication
 * stimulus. The intensity of the stimulus is rendered in 256 different
 * intensities of red.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */

@ThreadSafe
public class PheromoneRenderer implements Callable<Void> {
	private static final Logger logger = LoggerFactory.getLogger(PheromoneRenderer.class);
	
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
		logger.info("Pheromone render for '{}' started.", this.chemicalCommStimulusType.getName());
		BufferedImage bufferedImage = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
	
		double pheromoneIntensity = 0;
		
		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColumns; c++) {
				ChemicalCommStimulus stimulus = (ChemicalCommStimulus) grid[l][c].getCommunicationStimulus(chemicalCommStimulusType);
				
				if (stimulus == null) {
					pheromoneIntensity = 0;
				} else {
					pheromoneIntensity =stimulus.getIntensity();
				}
				
				int colorRate = 255 - (int) (pheromoneIntensity * 255);
				g2d.setColor(new Color(255, colorRate, colorRate));
				
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
		
		logger.info("Pheromone render for '{}' finished.", this.chemicalCommStimulusType.getName());
		return null;
	}
}
