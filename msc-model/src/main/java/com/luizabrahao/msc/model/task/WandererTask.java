package com.luizabrahao.msc.model.task;


import java.util.Random;

import net.jcip.annotations.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;

/**
 * A simple implementation of a task. When executing this task the agent
 * wanders the environment without worrying about anything else. The task picks
 * up a random direction and the agent moves to that neighbour node.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@Immutable
public class WandererTask extends AbstractTask {
	private static final Logger logger = LoggerFactory.getLogger(WandererTask.class);
	public static final String NAME = "Wanderer";
	private static final Random rand = new Random();

	public WandererTask() {
		super(WandererTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		logger.info("agent {} started task: {}", agent.getId(), WandererTask.NAME);
		
		while (true) {
			Direction directionToMove = WandererTask.getRandomDirection(agent);
			agent.getCurrentNode().getNeighbour(directionToMove).addAgent(agent);
		}
	}
	
	/**
	 * This method returns one of the neighbours of the agent's current node.
	 * It chooses one direction at random, if there is no neighbour on that
	 * direction, it tries again until one is found.
	 *
	 * Note that his method would fall into a deadlock if the agent's current
	 * node is not connected to any other node.
	 * 
	 * @param agent Agent that is performing the task
	 * @return Node that the agent is going to move to.
	 */
	public static Direction getRandomDirection(Agent agent) {
		Logger logger = LoggerFactory.getLogger(WandererTask.class);
		
		// 4 directions
		int randomDirection = WandererTask.rand.nextInt(4);
		Direction direction = null;
		
		switch (randomDirection) {
			case 0:
				direction = Direction.NORTH;
				break;
			
			case 1:
				direction = Direction.EAST;
				break;
			
			case 2:
				direction = Direction.SOUTH;
				break;
			
			case 3:
				direction = Direction.WEST;
				break;
		}
		
		return direction;
	}
}
