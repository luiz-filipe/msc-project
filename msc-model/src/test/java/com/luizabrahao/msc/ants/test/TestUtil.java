package com.luizabrahao.msc.ants.test;

import com.luizabrahao.msc.ants.env.ChemicalCommStimulus;
import com.luizabrahao.msc.ants.env.ForageStimulusType;
import com.luizabrahao.msc.ants.env.PheromoneNode;

public class TestUtil {
	
	private TestUtil() {}
	
	public static void setIntensity(int startLine, int finishLine, int startColum, int finishColumn, double intensity, PheromoneNode[][] grid) {
		for (int l = startLine; l < finishLine; l++) {
			for (int c = startColum; c < finishColumn; c++) {
				ChemicalCommStimulus s = grid[l][c].getCommunicationStimulus(ForageStimulusType.TYPE);
				s.setIntensity(intensity);
			}
		}
	}
}
