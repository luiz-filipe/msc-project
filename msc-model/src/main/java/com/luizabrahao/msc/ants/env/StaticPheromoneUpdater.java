package com.luizabrahao.msc.ants.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.agent.AgentType;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class StaticPheromoneUpdater extends AbstractAgent {
	private final Logger logger = LoggerFactory.getLogger(StaticPheromoneUpdater.class);
	public static double DECAY_FACTOR = 0.1;
	private final int nRows;
	private final int nColunms;
	
	public StaticPheromoneUpdater(String id, AgentType agentType, Node currentNode, int nRows, int nColunms) {
		super(id, agentType, currentNode);
		
		this.nRows = nRows;
		this.nColunms = nColunms;
	}

	@Override
	public void run() {
		logger.debug("[{}] Starting pherormone update run", this.getId());
		PheromoneNode startNode = (PheromoneNode) currentNode;
		PheromoneNode nodeToBeUpdated = (PheromoneNode) currentNode;
		
		for (int l = 0; l < nRows; l++) {
			startNode = (PheromoneNode) startNode.getNeighbour(Direction.SOUTH);
			
			for (int c = 0; c < nColunms; c++) {
				nodeToBeUpdated.updatePheromoneIntensity();
				logger.debug("column {} updated", c);
			}
			
			nodeToBeUpdated = startNode;
			logger.debug("line {} updated", l);
		}
		
		logger.debug("[{}] Finished pherormone update run", this.getId());
	}
}
