package com.luizabrahao.msc.test.mock;

import com.luizabrahao.msc.model.env.CommunicationStimulusType;

public enum TestCommunicationStimulusType implements CommunicationStimulusType {
	TYPE;

	private static final String name = "comm:stimulus:type:test";
	
	@Override
	public String getName() {
		return name;
	}

}
