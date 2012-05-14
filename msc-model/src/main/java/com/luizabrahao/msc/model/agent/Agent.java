package com.luizabrahao.msc.model.agent;

import com.luizabrahao.msc.model.env.Node;

public interface Agent {
	/**
	 * Every agent must have an identifier, and this should be used in the
	 * hashCode() and equals().
	 * 
	 * @return String a unique identifier.
	 */
	public String getId();
	
	/**
	 * Returns agent's cast
	 * 
	 * @return Cast agent's cast
	 */
	public Cast getCast();
	
	/**
	 * The node that the agent is currently sat on.
	 * This must be thread safe
	 * 
	 * @param node Node a node
	 */
	public void setCurrentNode(Node node);
	
	/**
	 * Returns the node the agent is sat on.
	 * This must be thread safe
	 * 
	 * @return Node
	 */
	Node getCurrentCode();
}
