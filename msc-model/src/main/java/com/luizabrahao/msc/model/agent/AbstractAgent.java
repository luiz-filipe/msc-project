package com.luizabrahao.msc.model.agent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.Node;
/**
 * Base implementation of Agent interface. It also implements Runnable in order
 * to force subclasses to be able to be ran in different threads.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public abstract class AbstractAgent implements Agent, Runnable {
	protected final String id;
	@GuardedBy("this") protected Node currentNode;
	protected final Cast cast;
	
	public AbstractAgent(String id, Cast cast, Node currentNode) {
		this.id = id;
		this.cast = cast;
		this.currentNode = currentNode;
		this.currentNode.addAgent(this);
	}
	
	/**
	 * This method is not normally called directly form user code. In normal
	 * circumstances the current node is set by the node.addAgent() method.
	 * Which makes sure both sides of the relationship are defined.
	 * 
	 *   @see Node
	 *   @param Node node is going to be set as current.
	 */
	@Override
	public synchronized void setCurrentNode(Node node) { this.currentNode = node; }
	
	@Override public synchronized Node getCurrentNode() { return currentNode; }
	@Override public String getId() { return id; }
	@Override public Cast getCast() { return cast; }
	
	
}
