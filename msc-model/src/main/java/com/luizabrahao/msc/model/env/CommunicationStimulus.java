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

	public String getName() { return name; }
	public CommunicationStimulusType getCommunicationStimulusType() { return communicationStimulusType;	}
}
