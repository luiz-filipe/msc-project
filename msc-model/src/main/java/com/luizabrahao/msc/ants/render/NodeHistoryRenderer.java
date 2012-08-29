package com.luizabrahao.msc.ants.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

/**
 * Renders an image that shows all nodes an agent has visited during a
 * simulation. Note that the agent must have the flag <em>recordNodeHistory</em>
 * enabled.
 * 
 * @see AbstractAgent
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */

@ThreadSafe
public class NodeHistoryRenderer implements Callable<Void> {
	private static final Logger logger = LoggerFactory.getLogger(NodeHistoryRenderer.class);
	
	private final Agent agent;
	private final String imagePath;
	private final int nColumns;
	private final int nLines;
	
	public NodeHistoryRenderer(Agent agent, String imagePath, int nColumns, int nLines) {
		this.agent = agent;
		this.imagePath = imagePath;
		this.nColumns = nColumns;
		this.nLines = nLines;
	}
	
	@Override
	public Void call() throws Exception {
		logger.info("Starting to render visited nodes history for: {} wirh name '{}'", agent.getId(), imagePath);

		BufferedImage image = new BufferedImage(nColumns, nLines, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		List<Node> nodes = null;
		
		synchronized (agent.getNodesVisited()) {
			nodes = agent.getNodesVisited();

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
		
		logger.info("Finished rendering nodes history for: {}", agent.getId());
		return null;
	}

}
