package com.luizabrahao.msc.model.agent;

import java.util.List;

import com.luizabrahao.msc.model.task.Task;

/**
 * Task agents have a list of tasks they are enable to execute. This list comes
 * from their type. Each type of task agents declare this list of tasks and it
 * is used by the agent during their life-cycle.
 * 
 * Task agent type should be implemented as singleton in order to save memory
 * consumption
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface TaskAgentType extends AgentType {
	List<Task> getTasks();
}