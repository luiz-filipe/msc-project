package com.luizabrahao.msc.ants.agent;

import java.util.HashMap;
import java.util.Map;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.model.agent.AbstractAgentType;

public abstract class AbstractAntType extends AbstractAgentType implements AntType {
	private final Map<ChemicalCommStimulusType, Double> stimulusIncrementList; 
	
	public AbstractAntType() {
		this.stimulusIncrementList = new HashMap<ChemicalCommStimulusType, Double>();
		stimulusIncrementList.put(ForageStimulusType.getInstance(), 0.001);
	}

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
