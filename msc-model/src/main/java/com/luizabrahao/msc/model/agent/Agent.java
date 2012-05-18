package com.luizabrahao.msc.model.agent;

import com.luizabrahao.msc.model.env.Node;

/**
 * The public API for every agent defined in the simulation.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface Agent {
	/**
	 * Every agent must have an identifier, and this should be used in the
	 * hashCode() and equals().
	 * 
	 * @return String a unique identifier.
	 */
	String getId();
	
	/**
	 * Returns agent's cast
	 * 
	 * @return Cast agent's cast
	 */
	AgentType getAgentType();
	
	/**
	 * The node that the agent is currently sat on.
	 * This must be thread safe
	 * 
	 * @param node Node a node
	 */
	void setCurrentNode(Node node);
	
	/**
	 * Returns the node the agent is sat on.
	 * This must be thread safe
	 * 
	 * @return Node
	 */
	Node getCurrentNode();
}