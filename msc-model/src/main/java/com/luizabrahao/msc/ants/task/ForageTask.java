package com.luizabrahao.msc.ants.task;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;
import com.luizabrahao.msc.model.task.WandererTask;

public class ForageTask extends AbstractTask {
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
			ForageTask.getNodeToMoveTo((AntAgent) agent).addAgent(agent);
		}
		
	}
	
	private static Node getNodeToMoveTo(AntAgent agent) {
		double pheromoneNorth = 0;
		double pheromoneEast = 0;
		double pheromoneSouth = 0;
		double pheromoneWest = 0;
		
		synchronized (agent.getCurrentNode()) {
			if (agent.getNeighbourInRelationToAgentOrientation(Direction.NORTH) != null) {
				pheromoneNorth = ((PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.NORTH)).getPheromoneIntensity();
			}
			
			if (agent.getNeighbourInRelationToAgentOrientation(Direction.EAST) != null) {
				pheromoneNorth = ((PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.EAST)).getPheromoneIntensity();
			}
			
			if (agent.getNeighbourInRelationToAgentOrientation(Direction.SOUTH) != null) {
				pheromoneNorth = ((PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.SOUTH)).getPheromoneIntensity();
			}
			
			if (agent.getNeighbourInRelationToAgentOrientation(Direction.WEST) != null) {
				pheromoneNorth = ((PheromoneNode) agent.getNeighbourInRelationToAgentOrientation(Direction.WEST)).getPheromoneIntensity();
			}
			
			double rateNorth = pheromoneNorth * ForageTask.WEIGHT_NORTH;
			double rateEast = pheromoneEast * ForageTask.WEIGHT_EAST;
			double rateSouth = pheromoneSouth * ForageTask.WEIGHT_SOUTH;
			double rateWest = pheromoneWest * ForageTask.WEIGHT_WEST;
			
			double sumRates = rateNorth + rateEast + rateSouth + rateWest;
			
			double probabilityNorth = rateNorth / sumRates;
			double probabilityEast = rateEast / sumRates;
			double probabilitySouth = rateSouth / sumRates;
			double probabilityWest = rateWest / sumRates;
			
			double randomPoint = Math.random();
			
			if (probabilityNorth <= randomPoint) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.NORTH);
			
			} else if ((probabilityNorth > randomPoint) && (probabilityNorth + probabilityEast <= randomPoint)) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.EAST);
			
			} else if ((probabilityNorth + probabilityEast > randomPoint) && (probabilityNorth + probabilityEast + probabilitySouth <= randomPoint)) {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.EAST);
			
			} else {
				return agent.getNeighbourInRelationToAgentOrientation(Direction.WEST);
			}
			
		}
	}
}
