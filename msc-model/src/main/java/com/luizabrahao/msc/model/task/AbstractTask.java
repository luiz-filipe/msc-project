package com.luizabrahao.msc.model.task;

import net.jcip.annotations.Immutable;

/**
 * Base implementation of Task interface.
 * 
 * This class is immutable, and any other classes that extend this one should
 * also be.
 * 
 * Tasks are identified by name. They should be unique strings. Another
 * important point is that tasks should be stateless, as the same object
 * instance of a task will be shared by many agents.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Immutable
public abstract class AbstractTask implements Task {
	protected final String name;

	public AbstractTask(String id) {
		this.name = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof AbstractTask)) {
			return false;
		}

		AbstractTask other = (AbstractTask) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}