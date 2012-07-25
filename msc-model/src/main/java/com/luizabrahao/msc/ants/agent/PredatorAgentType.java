package com.luizabrahao.msc.ants.agent;

import java.util.List;

import com.luizabrahao.msc.model.agent.TaskAgentType;
import com.luizabrahao.msc.model.task.Task;

public enum PredatorAgentType implements TaskAgentType {
	TYPE;
	
	private final String name = "type:ant:predator";

	PredatorAgentType() {}
	
	@Override public String getName() { return name; }
	@Override public List<Task> getTasks() {
		throw new RuntimeException("Ant predators are not able to perform any task.");
	}
}
