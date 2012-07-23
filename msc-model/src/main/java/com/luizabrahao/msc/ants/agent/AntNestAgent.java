package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.env.Node;

public class AntNestAgent extends AbstractAgent {
	private static final Logger logger = LoggerFactory.getLogger(AntNestAgent.class);
	private List<AntAgent> agents;
	@GuardedBy("this") private double amountOfFoodHeld = 0;
	
	public AntNestAgent(String id, Node currentNode) {
		super(id, AntNestType.TYPE, currentNode, false);
		
		this.agents = new ArrayList<AntAgent>();
	}

	public synchronized double getAmountOfFoodHeld() { return amountOfFoodHeld; }

	@Override
	public Void call() throws Exception {
		throw new RuntimeException("Nests are not to be used as threads... They just take advantage of the infrastructure of agents");
	}

	public void produceWorkers(String agentNamePrefix, int numberOfAgents) {
		agents.addAll(AntAgentFactory.produceBunchOfWorkers(numberOfAgents, agentNamePrefix, this.getCurrentNode()));
	}

	public void addAgent(AntAgent agent) {
		if (this.agents == null) {
			this.agents = new ArrayList<AntAgent>();
		}
		
		agents.add(agent);
	}
	
	public void addPortionOfFood(AntAgent agent, double portion) {
		synchronized (this) {
			amountOfFoodHeld = amountOfFoodHeld + portion;
		}
		
		logger.debug(this.getId() + ": {} deposited {} of food.", agent.getId(), portion);
	}
}
