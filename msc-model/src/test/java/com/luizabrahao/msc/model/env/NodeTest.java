package com.luizabrahao.msc.model.env;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.agent.AgentType;
import com.luizabrahao.msc.model.agent.BasicAgentType;

public class NodeTest {
	private class MockAgent extends AbstractAgent {
		public MockAgent(String id, AgentType agentType, Node currentNode) {
			super(id, agentType, currentNode);
		}
		
		@Override public void run() {}
	}
	
	
	@Test
	public void getNeighbourTest() {
		BasicNode n = new BasicNode("n");
		
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		assertTrue(n.getNeighbour(Direction.NORTH) == null);
		assertTrue(n.getNeighbour(Direction.SOUTH) == null);
		
		BasicNode north = new BasicNode("n");
		n.setNeighbours(Direction.NORTH, north);
		
		assertTrue(n.getNeighbour(Direction.NORTH) == north);
		assertTrue(north.getNeighbour(Direction.SOUTH) == n);
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		
		BasicNode south = new BasicNode("n");
		n.setNeighbours(Direction.SOUTH, south);
		
		assertTrue(n.getNeighbour(Direction.SOUTH) == south);
		assertTrue(south.getNeighbour(Direction.NORTH) == n);
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		
		BasicNode west = new BasicNode("n");
		n.setNeighbours(Direction.WEST, west);
		
		assertTrue(n.getNeighbour(Direction.WEST) == west);
		assertTrue(west.getNeighbour(Direction.EAST) == n);
		assertTrue(n.getNeighbour(Direction.EAST) == null);

		BasicNode east = new BasicNode("n");
		n.setNeighbours(Direction.EAST, east);
		
		assertTrue(n.getNeighbour(Direction.EAST) == east);
		assertTrue(east.getNeighbour(Direction.WEST) == n);
	}
	
	@Test
	public void addAgent() {
		BasicNode n = new BasicNode("n");
		MockAgent a = new MockAgent("a1", new BasicAgentType("test-agent"), n);
		
		n.addAgent(a);
		
		assertTrue(n.getAgents().size() == 1);
		assertTrue(n.getAgents().get(0) == a);
		assertTrue(a.getCurrentNode() == n);
	}
}
