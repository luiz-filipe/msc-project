package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.ants.env.PheromoneNode;
import com.luizabrahao.msc.ants.task.ForageTask;
import com.luizabrahao.msc.model.agent.AbstractAgentType;
import com.luizabrahao.msc.model.task.Task;

/**
 * Represents an ant from the Worker cast. As required it implements the
 * singleton pattern.
 * 
 * Workers have the following tasks:
 * - Forage
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */

@ThreadSafe
public class WorkerType extends AbstractAgentType implements AntType {
	public static final String NAME = "worker";
	public static final double PHEROMONE_INCREMENT = 0.001;
	
	private static WorkerType instance = new WorkerType();
	private final List<Task> tasks;
	
	public WorkerType() {
		this.tasks = new ArrayList<Task>();
		tasks.add(new ForageTask());
	}

	@Override
	public List<Task> getTasks() { return this.tasks; }

	public static WorkerType getInstance() { return instance; }

	@Override public String getName() { return WorkerType.NAME; }
	@Override public double getPheromoneIncrement() { return WorkerType.PHEROMONE_INCREMENT; }

	@Override
	public void depositPheromone(PheromoneNode node) {
		synchronized (node) {
			if (node.getPheromoneIntensity() == 1) {
				return;
			}
			
			node.incrementPheromoneIntensity(WorkerType.PHEROMONE_INCREMENT);
		}
	}	
}