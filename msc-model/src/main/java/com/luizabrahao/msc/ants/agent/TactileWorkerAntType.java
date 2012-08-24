package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.FoodSourceAgent;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.WarningStimulusType;
import com.luizabrahao.msc.ants.task.FindAndHideInNest;
import com.luizabrahao.msc.ants.task.FindHomeTask;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.task.Task;

public enum TactileWorkerAntType implements AntType {
TYPE;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkerAntType.class);
	
	private static final String name = "type:ant:tactile-worker";
	private final List<Task> tasks;
	private final Map<String, Double> stimulusIncrementList;
	private static final int memorySize = 50;
	private final double amountOfFoodCapableToCollect = 0.1;
	private static final long milisecondsToWait = 5;
	private static final double warningThreshold = 0.5;
	
	private static final int tactileWarningThreshold = 3;
	private int warningCounter = 0;

	TactileWorkerAntType() {
		tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
		tasks.add(new FindHomeTask());
		tasks.add(new FindAndHideInNest());
		
		stimulusIncrementList = new HashMap<String, Double>();
		stimulusIncrementList.put(ForageStimulusType.TYPE.getName(), 0.01);
		stimulusIncrementList.put(WarningStimulusType.TYPE.getName(), 0.05);
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
		
		if (ant.getCurrentTask() != null) {
			// if it is trying to hide, just continue...
			if (ant.getCurrentTask().getName().equals(FindAndHideInNest.NAME)) {
				ant.getTaskByName(FindAndHideInNest.NAME).execute(agent);
				
				this.waitSomeTime();
				return;
			}
		}
		
//		synchronized (agent.getCurrentNode().getAgents()) {
//			for (Agent sameNodeAgent : agent.getCurrentNode().getAgents()) {
//				if (sameNodeAgent.getId().equals(ant.getId())) {
//					continue;
//				}
//				
//				// we cannot cast nest to ants, and there no reason to do that
//				if (sameNodeAgent.getAgentType() == AntNestType.TYPE) {
//					continue;
//				}
//				
//				AntAgent sameNodeAnt = (AntAgent) sameNodeAgent;
//				
//				if (!sameNodeAnt.isCaringFood()) {
//					if (sameNodeAnt.getMovingDirection() == this.getInverse(ant.getMovingDirection())) {
//						warningCounter++;
//					}
//				}
//			}
//		}
//		
//		if (warningCounter > warningThreshold ) {
//			ant.getTaskByName(FindAndHideInNest.NAME).execute(agent);
//			
//			this.waitSomeTime();
//			return;
//		}
		
		
		ChemicalCommStimulus warningStimulus = (ChemicalCommStimulus) ant.getCurrentNode().getCommunicationStimulus(WarningStimulusType.TYPE);
		
		if ((warningStimulus != null) && (warningStimulus.getIntensity() > warningThreshold)) {
			// if the ant is caring food it is likely it already is moving
			// towards the nest, so there is no need to invert its direction.
			if (!ant.isCarryingFood()) {
				ant.invertDirection();
			}
			
			ant.setCurrentTask(ant.getTaskByName(FindAndHideInNest.NAME));
			logger.debug("{} has switched to {}", agent.getId(), FindAndHideInNest.NAME);
			ant.setCurrentTask(FindAndHideInNest.NAME);
			ant.getTaskByName(FindAndHideInNest.NAME).execute(agent);
			
			this.waitSomeTime();
			return;
		}
		
		
		FoodSourceAgent foodSource = ant.findFoodSource();
		
		if ((foodSource != null) && (!ant.isCarryingFood())) {
			ant.collectFood(foodSource, amountOfFoodCapableToCollect);
			logger.debug("{} found a source food and will try to collect food.", agent.getId());
			
			if (ant.isCarryingFood()) {
				ant.invertDirection();
			}
		}
				
		if (!ant.isCarryingFood()) {
			ant.setCurrentTask(ForageTask.NAME);
			ant.getTaskByName(ForageTask.NAME).execute(agent);
		} else {
			ant.setCurrentTask(FindHomeTask.NAME);
			ant.getTaskByName(FindHomeTask.NAME).execute(agent);
		}
		
		this.waitSomeTime();
	}
	
	private void waitSomeTime() {
		try {
			Thread.sleep(milisecondsToWait);
		} catch (InterruptedException e) {
			// don't need to do anything...
		}
	}
	
	private Direction getInverse(Direction direction) {
		if (direction == Direction.NORTH) {
			return Direction.SOUTH;
		}

		if (direction == Direction.EAST) {
			return Direction.WEST;
		}
		
		if (direction == Direction.SOUTH) {
			return Direction.NORTH;
		}
		
		if (direction == Direction.WEST) {
			return Direction.EAST;
		}
		
		throw new  RuntimeException("Invalid direction!");
	}
}
