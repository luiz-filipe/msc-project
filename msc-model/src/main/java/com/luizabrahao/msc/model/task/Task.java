package com.luizabrahao.msc.model.task;

import com.luizabrahao.msc.model.agent.Agent;

public interface Task {
	/**
	 * Each task type has a name, an identifier that is unique and is used to
	 * tell one task to another when it is needed.
	 *	
	 * @return String task type identifier.
	 */
	String getName();
	
	/**
	 * Executes a set of instruction that defines the agent's behaviour when
	 * executing the task.
	 * 
	 * @param agent Agent that will perform the task.
	 */
	void execute(Agent agent);
}
