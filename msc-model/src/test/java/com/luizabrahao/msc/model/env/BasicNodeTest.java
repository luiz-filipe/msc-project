package com.luizabrahao.msc.model.env;

import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.test.mock.TestAgent;
import com.luizabrahao.msc.test.mock.TestAgentType;
import com.luizabrahao.msc.test.mock.TestCommunicationStimulus;
import com.luizabrahao.msc.test.mock.TestCommunicationStimulusType;

public class BasicNodeTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(BasicNode.class).verify();
	}
	
	@Test
	public void communicationStimulusTest() {
		final Node node = new BasicNode("node");
		
		node.addCommunicationStimulus(new TestCommunicationStimulus());
		
		assertTrue(node.getCommunicationStimuli().size() == 1);
		assertTrue(node.getCommunicationStimuli().get(0).getType().equals(
				TestCommunicationStimulusType.TYPE));
	}
	
	@Test
	public void addAgentTest() {
		final Node node = new BasicNode("node");
		final Node stagging = new BasicNode("stagging node");
		
		final Agent a = new TestAgent("a", TestAgentType.TYPE, stagging, false);
		
		assertTrue(stagging.getAgents().size() == 1);
		assertTrue(stagging.getAgents().get(0).getId().equals("a"));
		
		node.addAgent(a);
		
		assertTrue(stagging.getAgents().size() == 0);
		assertTrue(node.getAgents().size() == 1);
		assertTrue(node.getAgents().get(0).getId().equals("a"));
	}

	@Test
	public void neighboursLinkTest() {
		final Node l = new BasicNode("left");
		final Node r = new BasicNode("right");
		
		l.setNeighbours(Direction.EAST, r);
		
		assertTrue(l.getNeighbour(Direction.NORTH) == null);
		assertTrue(l.getNeighbour(Direction.EAST).equals(r));
		assertTrue(l.getNeighbour(Direction.SOUTH) == null);
		assertTrue(l.getNeighbour(Direction.WEST) == null);
		
		assertTrue(r.getNeighbour(Direction.NORTH) == null);
		assertTrue(r.getNeighbour(Direction.EAST) == null);
		assertTrue(r.getNeighbour(Direction.SOUTH) == null);
		assertTrue(r.getNeighbour(Direction.WEST).equals(l));
	}
}
