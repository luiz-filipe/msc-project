package com.luizabrahao.msc.ants.agent;

import net.jcip.annotations.GuardedBy;

import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

/**
 * Defines an agent that represents a ant. The movingDirection field represents
 * the direction the agent is moving in relation to the grid of nodes. This
 * direction is used when the algorithm is calculating the probabilities of the
 * agent selecting what node it will move next.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public class AntAgent extends TaskAgent {
	@GuardedBy("this") private Direction movingDirection;

	public synchronized Direction getMovingDirection() { return movingDirection; }
	public synchronized void setMovingDirection(Direction movingDirection) {
		this.movingDirection = movingDirection;
	}

	public AntAgent(String id, TaskAgentType agentType, Node currentNode, boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
	}

	/**
	 * Imagine that you are looking the grid from the top. So if the agent is
	 * moving south towards south of the grid, the next node south to the node
	 * the agent is currently at is going to be the north node in relation to
	 * the agent.
	 * 
	 * It might be better to refactor that all movements were in relation to 
	 * the grid.
	 * 
	 * @param direction Direction the neighbour is in relation to the agent
	 * @return Node Neighbour node
	 */
	public Node getNeighbourInRelationToAgentOrientation(Direction direction) {
		// if the agent is moving north, there is no transformation to be done.
		if (movingDirection == Direction.NORTH) {
			return this.getCurrentNode().getNeighbour(direction);
		}
		
		if (movingDirection == Direction.EAST) {
			switch (direction) {
				case NORTH:
					return this.getCurrentNode().getNeighbour(Direction.EAST);
				case EAST:
					return this.getCurrentNode().getNeighbour(Direction.SOUTH);
				case SOUTH:
					return this.getCurrentNode().getNeighbour(Direction.WEST);
				case WEST:
					return this.getCurrentNode().getNeighbour(Direction.NORTH);
			}
		}
	
		if (movingDirection == Direction.SOUTH) {
			switch (direction) {
				case NORTH:
					return this.getCurrentNode().getNeighbour(Direction.SOUTH);
				case EAST:
					return this.getCurrentNode().getNeighbour(Direction.WEST);
				case SOUTH:
					return this.getCurrentNode().getNeighbour(Direction.NORTH);
				case WEST:
					return this.getCurrentNode().getNeighbour(Direction.EAST);
			}
		}
		
		if (movingDirection == Direction.WEST) {
			switch (direction) {
				case NORTH:
					return this.getCurrentNode().getNeighbour(Direction.WEST);
				case EAST:
					return this.getCurrentNode().getNeighbour(Direction.NORTH);
				case SOUTH:
					return this.getCurrentNode().getNeighbour(Direction.EAST);
				case WEST:
					return this.getCurrentNode().getNeighbour(Direction.SOUTH);
			}
		}
		
		return null;
	}

	@Override
	public void run() {
		// Running with FORAGE only for now.
		this.getTaskList().get(0).execute(this);
	}
	
	

}
