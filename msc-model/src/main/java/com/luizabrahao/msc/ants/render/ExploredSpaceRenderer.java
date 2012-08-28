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

import com.luizabrahao.msc.model.env.Node;

/**
 * Renders the environment, nodes that have been visited by any agent are
 * coloured in black, nodes that have not been visited at all are in white.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public class ExploredSpaceRenderer implements Callable<Void> {
	private static final Logger logger = LoggerFactory.getLogger(ExploredSpaceRenderer.class);
	
	private final Node[][] grid;
	private final String imagePath;
	private final int nColumns;
	private final int nLines;
	
	public ExploredSpaceRenderer(Node[][] grid, String imagePath, int nColumns, int nLines) {
		this.grid = grid;
		this.imagePath = imagePath;
		this.nColumns = nColumns;
		this.nLines = nLines;
	}

	@Override
	public Void call() throws Exception {
		logger.info("Starting to render explored space...");
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
		logger.info("Rendering explored space completed...");
		try {
			File file = new File(imagePath);
		    ImageIO.write(bufferedImage, "png", file);
		
		} catch (IOException e) {
			logger.error("Error rendering explored space.");
			logger.error(e.getMessage());
		}
		
		return null;	
	}

}
