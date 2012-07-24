package com.luizabrahao.msc.ants.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(AntAgent.class);
	
	@GuardedBy("this") private Direction movingDirection;
	private double amountOfFoodCarring = 0;

	@Override public synchronized Direction getMovingDirection() { return movingDirection; }
	@Override public synchronized void setMovingDirection(Direction movingDirection) { this.movingDirection = movingDirection; }

	public AntAgent(String id, AntType agentType, Node currentNode, boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
	}

	@Override public AntType getAgentType() { return (AntType) super.getAgentType(); }
	
	@Override
	public Void call() {
		while (!Thread.currentThread().isInterrupted()) {
			// Running with FORAGE only for now.
			this.getTaskList().get(0).execute(this);
		}
		
		logger.info("{} is stoping...", this.getId());
		return null;
	}
	
	@Override
	public double collectFood(Agent foodSource, double amountToCollect) {
		this.amountOfFoodCarring = ((FoodSourceAgent) foodSource).collectFood(amountToCollect);
		
		return this.amountOfFoodCarring;
	}
	
	@Override public boolean isCarringFood() { return (amountOfFoodCarring == 0) ? true : false; }
	
	@Override
	public void incrementStimulusIntensity(final ChemicalCommStimulusType chemicalCommStimulusType) {
		PheromoneNode currentNode = (PheromoneNode) this.getCurrentNode();
		this.updateNeighbour(currentNode, chemicalCommStimulusType, 0);
		
		// if the chemical stimulus is punctual, that is, it does not spread
		// across to its nodes neighbours we don't need to to anything else.
		if (chemicalCommStimulusType.getRadius() == 0) {
			return;
		}
		
		// if radius == 1, only main rows and columns will be updated, so let's
		// call to only them to be updated and return after that, to save
		// computational resources as much as we can
		if (chemicalCommStimulusType.getRadius() == 1) {
			this.updateNeighbours(chemicalCommStimulusType, Direction.NORTH);
			this.updateNeighbours(chemicalCommStimulusType, Direction.EAST);
			this.updateNeighbours(chemicalCommStimulusType, Direction.SOUTH);
			this.updateNeighbours(chemicalCommStimulusType, Direction.WEST);

			return;
		} 
		
		// updates the main rows and columns
		this.updateNeighbours(chemicalCommStimulusType, Direction.NORTH);
		this.updateNeighbours(chemicalCommStimulusType, Direction.EAST);
		this.updateNeighbours(chemicalCommStimulusType, Direction.SOUTH);
		this.updateNeighbours(chemicalCommStimulusType, Direction.WEST);

		this.updateNeighbours(chemicalCommStimulusType, Direction.NORTH, Direction.EAST);
		this.updateNeighbours(chemicalCommStimulusType, Direction.NORTH, Direction.WEST);
		this.updateNeighbours(chemicalCommStimulusType, Direction.SOUTH, Direction.EAST);
		this.updateNeighbours(chemicalCommStimulusType, Direction.SOUTH, Direction.WEST);
	}
	
	private void updateNeighbours(final ChemicalCommStimulusType chemicalCommStimulusType, final Direction direction) {
		PheromoneNode currentNode = (PheromoneNode) this.getCurrentNode();
		
		for (int i = 0; i < chemicalCommStimulusType.getRadius(); i++) {
			currentNode = (PheromoneNode) currentNode.getNeighbour(direction);
			
			if (currentNode != null) {
				this.updateNeighbour(currentNode, chemicalCommStimulusType, i + 1);
			}
		}
	}
	
	private void updateNeighbours(final ChemicalCommStimulusType chemicalCommStimulusType, final Direction verticalDirection, final Direction horizontalDirection) {
		PheromoneNode currentLineNode = (PheromoneNode) this.getCurrentNode().getNeighbour(verticalDirection);
		PheromoneNode currentNode = currentLineNode;
		
		for (int i = 0; i < chemicalCommStimulusType.getRadius() - 1; i++) {
			for (int j = 0; j < chemicalCommStimulusType.getRadius() - 1; j++) {
				currentNode = (PheromoneNode) currentNode.getNeighbour(horizontalDirection);
				
				if (currentNode != null) {
					if (i + j < chemicalCommStimulusType.getRadius() - i) {
						this.updateNeighbour(currentNode, chemicalCommStimulusType, i + j + 1);
					}
				}
			}
			
			currentLineNode = (PheromoneNode) currentLineNode.getNeighbour(verticalDirection);
			currentNode = currentLineNode;
		}
	}
	
	private void updateNeighbour(final PheromoneNode node, final ChemicalCommStimulusType chemicalCommStimulusType, final int distanceFromCurrentNode) {
		if (node == null) {
			return;
		}
		
		if (distanceFromCurrentNode == 0) {
			node.getCommunicationStimulus(chemicalCommStimulusType).increaseIntensity(this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()));
			logger.trace("Node {} updated with {}", node.getId(), this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()));
			
			return;
		}
		
		node.getCommunicationStimulus(chemicalCommStimulusType).increaseIntensity(this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()) / distanceFromCurrentNode);
		logger.trace("Node {} updated with {}", node.getId(), this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()) / distanceFromCurrentNode);
	}
}
