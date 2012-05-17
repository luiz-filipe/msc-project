package com.luizabrahao.msc.model.task;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.Agent;
import com.luizabrahao.msc.model.agent.Cast;
import com.luizabrahao.msc.model.agent.TaskAgent;
import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.Direction;
import com.luizabrahao.msc.model.env.EnvironmentFactory;
import com.luizabrahao.msc.model.env.Node;

public class WandererTaskTest {
	private final Logger logger = LoggerFactory.getLogger(WandererTaskTest.class);
	
	@Test
	public void getRandomNeighbourTest() {
		Node center = new BasicNode("center");
		Node east = new BasicNode("east");
		center.setNeighbours(Direction.EAST, east);
		
		Agent agent = new TaskAgent("a", Cast.WORKER, center, null);
		Node nextNode = WandererTask.getRandomNeighbour(agent);
		
		logger.debug("Selected node: " + nextNode);
		
		assertTrue(nextNode.equals(east));
	}
	
	@Test 
	public void executeTest() {
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(5, 5);
		Task task = new WandererTask();
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(task);
		
		TaskAgent a01 = new TaskAgent("a-01", Cast.WORKER, grid[1][0], taskList);
		TaskAgent a02 = new TaskAgent("a-02", Cast.WORKER, grid[1][1], taskList);
		
		a01.setCurrentTask(WandererTask.NAME);
		a02.setCurrentTask(WandererTask.NAME);
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(a01);
		executor.execute(a02);
	}

}
