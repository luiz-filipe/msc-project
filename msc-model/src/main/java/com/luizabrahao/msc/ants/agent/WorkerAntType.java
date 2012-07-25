package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.env.AttackStimulusType;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.task.FindAndHideInNest;
import com.luizabrahao.msc.ants.task.FindHomeTask;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.task.Task;

public enum WorkerAntType implements AntType {
	TYPE;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkerAntType.class);
	
	private static final String name = "type:ant:worker";
	private final List<Task> tasks;
	private final Map<String, Double> stimulusIncrementList;
	private static final int memorySize = 50;
	private final double amountOfFoodCapableToCollect = 0.1;
	private static final long milisecondsToWait = 5;
	private static final double attackThreshold = 0.5;

	WorkerAntType() {
		tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
		tasks.add(new FindHomeTask());
		tasks.add(new FindAndHideInNest());
		
		stimulusIncrementList = new HashMap<String, Double>();
		stimulusIncrementList.put(ForageStimulusType.TYPE.getName(), 0.01);
		stimulusIncrementList.put(AttackStimulusType.TYPE.getName(), 0.05);
	}
	
	@Override public String getName() { return name; }
	@Override public List<Task> getTasks() { return tasks; }
	@Override public int getMemorySize() { return memorySize; }
	@Override public double getAmountOfFoodCapableToCollect() { return amountOfFoodCapableToCollect; }
	
	@Override
	public double getStimulusIncrement(String chemicalCommStimulusTypeName) {
		for (Map.Entry<String, Double> entry : stimulusIncrementList.entrySet()) {
			if (entry.getKey().equals(chemicalCommStimulusTypeName)) {
				return entry.getValue();
			}
		}
		
		throw new RuntimeException("WorkerType does not have an increment declared for '" + chemicalCommStimulusTypeName + "'");
	}

	@Override
	public void execute(Agent agent) {
		AntAgent ant = (AntAgent) agent;
		
		ChemicalCommStimulus attackStimulus = (ChemicalCommStimulus) ant.getCurrentNode().getCommunicationStimulus(AttackStimulusType.TYPE);
		
		if (attackStimulus.getIntensity() > attackThreshold) {
			// if not caring food, need to turn back to nest.
			if (!ant.isCaringFood()) {
				ant.invertDirection();
			}
			
			// hide!!
			this.tasks.get(2).execute(agent);
		}
		
		FoodSourceAgent  foodSource = ant.findFoodSource();
		
		if ((foodSource != null) && (!ant.isCaringFood())) {
			ant.collectFood(foodSource, amountOfFoodCapableToCollect);
			logger.debug("{} found a source food and will try to collect food.", agent.getId());
			
			if (ant.isCaringFood()) {
				ant.invertDirection();
			}
		}
				
		if (!ant.isCaringFood()) {
			this.tasks.get(0).execute(agent);
		} else {
			this.tasks.get(1).execute(agent);
		}
		
		try {
			Thread.sleep(milisecondsToWait);
		} catch (InterruptedException e) {
			logger.trace("Agent '{}' interrupted while waiting.", agent.getId());
		}
	}
}