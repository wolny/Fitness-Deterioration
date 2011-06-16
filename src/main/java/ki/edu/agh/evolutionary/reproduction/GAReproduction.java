package ki.edu.agh.evolutionary.reproduction;

import ki.edu.agh.population.Population;

public class GAReproduction implements ReproductionAlgorithm {

	private ReproductionAlgorithm mutation;
	private ReproductionAlgorithm crossover;

	public ReproductionAlgorithm getMutation() {
		return mutation;
	}

	public void setMutation(ReproductionAlgorithm mutation) {
		this.mutation = mutation;
	}

	public ReproductionAlgorithm getCrossover() {
		return crossover;
	}

	public void setCrossover(ReproductionAlgorithm crossover) {
		this.crossover = crossover;
	}

	@Override
	public Population reproducePopulation(Population population) {
		// TODO Auto-generated method stub
		return null;
	}

}
