package com.luizabrahao.msc.ants.task;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.model.env.Node;

public interface AntTask {
	Node getNodeToMoveTo(AntAgent agent);
	double getNeighbourWeightNorth();
	double getNeighbourWeightEast();
	double getNeighbourWeightSouth();
	double getNeighbourWeightWest();

}
