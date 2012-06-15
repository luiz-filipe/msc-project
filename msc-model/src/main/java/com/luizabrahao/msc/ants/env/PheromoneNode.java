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
	@GuardedBy("this") private double pheromoneIntensity;
	
	public PheromoneNode(String id) {
		super(id);
		this.pheromoneIntensity = 0;
	}

	public synchronized double getPheromoneIntensity() { return pheromoneIntensity; }
	
	/**
	 * This should not be called directly from client code. The method is to be
	 * used during testing only!
	 * 
	 * @param pheromoneIntensity Double pheromone intensity in the node.
	 */
	public synchronized void setPheromoneIntensity(double pheromoneIntensity) { this.pheromoneIntensity = pheromoneIntensity; }

	public synchronized void updatePheromoneIntensity() {
		this.pheromoneIntensity = this.pheromoneIntensity * (1 - StaticPheromoneUpdater.DECAY_FACTOR);
		
		if (this.pheromoneIntensity < 0) {
			this.pheromoneIntensity = 0;
		}
	}
}
