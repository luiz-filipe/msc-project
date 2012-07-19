package com.luizabrahao.msc.ants.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.AttackStimulusType;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;

public class AttackTask extends AbstractTask implements AntTask {
	private static final Logger logger = LoggerFactory.getLogger(AttackTask.class);
	private static final long milisecondsToWait = 5;
	public static final String NAME = "Attack";
	public static final double WEIGHT_NORTH = 0.30;
	public static final double WEIGHT_EAST = 0.30;
	public static final double WEIGHT_SOUTH = 0.10;
	public static final double WEIGHT_WEST = 0.30;
	
	public AttackTask() {
		super(AttackTask.NAME);
	}
	
	@Override
	public void execute(Agent agent) {
		AntAgent a = (AntAgent) agent;
		Node nodeToMoveTo = this.getNodeToMoveTo((AntAgent) agent);
		a.incrementStimulusIntensity(AttackStimulusType.TYPE, a.getAgentType().getStimulusIncrement(AttackStimulusType.TYPE));

		nodeToMoveTo.addAgent(agent);

		try {
			Thread.sleep(milisecondsToWait);
		} catch (InterruptedException e) {
			logger.trace("Agent '{}' interrupted while waiting.", agent.getId());
		}
	}
	
	@Override
	public Node getNodeToMoveTo(AntAgent agent) {
		return AntTaskUtil.getNodeToMoveTo(agent, AttackStimulusType.TYPE);
	}
}
