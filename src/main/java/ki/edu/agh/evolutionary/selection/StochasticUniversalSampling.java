package ki.edu.agh.evolutionary.selection;

import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualComparator;
import ki.edu.agh.population.Population;
import ki.edu.agh.statistics.Utils;

public class StochasticUniversalSampling implements SelectionAlgorithm {
	private static final Logger LOG = Logger
			.getLogger(StochasticUniversalSampling.class);
	private final double[] fitnessRangeArray;
	private final Random rand;

	public StochasticUniversalSampling(int maxSize) {
		this.fitnessRangeArray = new double[maxSize];
		this.rand = new Random();
	}

	/**
	 * WARN: we treat higher fitness as better, so fitness assignment process
	 * should deal with minimization or maximization accordingly; SUS will not
	 * work if fitnessRange < 0 (interval will be bigger than fitnessRange!)
	 */
	@Override
	public Population select(Population population, int mateSize) {
		if (fitnessRangeArray.length < mateSize) {
			throw new RuntimeException(
					"Maximium size of FitnessRangeArray is less than the mate size");
		}
		Individual[] individuals = population.getAllMembers();
		createFitnessRangeArray(individuals, population.getSize());
		Individual[] result = new Individual[mateSize];
		double fitnessRange = fitnessRangeArray[population.getSize() - 1];

		if (fitnessRange < 0) {
			// if fitnessRange < 0 the SUS will not work
			LOG.warn("fitnessRange < 0 => return parent population");
			return population;
		}

		double interval = fitnessRange / mateSize;
		LOG.debug(">>> popSize: + " + population.getSize() + "; fitnessRange: "
				+ fitnessRange);
		LOG.debug(">>> interval: " + interval);
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
		population.updatePopulation(result, mateSize);
		// printPopulation(population);
		return population;
	}

	private void createFitnessRangeArray(Individual[] individuals, int popSize) {
		fitnessRangeArray[0] = individuals[0].getFitness();
		for (int i = 1; i < popSize; i++) {
			fitnessRangeArray[i] = individuals[i].getFitness()
					+ fitnessRangeArray[i - 1];
		}
	}

	public static void main(String[] args) {
		Population pop = Utils.createRandomUniModalPopulation(2, 100);
		System.out.println(pop);
		System.out.println();
		StochasticUniversalSampling sus = new StochasticUniversalSampling(100);
		pop = sus.select(pop, 50);
		printPopulation(pop);
	}

	public static void printPopulation(Population pop) {
		Arrays.sort(pop.getAllMembers(), new IndividualComparator());
		for (Individual i : pop) {
			System.out.println(i);
		}
	}
}
