package com.luizabrahao.msc.ants.env;

import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.BasicNode;
import com.luizabrahao.msc.model.env.CommunicationStimulus;

/**
 * A specialisation of <em>BasicNode</em> used to create environments for ant
 * simulations. Pheromone nodes have a list of chemical stimulus that are
 * present in the node. 
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public class PheromoneNode extends BasicNode {

	public PheromoneNode(String id) {
		super(id);
	}

	/**
	 * Tries to find the requested chemical communication stimulus in the list
	 * of communication stimulus. If one is present, returns it, if none is
	 * found, a new chemical communication stimulus is created, put in the list
	 * and returned.
	 * 
	 * @param chemicalCommStimulusType type of chemical communication stimulus
	 * @return chemical communication stimulus
	 */
	public synchronized ChemicalCommStimulus getCommunicationStimulus(ChemicalCommStimulusType chemicalCommStimulusType) {
		if (this.getCommunicationStimuli() != null) {
			for (CommunicationStimulus stimulus : this.getCommunicationStimuli()) {
				if (stimulus.getType() == chemicalCommStimulusType) {
					return (ChemicalCommStimulus) stimulus;
				}
			}
		}
		
		ChemicalCommStimulus c = new ChemicalCommStimulus(chemicalCommStimulusType);
		this.addCommunicationStimulus(c);
		
		return c;
	}
	
	/**
	 * Increments a communication stimulus' intensity by the requested amount.
	 * 
	 * @param chemicalCommStimulusType type of chemical communication stimulus
	 * @param amount amount to be incremented 
	 */
	public void incrementStimulusIntensity(ChemicalCommStimulusType chemicalCommStimulusType, double amount) {
		ChemicalCommStimulus c = this.getCommunicationStimulus(chemicalCommStimulusType);
		c.increaseIntensity(amount);
	}
}
