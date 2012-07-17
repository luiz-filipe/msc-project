package com.luizabrahao.msc.ants.env;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.CommunicationStimulus;
import com.luizabrahao.msc.model.env.CommunicationStimulusType;

@ThreadSafe
public class ChemicalCommStimulus extends CommunicationStimulus {
	@GuardedBy("this") private double intensity = 0;
	
	public ChemicalCommStimulus(String name, CommunicationStimulusType communicationStimulusType) {
		super(name, communicationStimulusType);
	}
	
	public synchronized double getIntensity() { return this.intensity; }
}
