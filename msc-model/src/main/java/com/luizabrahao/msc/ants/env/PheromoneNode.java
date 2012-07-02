package com.luizabrahao.msc.ants.env;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import com.luizabrahao.msc.model.env.BasicNode;

/**
 * Introduces the pheromoneIntensity field that is used for ant related
 * simulations. This class is thread-safe.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public class PheromoneNode extends BasicNode {
	public static final double DECAY_FACTOR = 0.1;
	@GuardedBy("this") private double pheromoneIntensity;
	
	public PheromoneNode(String id) {
		super(id);
		this.pheromoneIntensity = 0;
	}

	public synchronized double getPheromoneIntensity() { return pheromoneIntensity; }
	
	/**
	 * This should not be called directly from client code. The method is to be
	 * used during testing only! You should use update agents like
	 * StaticPheromoneUpdaterAgent to decay pheromone intensities. Updates must
	 * be done by the method updatePheromone.
	 * 
	 * @param pheromoneIntensity Double pheromone intensity in the node.
	 */
	public synchronized void setPheromoneIntensity(double pheromoneIntensity) { this.pheromoneIntensity = pheromoneIntensity; }

	/**
	 * Updates the pheromone intensity for this node following the rule:
	 * new intensity = current intensity * (1 - decay factor)
	 * 
	 */
	public synchronized void decayPheromoneIntensity() {
		this.pheromoneIntensity = this.pheromoneIntensity * (1 - PheromoneNode.DECAY_FACTOR);
		
	}
}