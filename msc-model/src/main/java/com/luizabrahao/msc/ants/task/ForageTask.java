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
	private static final long milisecondsToWait = 5;
	
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
			nodeToMoveTo.addAgent(agent);
			
			try {
				Thread.sleep(milisecondsToWait);
			} catch (InterruptedException e) {
				logger.trace("Agent '{}' interrupted while waiting.", agent.getId());
			}
		}
	}
	
	/**
	 * Uses pheromone trail to decide which node to move next. Each neighbour
	 * node has a weight, which is multiplied by the node's pheromone
	 * intensity. That gives the agent a stochastic behaviour, simulating
	 * external forces like wind that can sometimes take agents away from the
	 * optimal path. 
	 * 
	 * @param agent Agent that is going to move.
	 * @return Node to move to.
	 */
	public static Node getNodeToMoveTo(AntAgent agent) {
		double pheromoneNorth = 0;
		double pheromoneEast = 0;
		double pheromoneSouth = 0;
		double pheromoneWest = 0;
		
		if (agent.getMovingDirection() == null) {
			agent.setMovingDirection(Direction.SOUTH);
		}
		
		synchronized (agent.getCurrentNode()) {
		    // First we need to work out what are the neighbours nodes in
			// relation to the agent's current movement.
			Direction northOfTheAgent = Direction.NORTH;
		    Direction eastOfTheAgent = Direction.EAST;
		    Direction southOfTheAgent = Direction.SOUTH;
		    Direction westOfTheAgent = Direction.WEST;
		    
		    // Transformation of direction from being related to the
		    // grid to being related to the agent's moving direction.
		    // If the agent is moving north, nothing is needed to be done.
		    if (agent.getMovingDirection() == Direction.EAST) {
		    	northOfTheAgent = Direction.EAST;
			    eastOfTheAgent = Direction.SOUTH;
			    southOfTheAgent = Direction.WEST;
			    westOfTheAgent = Direction.NORTH;
		    
		    } else if (agent.getMovingDirection() == Direction.SOUTH) {
		    	northOfTheAgent = Direction.SOUTH;
			    eastOfTheAgent = Direction.WEST;
			    southOfTheAgent = Direction.NORTH;
			    westOfTheAgent = Direction.EAST;
		    
		    } else if (agent.getMovingDirection() == Direction.WEST) {
		    	northOfTheAgent = Direction.WEST;
			    eastOfTheAgent = Direction.NORTH;
			    southOfTheAgent = Direction.EAST;
			    westOfTheAgent = Direction.SOUTH;
		    }
		    
		    // Get the intensity of the pheromone of the neighbour nodes. The
		    // directions are all in relation to the agent movement direction
		    // and not to the grid.
		    PheromoneNode n = (PheromoneNode) agent.getCurrentNode().getNeighbour(northOfTheAgent);
		    pheromoneNorth = (n == null) ? 0 : n.getPheromoneIntensity();
		    
		    n = (PheromoneNode) agent.getCurrentNode().getNeighbour(eastOfTheAgent);
		    pheromoneEast = (n == null) ? 0 : n.getPheromoneIntensity();
		    
		    n = (PheromoneNode) agent.getCurrentNode().getNeighbour(southOfTheAgent);
		    pheromoneSouth = (n == null) ? 0 : n.getPheromoneIntensity();
		    
		    n = (PheromoneNode) agent.getCurrentNode().getNeighbour(westOfTheAgent);
		    pheromoneWest = (n == null) ? 0 : n.getPheromoneIntensity();
		    			
			final double rateNorth = pheromoneNorth * ForageTask.WEIGHT_NORTH;
			final double rateEast = pheromoneEast * ForageTask.WEIGHT_EAST;
			final double rateSouth = pheromoneSouth * ForageTask.WEIGHT_SOUTH;
			final double rateWest = pheromoneWest * ForageTask.WEIGHT_WEST;

			final double sumRates = rateNorth + rateEast + rateSouth + rateWest;
			final double endNorth = rateNorth / sumRates;
			final double endEast = endNorth + rateEast / sumRates;
			final double endSouth = endEast + rateSouth / sumRates;

			final double randomPoint = Math.random();
			
			if (endNorth >= randomPoint) {
				return agent.getCurrentNode().getNeighbour(northOfTheAgent);
			}
			
			if ((endNorth < randomPoint) && (endEast >= randomPoint)) {
				return agent.getCurrentNode().getNeighbour(eastOfTheAgent);
			}
			
			if ((endEast < randomPoint) && (endSouth >= randomPoint)) {
				return agent.getCurrentNode().getNeighbour(southOfTheAgent);
			}
			
			if ((endSouth < randomPoint) && (1 >= randomPoint)) {
				return agent.getCurrentNode().getNeighbour(westOfTheAgent);
			}
			
			return WandererTask.getRandomNeighbour(agent);
		}
	}
}