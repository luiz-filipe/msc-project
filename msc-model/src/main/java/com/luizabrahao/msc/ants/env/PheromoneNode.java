package com.luizabrahao.msc.ants.env;

import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.CommunicationStimulus;

public class PheromoneNode extends BasicNode {

	public PheromoneNode(String id) {
		super(id);
	}

	public synchronized ChemicalCommStimulus getCommunicationStimulus(ChemicalCommStimulusType chemicalCommStimulusType) {
		if (this.getCommunicationStimuli() != null) {
			for (CommunicationStimulus stimulus : this.getCommunicationStimuli()) {
				if (stimulus.getType() == chemicalCommStimulusType) {
					return (ChemicalCommStimulus) stimulus;
				}
			}
		}
		
		ChemicalCommStimulus c = new ChemicalCommStimulus(chemicalCommStimulusType);
		this.addCommunicationStimulus(c);
		
		return c;
	}
	
	public void incrementStimulusIntensity(ChemicalCommStimulusType chemicalCommStimulusType, double amount) {
		ChemicalCommStimulus c = this.getCommunicationStimulus(chemicalCommStimulusType);
		c.increaseIntensity(amount);
	}
}
