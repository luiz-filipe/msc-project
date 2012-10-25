package com.luizabrahao.msc.model.agent;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class TaskAgentTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(TaskAgent.class)
			.verify();
	}
}
