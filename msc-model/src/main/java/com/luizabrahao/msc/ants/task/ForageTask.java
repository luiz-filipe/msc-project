package com.luizabrahao.msc.ants.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.agent.AntAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.AbstractTask;

public class ForageTask extends AbstractTask implements AntTask {
	private static final Logger logger = LoggerFactory.getLogger(ForageTask.class);
	private static final long milisecondsToWait = 5;
	
	public static final String NAME = "Forage"; 

	public ForageTask() {
		super(ForageTask.NAME);
	}

	@Override
	public void execute(Agent agent) {
		AntAgent a = (AntAgent) agent;
		Node nodeToMoveTo = this.getNodeToMoveTo((AntAgent) agent);
		a.incrementStimulusIntensity(ForageStimulusType.TYPE);

		nodeToMoveTo.addAgent(agent);

		try {
			Thread.sleep(milisecondsToWait);
		} catch (InterruptedException e) {
			logger.trace("Agent '{}' interrupted while waiting.", agent.getId());
		}
	}
	
	@Override
	public Node getNodeToMoveTo(AntAgent agent) {
		return AntTaskUtil.getNodeToMoveTo(agent, ForageStimulusType.TYPE);
	}
}