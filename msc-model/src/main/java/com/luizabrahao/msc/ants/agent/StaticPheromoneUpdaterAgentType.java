package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.List;

import com.luizabrahao.msc.ants.task.StaticPheromoneUpdateTask;
import com.luizabrahao.msc.model.agent.AbstractAgentType;
import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.task.Task;

/**
 * Represents an agent that triggers the pheromone update method of the nodes
 * that compose a grid. As required it implements the singleton pattern.
 * 
 * Workers have the following tasks:
 * - StaticPheromoneUpdateTask
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public class StaticPheromoneUpdaterAgentType extends AbstractAgentType implements TaskAgentType {
	public static final String NAME = "pheromone-updater-agent";
	private static StaticPheromoneUpdaterAgentType instance = new StaticPheromoneUpdaterAgentType();
	private final List<Task> tasks;

	public StaticPheromoneUpdaterAgentType() {
		this.tasks = new ArrayList<Task>();
		tasks.add(new StaticPheromoneUpdateTask());
	}
	
	@Override public String getName() { return StaticPheromoneUpdaterAgentType.NAME; }
	@Override public List<Task> getTasks() { return this.tasks; }
	
	public static StaticPheromoneUpdaterAgentType getInstance() { return instance; }
}