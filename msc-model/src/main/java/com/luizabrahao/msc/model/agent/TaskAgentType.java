package com.luizabrahao.msc.model.agent;

import java.util.List;

import com.luizabrahao.msc.model.task.Task;


public interface TaskAgentType extends AgentType {
	List<Task> getTasks();
}
