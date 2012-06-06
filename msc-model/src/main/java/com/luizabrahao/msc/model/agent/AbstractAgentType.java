package com.luizabrahao.msc.model.agent;

/**
 * Basic abstract implementation of AgentType. All other AgentType classes
 * must extend this one.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public abstract class AbstractAgentType implements AgentType {
	/**
	 * All AgentType classes MUST be implemented as singleton. So we don't want
	 * anyone cloning their object instances.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
