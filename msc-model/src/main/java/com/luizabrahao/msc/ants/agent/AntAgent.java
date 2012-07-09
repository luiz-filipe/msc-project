package com.luizabrahao.msc.ants.agent;

import net.jcip.annotations.GuardedBy;

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

	@Override
	public Void call() throws Exception {
		while (true) {
			// Running with FORAGE only for now.
			this.getTaskList().get(0).execute(this);
		}
	}
	
	@Override
	public void depositPheromone(Node node) {
		((AntType) this.getAgentType()).depositPheromone((PheromoneNode) node);
	}
	
	@Override
	public double collectFood(Agent foodSource, double amountToCollect) {
		this.amountOfFoodCarring = ((FoodSourceAgent) foodSource).collectFood(amountToCollect);
		
		return this.amountOfFoodCarring;
	}
	
	@Override public boolean isCarringFood() { return (amountOfFoodCarring == 0) ? true : false; }
}
