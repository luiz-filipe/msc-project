package com.luizabrahao.msc.ants.env;

import com.luizabrahao.msc.model.agent.AbstractAgentType;

public class PheromoneUpdaterAgentType extends AbstractAgentType {
	public static final String NAME = "pheromone-updater-agent";
	private static PheromoneUpdaterAgentType instance = new PheromoneUpdaterAgentType();

	@Override
	public String getName() { return PheromoneUpdaterAgentType.NAME; }
	
	public static PheromoneUpdaterAgentType getInstance() { return instance; }

}
