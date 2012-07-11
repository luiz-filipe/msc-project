package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;

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
	void depositPheromone(PheromoneNode node);
	double collectFood(Agent foodSource, double amountToCollect);
	boolean isCarringFood();
}
