package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.model.agent.TaskAgentType;

public interface AntType extends TaskAgentType {
	double getStimulusIncrement(ChemicalCommStimulusType chemicalCommStimulusType);
}
