package com.luizabrahao.msc.ants.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.agent.AgentType;
import com.luizabrahao.msc.model.env.Direction;

public class StaticPheromoneUpdater extends AbstractAgent {
	private final Logger logger = LoggerFactory.getLogger(StaticPheromoneUpdater.class);
	public static double DECAY_FACTOR = 0.1;
	private final int maximumNumberOfRows;
	
	public StaticPheromoneUpdater(String id, AgentType agentType, PheromoneNode currentNode, int maximumNumberOfRows) {
		super(id, agentType, currentNode, false);
		this.maximumNumberOfRows = maximumNumberOfRows;
	}

	@Override
	public void run() {
		logger.debug("[{}] Starting pherormone update run", this.getId());
		
		PheromoneNode startNode = (PheromoneNode) currentNode;
		PheromoneNode nodeToBeUpdated = startNode;
		int numberOfRowsProcessed = 0;
		
		for (;;) {
			for (;;) {
				nodeToBeUpdated.updatePheromoneIntensity();
				
				if (nodeToBeUpdated.getNeighbour(Direction.EAST) != null) {
					nodeToBeUpdated = (PheromoneNode) nodeToBeUpdated.getNeighbour(Direction.EAST);
				
				} else {
					break;
				}
			}
			
			if (startNode.getNeighbour(Direction.SOUTH) != null) {
				startNode = (PheromoneNode) startNode.getNeighbour(Direction.SOUTH);
				nodeToBeUpdated = startNode;
			
			} else {
				break;
			}
			
			numberOfRowsProcessed++;
			
			if (numberOfRowsProcessed == maximumNumberOfRows) {
				break;
			}
		}
		
			
		logger.debug("[{}] Finished pherormone update run", this.getId());
	}
}
