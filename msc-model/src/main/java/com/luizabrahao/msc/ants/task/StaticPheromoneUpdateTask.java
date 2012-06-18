package com.luizabrahao.msc.ants.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.StaticPheromoneUpdaterAgent;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.task.AbstractTask;

/**
 * Starting from its current node, it triggers the pheromone update method for
 * each node than it moves to the next node (towards east) in the same line
 * until it reaches the end of that line, when that happens it moved to the
 * next line and the process is repeated until it reaches the last line of the
 * grid or the number of lines the updater has processed is the number of lines
 * to be processed defined by the agents' field numberOfLinesToProcess.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public class StaticPheromoneUpdateTask extends AbstractTask {
	private static final Logger logger = LoggerFactory.getLogger(StaticPheromoneUpdateTask.class);
	public static final String NAME = "Static updater";
	
	public StaticPheromoneUpdateTask() {
		super(StaticPheromoneUpdateTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		logger.debug("[{}] Starting pherormone update run", agent.getId());
		final int maximumNumberOfLines = ((StaticPheromoneUpdaterAgent) agent).getNumberOfLinesToProcess();
		
		PheromoneNode startNode = (PheromoneNode) agent.getCurrentNode();
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
			
			if (numberOfRowsProcessed == maximumNumberOfLines) {
				break;
			}
		}
		
		logger.debug("[{}] Finished pherormone update run", agent.getId());
	}
}
