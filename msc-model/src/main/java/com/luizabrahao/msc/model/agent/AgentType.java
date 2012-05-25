package com.luizabrahao.msc.model.agent;

import java.util.List;

import com.luizabrahao.msc.model.task.Task;

/**
 * AgentType is a basic data type that hold all the tasks agent of a certain
 * type can perform and extra information like the type's name.
 * 
 * AgentTypes are advised to be implemented as singleton, in order to save
 * memory consumption. 
 * 
 * Classes that implement this interface must be thread-safe and have a public
 * static final field called NAME. 
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface AgentType {
	List<Task> getTasks();
}