package com.luizabrahao.msc.test.mock;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.agent.AgentType;
import com.luizabrahao.msc.model.env.Node;

public class TestAgent extends AbstractAgent {

	public TestAgent(String id, AgentType agentType, Node currentNode,
			boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
	}

	@Override
	public Void call() throws Exception {
		throw new RuntimeException("You cannot use TestAgent as java tasks");
	}
}
