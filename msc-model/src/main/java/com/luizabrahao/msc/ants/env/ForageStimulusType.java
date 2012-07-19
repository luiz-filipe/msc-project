package com.luizabrahao.msc.ants.env;

public enum ForageStimulusType implements ChemicalCommStimulusType {
	TYPE;

	private static final String name = "ant:env:stimulus:forage";
	private static final double decay_factor = 0.1;
	private static final int radius = 0;
	
	@Override public String getName() { return name; }
	@Override public double getDecayFactor() { return decay_factor; }
	@Override public int getRadius() { return radius; }
}
