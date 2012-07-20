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
		PheromoneNode n = (PheromoneNode) this.getCurrentNode();
		n.incrementStimulusIntensity(chemicalCommStimulusType, this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()));
		
		// if the chemical stimulus is punctual, that is, it does not spread
		// across to its nodes neighbours we don't need to to anything else.
		if (chemicalCommStimulusType.getRadius() == 0) {
			return;
		}
		
		// update the main row
		this.updateRow((PheromoneNode) this.getCurrentNode(), chemicalCommStimulusType, chemicalCommStimulusType.getRadius());
		
		PheromoneNode nodeToUpdate = (PheromoneNode) this.getCurrentNode();
		int numberOfNeighbours = chemicalCommStimulusType.getRadius() - 1;
		
		// update the north part of distribution
		for (int i = 0; i < chemicalCommStimulusType.getRadius(); i++) {
			nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.NORTH);
			this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, i + 1);
			
			this.updateRow(nodeToUpdate, chemicalCommStimulusType, numberOfNeighbours);
			
			numberOfNeighbours = numberOfNeighbours - 1;
		}
		
		nodeToUpdate = (PheromoneNode) this.getCurrentNode();
		numberOfNeighbours = chemicalCommStimulusType.getRadius() - 1;
		
		// update the north part of distribution
		for (int i = 0; i < chemicalCommStimulusType.getRadius(); i++) {
			nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.SOUTH);
			this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, i + 1);
			
			this.updateRow(nodeToUpdate, chemicalCommStimulusType, numberOfNeighbours);
			
			numberOfNeighbours = numberOfNeighbours - 1;
		}
	}
	
	private void updateRow(final PheromoneNode nodeToStartFrom, final ChemicalCommStimulusType chemicalCommStimulusType, final int numberOfNeighboursEachside) {
		// updates the main line direction: east
		PheromoneNode nodeToUpdate = (PheromoneNode) nodeToStartFrom;
		for (int i = 0; i < numberOfNeighboursEachside; i++) {
			nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.EAST);
			
			if (nodeToUpdate == null) {
				break;
			}
			
			this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, i + 1);
		}
		
		// update the main line direction: west
		nodeToUpdate = (PheromoneNode) nodeToStartFrom;
		for (int i = 0; i < numberOfNeighboursEachside; i++) {
			nodeToUpdate = (PheromoneNode) nodeToUpdate.getNeighbour(Direction.WEST);
			
			if (nodeToUpdate == null) {
				break;
			}
			
			this.updateNeighbour(nodeToUpdate, chemicalCommStimulusType, i + 1);
		}
		
	}
	
	private void updateNeighbour(final PheromoneNode node, final ChemicalCommStimulusType chemicalCommStimulusType, final int distanceFromCurrentNode) {
		if (node == null) {
			return;
		}
		
		if (distanceFromCurrentNode == 0) {
			node.getCommunicationStimulus(chemicalCommStimulusType).increaseIntensity(this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()));
			logger.debug("Node {} updated with {}", node.getId(), this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()));
			
			return;
		}
		
		node.getCommunicationStimulus(chemicalCommStimulusType).increaseIntensity(this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()) / distanceFromCurrentNode);
		logger.debug("Node {} updated with {}", node.getId(), this.getAgentType().getStimulusIncrement(chemicalCommStimulusType.getName()) / distanceFromCurrentNode);
	}
	
	
}
