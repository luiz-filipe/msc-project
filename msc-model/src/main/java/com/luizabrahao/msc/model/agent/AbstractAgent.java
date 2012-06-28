package com.luizabrahao.msc.model.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public abstract class AbstractAgent implements Agent, Callable<Void> {
	protected final String id;
	protected final AgentType agentType;
	protected final boolean recordNodeHistory;
	@GuardedBy("this") protected Node currentNode;
	@GuardedBy("this") protected List<Node> nodesVisited = null;
	
	public AbstractAgent(String id, AgentType agentType, Node currentNode, boolean recordNodeHistory) {
		this.id = id;
		this.agentType = agentType;
		this.currentNode = currentNode;
		this.recordNodeHistory = recordNodeHistory;
		currentNode.addAgent(this);
	}
	
	/**
	 * This method is not normally called directly form user code. In normal
	 * circumstances the current node is set by the node.addAgent() method.
	 * Which makes sure both sides of the relationship are defined.
	 * 
	 *   @see Node
	 *   @param Node node is going to be set as current.
	 */
	@Override public synchronized void setCurrentNode(Node node) { this.currentNode = node; }
	
	@Override public synchronized Node getCurrentNode() { return currentNode; }
	@Override public String getId() { return id; }
	@Override public AgentType getAgentType() { return agentType; }

	@Override
	public void addToVisitedHistory(Node node) {
		synchronized (this) {
			if (nodesVisited == null) {
				nodesVisited = Collections.synchronizedList(new ArrayList<Node>());
			}
		}
		
		nodesVisited.add(node);
	}

	@Override
	public synchronized List<Node> getNodesVisited() {
		return Collections.unmodifiableList(nodesVisited);
	}

	@Override
	public boolean shouldRecordNodeHistory() {
		return recordNodeHistory;
	}
}
