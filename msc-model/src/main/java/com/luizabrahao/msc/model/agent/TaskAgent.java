package com.luizabrahao.msc.model.agent;

import java.util.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.Node;
import com.luizabrahao.msc.model.task.Task;

/**
 * This class is a task-oriented agent. It contains a list of tasks that the
 * agent is capable to execute. Only one task can be executed at time, the
 * currentTask variable holds the current task in execution.
 *
 * This class is thread-safe.
 * 
 * I have decided not to protect the code throwing custom exceptions at this
 * stage, it should be an improvement to be considered if the model is to be
 * expanded. 
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public abstract class TaskAgent extends AbstractAgent {
	@GuardedBy("this") private Task currentTask;
	protected final TaskAgentType agentType;

	public TaskAgent(String id, TaskAgentType agentType, Node currentNode, boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
		this.agentType = agentType;
	}

	public List<Task> getTaskList() { return agentType.getTasks(); }
	public synchronized Task getCurrentTask() { return currentTask; }
	public synchronized void setCurrentTask(Task currentTask) { this.currentTask = currentTask; }

	public synchronized void setCurrentTask(String taskName) {
		this.currentTask = this.getTaskByName(taskName);
	}
	
	private Task getTaskByName(String name) {
		for (Task task : agentType.getTasks()) {
			if (task.getName().equals(name)) {
				return task;
			}
		}
		
		return null;
	}
}