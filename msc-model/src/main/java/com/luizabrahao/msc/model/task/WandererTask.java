package com.luizabrahao.msc.model.task;


import net.jcip.annotations.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;

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
	private final Logger logger = LoggerFactory.getLogger(WandererTask.class);
	public static final String NAME = "Wanderer"; 

	public WandererTask() {
		super(WandererTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		logger.info("agent {} started task: {}", agent.getId(), WandererTask.NAME);
		
		while (true) {
			WandererTask.getRandomNeighbour(agent).addAgent(agent);
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
	public static Node getRandomNeighbour(Agent agent) {
		Logger logger = LoggerFactory.getLogger(WandererTask.class);
		
		int direction = (int) (Math.random() * 4);
		Node nextNode = null;
		
		switch (direction) {
			case 0:
				nextNode = agent.getCurrentNode().getNeighbour(Direction.NORTH);
				break;
			
			case 1:
				nextNode = agent.getCurrentNode().getNeighbour(Direction.EAST);
				break;
			
			case 2:
				nextNode = agent.getCurrentNode().getNeighbour(Direction.SOUTH);
				break;
			
			case 3:
				nextNode = agent.getCurrentNode().getNeighbour(Direction.WEST);
				break;
		}
		
		if (nextNode == null) {
			logger.debug("{}: there is no neighbour on direction {}, trying again...", agent.getId(), direction);
			nextNode = WandererTask.getRandomNeighbour(agent);
		}
		
		return nextNode;
	}
	
	
}
