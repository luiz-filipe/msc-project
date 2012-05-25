package com.luizabrahao.msc.model.agent;

public abstract class AbstractAgentType implements AgentType {
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
