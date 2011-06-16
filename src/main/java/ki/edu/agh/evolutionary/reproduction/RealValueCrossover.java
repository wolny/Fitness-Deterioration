package ki.edu.agh.evolutionary.reproduction;

import ki.edu.agh.population.Population;

public class RealValueCrossover implements ReproductionAlgorithm {
	private double crossoverProbability;

	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	@Override
	public Population reproducePopulation(Population population) {
		// TODO Auto-generated method stub
		return null;
	}

}
