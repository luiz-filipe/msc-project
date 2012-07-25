package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luizabrahao.msc.ants.env.AttackStimulusType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.task.FindHomeTask;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.task.Task;

public enum SoldierAntType implements AntType {
	TYPE;

	private static final String name = "type:ant:soldier";
	private final List<Task> tasks;
	private final Map<String, Double> stimulusIncrementList;
	private static final int memorySize = 50;
	private final double amountOfFoodCapableToCollect = 0.1;
	private static final long milisecondsToWait = 5;
	private static final double attackThreshold = 0.5;
	
	@Override public String getName() { return name; }
	@Override public List<Task> getTasks() { return tasks; }
	@Override public int getMemorySize() { return memorySize; }
	@Override public double getAmountOfFoodCapableToCollect() { return amountOfFoodCapableToCollect; }
	
	SoldierAntType() {
		tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
		tasks.add(new FindHomeTask());
		
		stimulusIncrementList = new HashMap<String, Double>();
		stimulusIncrementList.put(ForageStimulusType.TYPE.getName(), 0.01);
		stimulusIncrementList.put(AttackStimulusType.TYPE.getName(), 0.3);
	}

	@Override
	public void execute(Agent agent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double getStimulusIncrement(String chemicalCommStimulusTypeName) {
		for (Map.Entry<String, Double> entry : stimulusIncrementList.entrySet()) {
			if (entry.getKey().equals(chemicalCommStimulusTypeName)) {
				return entry.getValue();
			}
		}
		
		throw new RuntimeException("WorkerType does not have an increment declared for '" + chemicalCommStimulusTypeName + "'");
	}
}
