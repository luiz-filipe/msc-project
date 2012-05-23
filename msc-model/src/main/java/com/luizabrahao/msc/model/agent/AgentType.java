package com.luizabrahao.msc.model.agent;

/**
 * AgentType is a basic data type that holds info about the the type of the
 * agent. Each agent must have a AgentType object associated to it. This  can
 * be useful when comparing agents.
 * 
 * The reason to not use a string or enumeration is that more information about
 * the agent type might become necessary at some point. For examples, an 
 * AgentType could define a colour, that all agents of that class have.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface AgentType {
	String getName();
}