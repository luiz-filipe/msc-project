package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.model.agent.AgentType;

public enum AntNestType implements AgentType {
	TYPE;
	
	private static final String name = "ant:agent:nest";
	
	@Override public String getName() { return name; }
}
