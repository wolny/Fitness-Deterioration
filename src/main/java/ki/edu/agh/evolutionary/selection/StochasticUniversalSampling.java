package ki.edu.agh.evolutionary.selection;

import java.util.Random;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public class StochasticUniversalSampling implements SelectionAlgorithm {

	private final double[] fitnessRangeArray;
	private Individual[] currentPopulation;
	private Individual[] newPopulation;
	private final Random rand;

	public StochasticUniversalSampling(int maxSize) {
		this.fitnessRangeArray = new double[maxSize];
		this.currentPopulation = new Individual[maxSize];
		this.newPopulation = new Individual[maxSize];
		this.rand = new Random();
	}

	/**
	 * WARN: we treat higher fitness as better, so fitness assignment process
	 * should deal with minimization or maximization accordingly
	 */
	@Override
	public Individual[] select(Population population, int mateSize) {
		if (fitnessRangeArray.length < mateSize) {
			throw new RuntimeException(
					"Maximium size of FitnessRangeArray is less than the mate size");
		}
		Individual[] individuals = population.getAllMembers();
		createFitnessRangeArray(individuals);
		Individual[] result = new Individual[mateSize];

		double interval = fitnessRangeArray[fitnessRangeArray.length - 1]
				/ mateSize;
		double value = rand.nextDouble() * interval;

		int index = 0;
		for (int i = 0; i < mateSize; i++) {
			while (fitnessRangeArray[index] < value) {
				index++;
			}
			value += interval;
			result[i] = individuals[index];
		}

		// swapPopulations();
		// after swap return currentPopulation
		return result;
	}

	protected void swapPopulations() {
		Individual[] tmp = currentPopulation;
		this.currentPopulation = this.newPopulation;
		this.newPopulation = tmp;
	}

	private void createFitnessRangeArray(Individual[] individuals) {
		fitnessRangeArray[0] = individuals[0].getFitness();
		for (int i = 1; i < individuals.length; i++) {
			fitnessRangeArray[i] = individuals[i].getFitness()
					+ fitnessRangeArray[i - 1];
		}
	}
}
