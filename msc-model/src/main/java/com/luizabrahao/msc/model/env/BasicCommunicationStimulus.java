package com.luizabrahao.msc.model.env;

import net.jcip.annotations.Immutable;

/**
 * A communication stimulus might represent any interaction between agents and
 * the environment. For example, when ant agents deposit pheromone on the
 * environment, the pheromone is a type of communication stimulus. The
 * ChemicalCommStimulus class is an example of this case. This is just an
 * abstraction implementation that should be used as base.
 * 
 * @see ChemicalCommStimulus
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@Immutable
public class BasicCommunicationStimulus implements CommunicationStimulus {
	private final CommunicationStimulusType communicationStimulusType;

	public BasicCommunicationStimulus(CommunicationStimulusType communicationStimulusType) {
		this.communicationStimulusType = communicationStimulusType;
	}

	@Override public CommunicationStimulusType getType() { return this.communicationStimulusType; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((communicationStimulusType == null) ? 0
						: communicationStimulusType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicCommunicationStimulus other = (BasicCommunicationStimulus) obj;
		if (communicationStimulusType == null) {
			if (other.communicationStimulusType != null)
				return false;
		} else if (!communicationStimulusType
				.equals(other.communicationStimulusType))
			return false;
		return true;
	}

	
}
