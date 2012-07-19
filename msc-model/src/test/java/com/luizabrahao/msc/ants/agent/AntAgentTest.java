package com.luizabrahao.msc.ants.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;

public class AntAgentTest {
	@Test
	public void incrementTest() {
		PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		final AntAgent a = new AntAgent("a", WorkerType.TYPE, grid[1][1], false);
		
		a.incrementStimulusIntensity(ForageStimulusType.TYPE);
		
		assertTrue(grid[0][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0.001);
		assertTrue(grid[1][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
	
		grid = AntEnvironmentFactory.createPheromoneNodeGrid(7, 7);
		
		assertTrue(grid[0][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[0][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[1][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0.001);
		assertTrue(grid[1][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][0].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][1].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
		assertTrue(grid[2][2].getCommunicationStimulus(ForageStimulusType.TYPE).getIntensity() == 0);
	}
	
	
}
