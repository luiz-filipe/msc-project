package com.luizabrahao.msc.model.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.agent.Agent;

/**
 * This class is the basic implementation of Node. It hold references to
 * neighbour nodes and utility methods to navigate through them.
 * 
 * Note that this class is thread-safe as far as the agents are concerned. The
 * methods getNeighbour, setNeighbour and setNeighbours do expose the neighbour
 * nodes, but they were deliberately left without synchronisation because they
 * must be used only at setup time, that is, the environment does not change
 * after the simulation starts. getNeighbour is extensively used throughout the
 * simulation, so the overhead added by the synchronisation would not pay off.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public class BasicNode implements Node {
	private final Logger logger = LoggerFactory.getLogger(BasicNode.class);
	
	private final String id;
	protected Node north = null;
	protected Node east = null;
	protected Node south = null;
	protected Node west = null;
	@GuardedBy("this") protected List<Agent> agents = null;
	
	public BasicNode(String id) {
		this.id = id;
	}
		
	@Override public List<Agent> getAgents() { return agents; }
	@Override public String getId() { return id; }
	
	/**
	 * This needs to be synchronised because the agents list is lazily
	 * initialised. This time it was chosen to pay the price the
	 * synchronisation adds in order to save memory allocation.
	 */
	@Override
	public void addAgent(Agent agent) {
		synchronized(this) {
			if (agents == null) {
				agents = Collections.synchronizedList(new ArrayList<Agent>());
			}
		}
		
		synchronized(agents) {
			this.agents.add(agent);
			logger.debug("{}: agent {} moved here.", this.getId(), agent.getId());
		}
		
		// I'm not sure if I should leave this inside or outside the
		// synchronisation block above, i'm leaving outside now because I think
		// if I leave inside the method will use the wrong lock and Agent is
		// thread-safe.
		agent.setCurrentNode(this);
	}
	
	/**
	 * Returns the neighbour node in the specified direction. This method is
	 * not thread-safe, but it was decided to leave so as it will not cause any
	 * issue when used in environment that don't change during the simulation.
	 */
	@Override
	public Node getNeighbour(Direction direction) {
		switch (direction) {
			case NORTH:
				return this.north;
			case EAST:
				return this.east;
			case SOUTH:
				return this.south;
			case WEST:
				return this.west;
			
			default:
				return null;
		}
	}
	
	/**
	 * Should not be called directly from user code. It is used to expose
	 * neighbours indirectly and is not thread-safe.
	 */
	@Override
	public void setNeighbour(Direction direction, Node node) {
		switch (direction) {
			case NORTH:
				this.north = node;
				break;
			case EAST:
				this.east = node;
				break;
			case SOUTH:
				this.south = node;
				break;
			case WEST:
				this.west = node;
				break;
		}
	}
	
	/**
	 * Should be used only at environment setup time as it is not thread-safe
	 */
	@Override
	public void setNeighbours(Direction direction, Node node) {
		switch (direction) {
			case NORTH:
				this.north = node;
				node.setNeighbour(Direction.SOUTH, this);
				break;
			
			case EAST:
				this.east = node;
				node.setNeighbour(Direction.WEST, this);
				break;
			
			case SOUTH:
				this.south = node;
				node.setNeighbour(Direction.NORTH, this);
				break;
				
			case WEST:
				this.west = node;
				node.setNeighbour(Direction.EAST, this);
				break;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((east == null) ? 0 : east.hashCode());
		result = prime * result + ((north == null) ? 0 : north.hashCode());
		result = prime * result + ((south == null) ? 0 : south.hashCode());
		result = prime * result + ((west == null) ? 0 : west.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicNode other = (BasicNode) obj;
		if (east == null) {
			if (other.east != null)
				return false;
		} else if (!east.equals(other.east))
			return false;
		if (north == null) {
			if (other.north != null)
				return false;
		} else if (!north.equals(other.north))
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		if (west == null) {
			if (other.west != null)
				return false;
		} else if (!west.equals(other.west))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicNode [id=" + id + "]";
	}
}
