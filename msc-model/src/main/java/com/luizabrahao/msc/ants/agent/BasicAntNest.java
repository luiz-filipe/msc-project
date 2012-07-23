package com.luizabrahao.msc.ants.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luizabrahao.msc.model.agent.AbstractAgent;
import com.luizabrahao.msc.model.env.Node;

public class BasicAntNest extends AbstractAgent implements AntNest {
	private static final Logger logger = LoggerFactory.getLogger(BasicAntNest.class);
	
	private final int threadPoolSize;
	private List<AntAgent> agents;
	private final ExecutorService executor;
	
	public BasicAntNest(String id, Node currentNode, int numberOfConcurrentAgents) {
		super(id, AntNestType.TYPE, currentNode, false);
		
		this.threadPoolSize = numberOfConcurrentAgents;
		this.agents = new ArrayList<AntAgent>();
		this.executor = Executors.newFixedThreadPool(numberOfConcurrentAgents);
	}

	@Override public int getSimultaneouslyAgentsCapacity() { return this.threadPoolSize; }
	
	@Override
	public Void call() throws Exception {
		throw new RuntimeException("Nests are not to be used as threads... They just take advantage of the infrastructure of agents");
	}


	@Override
	public void produceWorkers(String agentNamePrefix, int numberOfAgents) {
		agents.addAll(AntAgentFactory.produceBunchOfWorkers(numberOfAgents, agentNamePrefix, this.getCurrentNode()));
	}

	@Override
	public List<Future<Void>> openAndKillNestAfterTimeout(long timeToLeaveOpen, TimeUnit unit) throws InterruptedException {
		if (this.agents.size() == 0) {
			logger.warn("{} has no agents to use.", this.getId());
		}
		
		logger.info("{} is opening now and it is close in {} " + unit, this.getId(), timeToLeaveOpen);
		return executor.invokeAll(agents, timeToLeaveOpen, unit);		
	}
	
	@Override
	public void addAgent(AntAgent agent) {
		if (this.agents == null) {
			this.agents = new ArrayList<AntAgent>();
		}
		
		agents.add(agent);
	}
}
