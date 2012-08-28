package com.luizabrahao.msc.ants.task;

import net.jcip.annotations.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.agent.AntNestAgent;
import com.luizabrahao.msc.ants.agent.AntNestType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;

/**
 * This task is executed by ants when they have detected any sort of danger and
 * the most important thing to do is to run back to the nest and hide. When ants
 * do find their nest, they stay there.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public class FindAndHideInNest extends AbstractTask implements AntTask {
	private static final Logger logger = LoggerFactory.getLogger(FindAndHideInNest.class);
	public static final String NAME = "ant:task:find-home-and-hide";

	public static final double WEIGHT_NORTH = 0.40;
	public static final double WEIGHT_EAST = 0.25;
	public static final double WEIGHT_SOUTH = 0.10;
	public static final double WEIGHT_WEST = 0.25;

	public FindAndHideInNest() {
		super(FindAndHideInNest.NAME);
	}
	
	@Override public double getNeighbourWeightNorth() { return WEIGHT_NORTH; }
	@Override public double getNeighbourWeightEast() { return WEIGHT_EAST; }
	@Override public double getNeighbourWeightSouth() { return WEIGHT_SOUTH; }
	@Override public double getNeighbourWeightWest() { return WEIGHT_WEST; }
	
	private AntNestAgent getNest(Node node) {
		synchronized (node.getAgents()) {
			for (Agent agent : node.getAgents()) {
				if (agent.getAgentType() == AntNestType.TYPE) {
					return (AntNestAgent) agent;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void execute(Agent agent) {
		AntAgent ant = (AntAgent) agent;
		ant.setCurrentTask(this.name);
		
		AntNestAgent nest = this.getNest(agent.getCurrentNode());
		
		if (nest != null) {
			// The agent has reached nest... Do something...
			logger.debug("{} is hidding in the nest", agent.getId());
			return;
		}
		
		Node nodeToMoveTo = ant.getLatestNodeFromMemory();
		
		// if the agent runs out of memory
		if (nodeToMoveTo == null) {
			Direction d = AntTaskUtil.getDirectionToMoveTo(ant, ForageStimulusType.TYPE);
			nodeToMoveTo = agent.getCurrentNode().getNeighbour(d);
			
			if (nodeToMoveTo == null) {
				Direction newDirection = this.findRandomDirectionToMove(ant);
				nodeToMoveTo = ant.getCurrentNode().getNeighbour(newDirection);
				ant.setMovingDirection(newDirection);
			}
		}
		
		ant.incrementStimulusIntensityMultipliedByFactor(ForageStimulusType.TYPE, 2);
		nodeToMoveTo.addAgent(agent);
	}

	private Direction findRandomDirectionToMove(AntAgent agent) {
		Direction d = AntTaskUtil.getDirectionToMoveTo(agent, ForageStimulusType.TYPE);
		
		Node n = agent.getCurrentNode().getNeighbour(d);
		
		if (n == null) {
			return findRandomDirectionToMove(agent);
		}
		
		logger.debug("{} has changed its direction to {}", agent.getId(), d);
		
		return d;
	}
}
