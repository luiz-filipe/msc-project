package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.env.Node;

public class PredatorAgent extends AbstractAgent {

	public PredatorAgent(String id, Node currentNode) {
		super(id, PredatorAgentType.TYPE, currentNode, false);
	}

	@Override
	public Void call() throws Exception {
		// maybe kill ants that are in the same node?
		throw new RuntimeException("Predators are not able to execute any task.");
	}
}
