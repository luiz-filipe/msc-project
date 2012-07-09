package com.luizabrahao.msc.ants.env;

import com.luizabrahao.msc.model.agent.AbstractAgentType;

public class FoodSourceAgentType extends AbstractAgentType {
	public static final String NAME = "food-source";
	private static FoodSourceAgentType instance = new FoodSourceAgentType();
	
	@Override
	public String getName() { return FoodSourceAgentType.NAME; }
	
	public static FoodSourceAgentType getInstance() { return instance; }

}
