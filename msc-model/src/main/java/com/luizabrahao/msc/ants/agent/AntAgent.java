package com.luizabrahao.msc.ants.agent;

import net.jcip.annotations.GuardedBy;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

/**
 * Defines an agent that represents a ant. The movingDirection field represents
 * the direction the agent is moving in relation to the grid of nodes. This
 * direction is used when the algorithm is calculating the probabilities of the
 * agent selecting what node it will move next.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * @see ForageTask
 *
 */
public class AntAgent extends TaskAgent implements Ant {
	@GuardedBy("this") private Direction movingDirection;
	private double amountOfFoodCarring = 0;

	@Override public synchronized Direction getMovingDirection() { return movingDirection; }
	@Override public synchronized void setMovingDirection(Direction movingDirection) { this.movingDirection = movingDirection; }

	public AntAgent(String id, AntType agentType, Node currentNode, boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
	}

	@Override public AntType getAgentType() { return (AntType) super.getAgentType(); }
	
	@Override
	public Void call() throws Exception {
		while (true) {
			// Running with FORAGE only for now.
			this.getTaskList().get(0).execute(this);
		}
	}
	
	@Override
	public double collectFood(Agent foodSource, double amountToCollect) {
		this.amountOfFoodCarring = ((FoodSourceAgent) foodSource).collectFood(amountToCollect);
		
		return this.amountOfFoodCarring;
	}
	
	@Override public boolean isCarringFood() { return (amountOfFoodCarring == 0) ? true : false; }
	
	@Override
	public void incrementStimulusIntensity(ChemicalCommStimulusType chemicalCommStimulusType) {
		// update current node
		PheromoneNode n = (PheromoneNode) this.getCurrentNode();
		n.incrementStimulusIntensity(chemicalCommStimulusType, this.getAgentType().getStimulusIncrement(chemicalCommStimulusType));

		// if the chemical stimulus is pontual, that is, it does not spread
		// to the nodes neighbours
		if (chemicalCommStimulusType.getRadius() == 0) {
			return;
		}
		
		int distanceToUpdate = chemicalCommStimulusType.getRadius();
		PheromoneNode nodeToUpdate = (PheromoneNode) this.getCurrentNode();
		
		// this loop updates the current line and all above the current node
		for (int i = 0; i < chemicalCommStimulusType.getRadius(); i++) {
			if (nodeToUpdate == null) {
				return;
			}
			
			distanceToUpdate = distanceToUpdate - i;
			
			// EAST
			for (int j = 0; j < distanceToUpdate; j++) {
				this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, j);
				nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.EAST);
			}
			
			// WEST
			for (int j = 0; j < distanceToUpdate; j++) {
				this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, j);
				nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.WEST);
			}
			
			nodeToUpdate = (PheromoneNode) this.getCurrentNode().getNeighbour(Direction.NORTH);
		}
		
		distanceToUpdate = chemicalCommStimulusType.getRadius();
		nodeToUpdate = (PheromoneNode) this.getCurrentNode().getNeighbour(Direction.SOUTH);
		
		// this loop updates the nodes bellow the current node's line
		for (int i = 0; i < chemicalCommStimulusType.getRadius(); i++) {
			if (nodeToUpdate == null) {
				return;
			}
					
			distanceToUpdate = distanceToUpdate - i;
					
			// EAST
			for (int j = 0; j < distanceToUpdate; j++) {
				this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, j);
				nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.EAST);
			}
					
			// WEST
			for (int j = 0; j < distanceToUpdate; j++) {
				this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, j);
				nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.WEST);
			}
					
			nodeToUpdate = (PheromoneNode) this.getCurrentNode().getNeighbour(Direction.SOUTH);
		}

	}
	
	private void updateNeighbour(final PheromoneNode node, final ChemicalCommStimulusType chemicalCommStimulusType, final int distanceFromCurrentNode) {
		if (node == null) {
			return;
		}
		
		// TODO it is incrementing by 1 for testing.
		node.getCommunicationStimulus(chemicalCommStimulusType).increaseIntensity(1);
	}
	
	
}
