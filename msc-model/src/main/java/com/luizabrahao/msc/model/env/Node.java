package com.luizabrahao.msc.model.env;

import java.util.List;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.annotation.FrameworkExclusive;
import com.luizabrahao.msc.model.annotation.ThreadSafetyBreaker;

/**
 * This class represents a piece of the environment, one could say it is a
 * infinitesimal representation of the environment. A node as a representation
 * of space is able to accommodate agents.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface Node {
	/**
	 * Each agent has an unique identifier, this is going to be used during the
	 * data analysis.
	 * @return
	 */
	String getId();
	
	/**
	 * Adds a agent to this node, it takes care of both directions connecting
	 * both node to the agent and the agent to the node.
	 * 
	 * @param agent Agent that is going to be added to the node.
	 */
	void addAgent(Agent agent);
	
	/**
	 * Returns the neighbour node of the specified direction. In case the node
	 * is part of the boundary in a determined direction it will not have any
	 * neighbour in that direction, so null will be returned instead.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method should be used only to transverse the
	 * nodes graph. The reason for not making this method thread-safe is
	 * performance. This method is likely to be called very often during the
	 * simulations, so it wouldn't make sense to make it thread-safe if there
	 * are no plans to use dynamic environments.
	 * 
	 * @param direction Direction of the neighbour node related to the current
	 *        object.
	 * @return Node neighbour node.
	 */
	@ThreadSafetyBreaker
	Node getNeighbour(Direction direction);
	
	/**
	 * This method should not be called directly from your code. As the
	 * neighbours node are not exposed explicitly, this method creates the
	 * means to set a neighbour in a particular direction.
	 * 
	 * DO NOT CALL this method in your code, use setNeighbours instead, it
	 * makes sure both of your node objects are liked to each other.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method is to be used only during the environment
	 * setup.
	 * 
	 * @param direction Direction of the neighbour node related to the current
	 *        object.
	 * @param node Node neighbour node.
	 */
	@FrameworkExclusive @ThreadSafetyBreaker
	void setNeighbour(Direction direction, Node node);
	
	/**
	 * Firstly this set the node passed as argument as the neighbour of the
	 * instance node, after that, it does the same for the neighbour node but
	 * in the opposite direction.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method should be used only to transverse the
	 * nodes graph. The reason for not making this method thread-safe is
	 * performance. This method is likely to be called very often during the
	 * simulations, so it wouldn't make sense to make it thread-safe if there
	 * are no plans to use dynamic environments.
	 * 
	 * @param direction Direction of the neighbour node related to the current
	 *        object.
	 * @param node Node neighbour node.
	 */
	@FrameworkExclusive @ThreadSafetyBreaker
	void setNeighbours(Direction direction, Node node);
	
	List<Agent> getAgents();
	
	void addAgentStartingHere(Agent agent);
}