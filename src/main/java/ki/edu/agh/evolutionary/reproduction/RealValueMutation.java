package ki.edu.agh.evolutionary.reproduction;

import ki.edu.agh.population.Population;

public class RealValueMutation implements ReproductionAlgorithm {
	private double mutationProbability;

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	@Override
	public Population reproducePopulation(Population population) {
		// TODO Auto-generated method stub
		return null;
	}

}
