package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

/**
 * Defines the basic API that must be implemented by any agent that represent
 * any ant.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface Ant {
	Direction getMovingDirection();
	void setMovingDirection(Direction movingDirection);
	void depositPheromone(Node node);
	double collectFood(Agent foodSource, double amountToCollect);
	boolean isCarringFood();
}
