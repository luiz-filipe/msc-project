package com.luizabrahao.msc.ants.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;
import com.luizabrahao.msc.model.task.WandererTask;

public class ForageTask extends AbstractTask {
	private static final Logger logger = LoggerFactory.getLogger(ForageTask.class);
	
	public static final String NAME = "Forage"; 
	public static final double WEIGHT_NORTH = 0.35;
	public static final double WEIGHT_EAST = 0.25;
	public static final double WEIGHT_SOUTH = 0.15;
	public static final double WEIGHT_WEST = 0.25;

	public ForageTask() {
		super(ForageTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		while (true) {
			Node nodeToMoveTo = ForageTask.getNodeToMoveTo((AntAgent) agent); 
			
			if (nodeToMoveTo != null) {
				nodeToMoveTo.addAgent(agent);
			} else {
				logger.debug("ForageTask.getNodeToMoveTo returned null");
			}
		}
	}
	
	public static Node getNodeToMoveTo(AntAgent agent) {
		double pheromoneNorth = 0;
		double pheromoneEast = 0;
		double pheromoneSouth = 0;
		double pheromoneWest = 0;
		
		if (((AntAgent) agent).getMovingDirection() == null) {
			((AntAgent) agent).setMovingDirection(Direction.SOUTH);
		}
		
		synchronized (agent.getCurrentNode()) {
		    PheromoneNode node = (PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
			
		    if (node != null) {
		    	pheromoneNorth = node.getPheromoneIntensity();
		    } else {
		    	pheromoneNorth = 0;
		    }
		    
		    node = (PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.EAST);
		    
		    if (node != null) {
		    	pheromoneEast = node.getPheromoneIntensity();
		    } else {
		    	pheromoneEast = 0;
		    }
		    
		    node = (PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
		    
		    if (node != null) {
		    	pheromoneSouth = node.getPheromoneIntensity();
		    } else {
		    	pheromoneSouth = 0;
		    }
		    
		    node = (PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.WEST);
		    
		    if (node != null) {
		    	pheromoneWest = node.getPheromoneIntensity();
		    } else {
		    	pheromoneWest = 0;
		    }
			
			double rateNorth = pheromoneNorth * ForageTask.WEIGHT_NORTH;
			double rateEast = pheromoneEast * ForageTask.WEIGHT_EAST;
			double rateSouth = pheromoneSouth * ForageTask.WEIGHT_SOUTH;
			double rateWest = pheromoneWest * ForageTask.WEIGHT_WEST;

			// if all neighbour nodes are going to have the same probability
			// let's return one at random.
			if ((rateNorth == rateEast) && (rateSouth == rateWest)) {
				if (rateNorth == rateSouth) {
					return WandererTask.getRandomNeighbour(agent);
				}
			}

			double sumRates = rateNorth + rateEast + rateSouth + rateWest;
			double endNorth = rateNorth / sumRates;
			double endEast = endNorth + rateEast / sumRates;
			double endSouth = endEast + rateSouth / sumRates;

			double randomPoint = Math.random();
			
			if (endNorth >= randomPoint) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
			}
			
			if ((endNorth < randomPoint) && (endEast >= randomPoint)) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.EAST);
			}
			
			if ((endEast < randomPoint) && (endSouth >= randomPoint)) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.SOUTH);
			}
			
			if ((endSouth < randomPoint) && (1 >= randomPoint)) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.WEST);
			}
			
			return WandererTask.getRandomNeighbour(agent);
		}
	}
}
