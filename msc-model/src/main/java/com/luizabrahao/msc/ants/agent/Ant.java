package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
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
	void incrementStimulusIntensity(ChemicalCommStimulusType chemicalCommStimulusType);
	double collectFood(Agent foodSource, double amountToCollect);
	boolean isCarringFood();
	void addToMemory(Node node);
	FoodSourceAgent findFoodSource();
	Node getLatestNodeFromMemory();
}
