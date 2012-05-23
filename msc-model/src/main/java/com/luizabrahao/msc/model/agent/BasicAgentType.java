package com.luizabrahao.msc.model.agent;

import net.jcip.annotations.Immutable;

@Immutable
public class BasicAgentType implements AgentType {
	protected final String name;
	
	public BasicAgentType(String name) {
		this.name = name;
	}
	
	@Override public String getName() { return this.name; }
}
