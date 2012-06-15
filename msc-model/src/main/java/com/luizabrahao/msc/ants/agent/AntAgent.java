package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

public class AntAgent extends TaskAgent {
	private Direction movingDirection;

	public Direction getMovingDirection() { return movingDirection; }

	public AntAgent(String id, TaskAgentType agentType, Node currentNode) {
		super(id, agentType, currentNode);
	}

	/**
	 * 
	 * 
	 * @param direction
	 * @return
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
				default:
					return null;
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
				default:
					return null;
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
				default:
					return null;
			}
		}
		
		return null;
	}

	@Override
	public void run() {
		this.getTaskList().get(0).execute(this);
	}

}
