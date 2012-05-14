package com.luizabrahao.msc.model.agent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.Node;

@ThreadSafe
public class AbstractAgent implements Agent {
	protected final String id;
	@GuardedBy("this") protected Node currentNode;
	protected final Cast cast;
	
	public AbstractAgent(String id, Cast cast, Node currentNode) {
		this.id = id;
		this.cast = cast;
		this.currentNode = currentNode;
	}
	
	@Override public synchronized void setCurrentNode(Node node) { this.currentNode = node; }
	@Override public synchronized Node getCurrentCode() { return currentNode; }
	@Override public String getId() { return id; }
	@Override public Cast getCast() { return cast; }
	
	
}
