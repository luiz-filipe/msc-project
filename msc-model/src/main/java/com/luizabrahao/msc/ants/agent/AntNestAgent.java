package com.luizabrahao.msc.ants.agent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.env.Node;

@ThreadSafe
public class AntNestAgent extends AbstractAgent {
	private static final Logger logger = LoggerFactory.getLogger(AntNestAgent.class);
	@GuardedBy("this") private double amountOfFoodHeld = 0;
	
	public AntNestAgent(String id, Node currentNode) {
		super(id, AntNestType.TYPE, currentNode, false);
	}

	public synchronized double getAmountOfFoodHeld() { return amountOfFoodHeld; }

	@Override
	public Void call() throws Exception {
		throw new RuntimeException("Nests are not to be used as threads... They just take advantage of the infrastructure of agents");
	}

	public void addPortionOfFood(final AntAgent agent, final double portion) {
		synchronized (this) {
			amountOfFoodHeld = amountOfFoodHeld + portion;
		}
		
		logger.debug(this.getId() + ": {} deposited {} of food.", agent.getId(), portion);
	}
}
