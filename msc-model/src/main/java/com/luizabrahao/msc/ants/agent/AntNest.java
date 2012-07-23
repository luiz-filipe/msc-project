package com.luizabrahao.msc.ants.agent;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface AntNest {
	int getSimultaneouslyAgentsCapacity();
	void produceWorkers(String agentNamePrefix, int numberOfAgents);
	List<Future<Void>> openAndKillNestAfterTimeout(long timeToLeaveOpen, TimeUnit unit) throws InterruptedException;
	void addAgent(AntAgent agent);
}