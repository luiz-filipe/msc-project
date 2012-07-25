package com.luizabrahao.msc.ants.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.env.Node;

@ThreadSafe
public class FoodSourceAgent extends AbstractAgent {
	private static final Logger logger = LoggerFactory.getLogger(FoodSourceAgent.class);
	private static final long CHECK_INTERVAL = 1000;
	@GuardedBy("this") private double foodAmount;

	public FoodSourceAgent(String id, Node currentNode, double amountOfFood) {
		super(id, FoodSourceAgentType.TYPE, currentNode, false);
		this.foodAmount = amountOfFood;
		
		logger.debug("Food source '{}' initialised with amount of '{}'", id, amountOfFood);
	}

	public synchronized double collectFood(double amountToCollect) {
		if (this.foodAmount == 0) {
			logger.trace("{}: there is no more food to be collected.", this.getId());
			return 0;
		}
		
		if (this.foodAmount > amountToCollect) {
			this.foodAmount = this.foodAmount - amountToCollect;
			
			logger.trace("{}: {} of food collected.", this.getId(), amountToCollect);
			return amountToCollect;
		
		} else {
			double amountAvailable = this.foodAmount;
			this.foodAmount = 0;
			
			logger.trace("{}: {} of food collected.", this.getId(), amountAvailable);
			return amountAvailable;
		}
	}
	
	@Override
	public Void call() throws Exception {
		while (true) {
			try {
				Thread.sleep(FoodSourceAgent.CHECK_INTERVAL);
			} catch (Exception e) {
				// don't need to do anything... let it just stop...
			}
			
			synchronized (this) {
				if (foodAmount == 0) {
					logger.info("Food source '{}' has 0 of food.", this.getId());
				}
			}
		}
	}

}
