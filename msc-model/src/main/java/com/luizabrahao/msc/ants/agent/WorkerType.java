package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.task.Task;

@ThreadSafe
public enum WorkerType implements AntType {
	TYPE;

	WorkerType() {
		tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
		
		stimulusIncrementList = new HashMap<ChemicalCommStimulusType, Double>();
		stimulusIncrementList.put(ForageStimulusType.TYPE, 0.001);
	}
	
	private final String name = "type:ant:worker";
	private final List<Task> tasks;
	private final Map<ChemicalCommStimulusType, Double> stimulusIncrementList;
	
	@Override public String getName() { return name; }
	@Override public List<Task> getTasks() { return tasks; }

	@Override
	public double getStimulusIncrement(ChemicalCommStimulusType chemicalCommStimulusType) {
		for (Map.Entry<ChemicalCommStimulusType, Double> entry : stimulusIncrementList.entrySet()) {
			if (entry.getKey() == chemicalCommStimulusType) {
				return entry.getValue();
			}
		}
		
		return 0;
	}
}