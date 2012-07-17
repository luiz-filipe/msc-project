package com.luizabrahao.msc.model.env;

import net.jcip.annotations.Immutable;

@Immutable
public abstract class CommunicationStimulus implements CommunicationStimulusType {
	private final String name;
	private final CommunicationStimulusType communicationStimulusType;

	public CommunicationStimulus(String name, CommunicationStimulusType communicationStimulusType) {
		this.name = name;
		this.communicationStimulusType = communicationStimulusType;
	}

	@Override public String getName() { return name; }
	public CommunicationStimulusType getType() { return this.communicationStimulusType; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CommunicationStimulus other = (CommunicationStimulus) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
