package com.luizabrahao.msc.model.task;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class AbstractTaskTest {
	
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(AbstractTask.class)
			.verify();
	}
}
