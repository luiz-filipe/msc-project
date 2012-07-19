package com.luizabrahao.msc.ants.env;

public class AttackStimulusType implements ChemicalCommStimulusType {
	private static final String NAME = "stimulus-chemical-attack";
	private static final double DECAY_FACTOR = 0.2; 
	private static final int RADIUS = 2;
	
	private static final AttackStimulusType instance = new AttackStimulusType();
	
	@Override public String getName() { return AttackStimulusType.NAME; }
	@Override public double getDecayFactor() { return AttackStimulusType.DECAY_FACTOR; }
	@Override public int getRadius() {return AttackStimulusType.RADIUS; }
	
	public static AttackStimulusType getInstance() { return instance; }
}
