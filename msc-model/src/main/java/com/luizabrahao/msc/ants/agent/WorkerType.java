package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.ants.env.AttackStimulusType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.task.Task;

@ThreadSafe
public enum WorkerType implements AntType {
	TYPE;

	private final String name = "type:ant:worker";
	private final List<Task> tasks;
	private final Map<String, Double> stimulusIncrementList;

	WorkerType() {
		tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
		
		stimulusIncrementList = new HashMap<String, Double>();
		stimulusIncrementList.put(ForageStimulusType.TYPE.getName(), 0.02);
		stimulusIncrementList.put(AttackStimulusType.TYPE.getName(), 0.05);
	}
	
	
	@Override public String getName() { return name; }
	@Override public List<Task> getTasks() { return tasks; }

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