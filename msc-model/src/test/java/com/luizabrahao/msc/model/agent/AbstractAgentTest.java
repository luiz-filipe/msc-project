package com.luizabrahao.msc.model.agent;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class AbstractAgentTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(AbstractAgent.class)
			.verify();
	}
}
