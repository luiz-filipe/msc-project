package com.luizabrahao.msc.test.mock;

import com.luizabrahao.msc.model.agent.AgentType;

public enum TestAgentType implements AgentType {
	TYPE;

	private static final String name = "agent:type:test";
	
	@Override
	public String getName() {
		return name;
	}

}
