package com.luizabrahao.msc.ants.env;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.annotation.FrameworkExclusive;
import com.luizabrahao.msc.model.env.CommunicationStimulus;

@ThreadSafe
public class ChemicalCommStimulus extends CommunicationStimulus {
	@GuardedBy("this") private double intensity = 0;
	
	public ChemicalCommStimulus(String name, ChemicalCommStimulusType chemicalCommStimulusType) {
		super(name, chemicalCommStimulusType);
	}
	
	public synchronized double getIntensity() { return this.intensity; }
	
	@FrameworkExclusive
	public synchronized void setIntensity(double intensity) { this.intensity = intensity; }

	
	@Override
	public ChemicalCommStimulusType getType() {
		return (ChemicalCommStimulusType) super.getType();
	}

	/**
	 * Increases the current intensity of the stimulus by the amount passed as
	 * parameter
	 * 
	 * @param amount Double Amount to incremenet the intensity by.
	 */
	public synchronized void increaseIntensity(double amount) {
		this.intensity = this.intensity + amount;
	}
	
	public synchronized void  decayIntensity() {
		this.intensity = this.intensity * (1 - this.getType().getDecayFactor());
	}
}
