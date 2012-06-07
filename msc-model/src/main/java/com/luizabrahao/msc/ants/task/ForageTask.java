package com.luizabrahao.msc.ants.task;

import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;
import com.luizabrahao.msc.model.task.WandererTask;

public class ForageTask extends AbstractTask {
	public static final String NAME = "Forage"; 

	public ForageTask() {
		super(ForageTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		while (true) {
			ForageTask.getNodeToMoveTo(agent).addAgent(agent);
		}
		
	}
	
	private static Node getNodeToMoveTo(Agent agent) {
		Direction d = Direction.SOUTH;
		double bestPheromone = 0;
		double pheromoneNorth = 0;
		double pheromoneEast = 0;
		double pheromoneSouth = 0;
		double pheromoneWest = 0;
		
		synchronized (agent.getCurrentNode()) {
			if (agent.getCurrentNode().getNeighbour(Direction.NORTH) != null) {
				pheromoneNorth = ((PheromoneNode) agent.getCurrentNode().getNeighbour(Direction.NORTH)).getPheromoneIntensity();
			}
			
			if (agent.getCurrentNode().getNeighbour(Direction.EAST) != null) {
				pheromoneEast = ((PheromoneNode) agent.getCurrentNode().getNeighbour(Direction.EAST)).getPheromoneIntensity();
			}
			
			if (agent.getCurrentNode().getNeighbour(Direction.SOUTH) != null) {
				pheromoneSouth = ((PheromoneNode) agent.getCurrentNode().getNeighbour(Direction.SOUTH)).getPheromoneIntensity();
			}
			
			if (agent.getCurrentNode().getNeighbour(Direction.WEST) != null) {
				pheromoneWest = ((PheromoneNode) agent.getCurrentNode().getNeighbour(Direction.WEST)).getPheromoneIntensity();
			}
			
			if (pheromoneNorth > bestPheromone) {
				d = Direction.NORTH;
			}
			
			if (pheromoneEast > bestPheromone) {
				d = Direction.EAST;
			}
			
			if (pheromoneSouth > bestPheromone) {
				d = Direction.SOUTH;
			}
			
			if (pheromoneWest > bestPheromone) {
				d = Direction.WEST;
			}
			
			if (Double.compare(bestPheromone, pheromoneNorth) == 0) {
				if (Double.compare(bestPheromone, pheromoneEast) == 0) {
					if (Double.compare(bestPheromone, pheromoneSouth) == 0) {
						if (Double.compare(bestPheromone, pheromoneWest) == 0) {
							return WandererTask.getRandomNeighbour(agent);
						}
					}
				}
			}
		}
		
		return agent.getCurrentNode().getNeighbour(d);
	}

}
