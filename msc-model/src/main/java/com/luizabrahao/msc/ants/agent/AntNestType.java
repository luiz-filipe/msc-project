package com.luizabrahao.msc.ants.agent;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.agent.AgentType;

/**
 * Define the type of agent that identify ant nests.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public enum AntNestType implements AgentType {
	TYPE;
	
	private static final String name = "ant:agent:nest";
	
	@Override public String getName() { return name; }
}
