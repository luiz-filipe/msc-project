package com.luizabrahao.msc.model.agent;

import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.task.Task;
import com.luizabrahao.msc.model.task.WandererTask;

/**
 * This is the reference implementation of a concrete TaskAgentType. Note that
 * it is implemented as an Enum to implement the Singleton pattern.
 * 
 * This agent yype only has the WandererTask in its list and should be used in
 * unit tests and as a reference when building more complex and domain specific
 * agent types.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public enum BasicTaskAgentType implements TaskAgentType {
	TYPE;

	private final String name = "BaseTaskAgent Type";
	private final List<Task> tasks;
	
	BasicTaskAgentType() {
		tasks = new ArrayList<Task>();
		tasks.add(new WandererTask());
	}
	
	@Override public List<Task> getTasks() { return tasks; }
	@Override public String getName() { return name; }
}