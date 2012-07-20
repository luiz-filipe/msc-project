package com.luizabrahao.msc.ants.agent;

import com.luizabrahao.msc.model.agent.TaskAgentType;

public interface AntType extends TaskAgentType {
	double getStimulusIncrement(String chemicalCommStimulusTypeName);
}
