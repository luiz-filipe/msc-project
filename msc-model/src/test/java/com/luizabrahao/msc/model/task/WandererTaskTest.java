package com.luizabrahao.msc.model.task;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.agent.BasicTaskAgentType;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.EnvironmentFactory;
import com.luizabrahao.msc.model.env.Node;

public class WandererTaskTest {
	private final Logger logger = LoggerFactory.getLogger(WandererTaskTest.class);
	
	private class MockTaskAgent extends TaskAgent {
		public MockTaskAgent(String id, TaskAgentType agentType, Node currentNode) {
			super(id, agentType, currentNode, false);
		}

		@Override
		public Void call() throws Exception {
			this.getTaskList().get(0).execute(this);
			return null;
		}
	}
	
	@Test
	public void getRandomNeighbourTest() {
		Node center = new BasicNode("center");
		Node east = new BasicNode("east");
		center.setNeighbours(Direction.EAST, east);
		
		Agent agent = new MockTaskAgent("a", BasicTaskAgentType.TYPE, center);
		Node nextNode = WandererTask.getRandomNeighbour(agent);
		
		logger.debug("Selected node: " + nextNode);
		
		assertTrue(nextNode.equals(east));
	}
	
	@Test 
	public void executeTest() {
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(300, 100);
		TaskAgent a01 = new MockTaskAgent("a-01", BasicTaskAgentType.TYPE, grid[1][0]);
		TaskAgent a02 = new MockTaskAgent("a-02", BasicTaskAgentType.TYPE, grid[1][1]);
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.submit(a01);
		executor.submit(a02);
	}
}
