package com.luizabrahao.msc.ants.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.AttackStimulusType;
import com.luizabrahao.msc.ants.env.ChemicalCommStimulusType;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;

public class AntAgentTest {
	@Test
	public void incrementTest() {
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		AntAgent a = new AntAgent("a", WorkerType.TYPE, grid[1][1], false);
		
		a.incrementStimulusIntensity(ForageStimulusType.TYPE);
		
		assertTrue(grid[0][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == a.getAgentType().getStimulusIncrement(ForageStimulusType.TYPE.getName()));
		assertTrue(grid[1][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
	
		grid = AntEnvironmentFactory.createPheromoneNodeGrid(7, 7);
		a = new AntAgent("a", WorkerType.TYPE, grid[3][3], false);
		ChemicalCommStimulusType attack = AttackStimulusType.TYPE;
		
		a.incrementStimulusIntensity(AttackStimulusType.TYPE);
		
		assertTrue(grid[0][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[0][1].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[0][2].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[0][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 3);
		assertTrue(grid[0][4].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[0][5].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[0][6].getCommunicationStimulus(attack).getIntensity() == 0);
		
		assertTrue(grid[1][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[1][1].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[1][2].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[1][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[1][4].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[1][5].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[1][6].getCommunicationStimulus(attack).getIntensity() == 0);
		
		assertTrue(grid[2][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[2][1].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[2][2].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[2][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[2][4].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[2][5].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[2][6].getCommunicationStimulus(attack).getIntensity() == 0);
		
		assertTrue(grid[3][0].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][1].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][2].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][4].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][5].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[3][6].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		
		assertTrue(grid[4][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[4][1].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[4][2].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[4][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[4][4].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[4][5].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()));
		assertTrue(grid[4][6].getCommunicationStimulus(attack).getIntensity() == 0);
		
		assertTrue(grid[5][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[5][1].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[5][2].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[5][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[5][4].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 2);
		assertTrue(grid[5][5].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[5][6].getCommunicationStimulus(attack).getIntensity() == 0);
		
		assertTrue(grid[6][0].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[6][1].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[6][2].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[6][3].getCommunicationStimulus(attack).getIntensity() == a.getAgentType().getStimulusIncrement(attack.getName()) / 3);
		assertTrue(grid[6][4].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[6][5].getCommunicationStimulus(attack).getIntensity() == 0);
		assertTrue(grid[6][6].getCommunicationStimulus(attack).getIntensity() == 0);
	}
	
	
}
