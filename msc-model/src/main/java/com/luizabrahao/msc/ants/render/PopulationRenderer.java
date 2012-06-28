package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class PopulationRenderer implements Callable<Void>{
	private static final Logger logger = LoggerFactory.getLogger(PopulationRenderer.class);
	
	private final List<Agent> agents;
	private final String imagePath;
	private final int nColumns;
	private final int nLines;

	public PopulationRenderer(List<Agent> agents, String imagePath, int nColumns, int nLines) {
		this.agents = agents;
		this.imagePath = imagePath;
		this.nColumns = nColumns;
		this.nLines = nLines;
	}
	
	@Override
	public Void call() throws Exception {
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
			logger.error("Error rendering population.");
			logger.error(e.getMessage());
		}
		
		return null;
	}

}
