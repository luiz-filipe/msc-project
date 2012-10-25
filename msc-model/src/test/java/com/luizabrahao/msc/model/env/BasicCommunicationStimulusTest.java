package com.luizabrahao.msc.model.env;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import com.luizabrahao.msc.test.mock.TestCommunicationStimulus;

public class BasicCommunicationStimulusTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(BasicCommunicationStimulus.class)
			.withRedefinedSubclass(TestCommunicationStimulus.class)
			.verify();
	}
}
