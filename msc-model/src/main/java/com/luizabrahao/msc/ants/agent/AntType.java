package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.model.agent.TaskAgentType;

public interface AntType extends TaskAgentType {
	/**
	 * How much pheromone is increased when the agent deposits pheromone
	 * onto a node

	 * @return Double the amount of pheromone that will be added to the node.
	 */
	double getPheromoneIncrement();
	
	void depositPheromone(PheromoneNode node);
}
