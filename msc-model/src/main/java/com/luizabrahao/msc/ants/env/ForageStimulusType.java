package com.luizabrahao.msc.ants.env;

public class ForageStimulusType implements ChemicalCommStimulusType {
	private static final String NAME = "stimulus-chemical-forage";
	private static final double DECAY_FACTOR = 0.1; 
	private static final int RADIUS = 1;
	private static final ForageStimulusType instance = new ForageStimulusType();
	
	@Override public String getName() { return ForageStimulusType.NAME; }
	@Override public double getDecayFactor() { return ForageStimulusType.DECAY_FACTOR; }
	@Override public int getRadius() {return ForageStimulusType.RADIUS; }
	
	public static ForageStimulusType getInstance() { return instance; }
	
}
