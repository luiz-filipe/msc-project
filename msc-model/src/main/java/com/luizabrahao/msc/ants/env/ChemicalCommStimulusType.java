package com.luizabrahao.msc.ants.env;

import com.luizabrahao.msc.model.env.CommunicationStimulusType;

public interface ChemicalCommStimulusType extends CommunicationStimulusType {
	double getDecayFactor();
	int getRadius();
}
