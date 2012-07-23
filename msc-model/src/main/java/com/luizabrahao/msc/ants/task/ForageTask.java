package com.luizabrahao.msc.ants.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;

public class ForageTask extends AbstractTask implements AntTask {
	private static final Logger logger = LoggerFactory.getLogger(ForageTask.class);
	private static final long milisecondsToWait = 5;
	public static final String NAME = "ant:task:forage";
	
	public static final double WEIGHT_NORTH = 0.40;
	public static final double WEIGHT_EAST = 0.25;
	public static final double WEIGHT_SOUTH = 0.10;
	public static final double WEIGHT_WEST = 0.25;

	public ForageTask() {
		super(ForageTask.NAME);
	}

	@Override public double getNeighbourWeightNorth() { return WEIGHT_NORTH; }
	@Override public double getNeighbourWeightEast() { return WEIGHT_EAST; }
	@Override public double getNeighbourWeightSouth() { return WEIGHT_SOUTH; }
	@Override public double getNeighbourWeightWest() { return WEIGHT_WEST; }
	
	@Override
	public void execute(Agent agent) {
		AntAgent a = (AntAgent) agent;
		
		Direction d = AntTaskUtil.getDirectionToMoveTo(a, ForageStimulusType.TYPE);
		Node nodeToMoveTo = agent.getCurrentNode().getNeighbour(d);
		
		if (nodeToMoveTo == null) {
			Direction newDirection = this.findRandomDirectionToMove(a);
			nodeToMoveTo = a.getCurrentNode().getNeighbour(newDirection);
			a.setMovingDirection(newDirection);
		}
		
		a.incrementStimulusIntensity(ForageStimulusType.TYPE);

		
		nodeToMoveTo.addAgent(agent);

		try {
			Thread.sleep(milisecondsToWait);
		} catch (InterruptedException e) {
			logger.trace("Agent '{}' interrupted while waiting.", agent.getId());
		}
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