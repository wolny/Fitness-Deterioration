package ki.edu.agh.evolutionary.reproduction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public class GAReproduction implements ReproductionAlgorithm {
	private final Random rand = new Random();
	private double crossoverProbability;
	private double mutationProbability;
	private Mutation mutation;
	private Crossover crossover;

	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public Mutation getMutation() {
		return mutation;
	}

	public void setMutation(Mutation mutation) {
		this.mutation = mutation;
	}

	public Crossover getCrossover() {
		return crossover;
	}

	public void setCrossover(Crossover crossover) {
		this.crossover = crossover;
	}

	private void shufflePopulation(Population population) {
		List<Individual> list = Arrays.asList(population.getAllMembers());
		Collections.shuffle(list);
		Individual[] shuffled = list.toArray(new Individual[0]);
		population.updatePopulation(shuffled, population.getSize());
	}

	@Override
	public Population reproducePopulation(Population population) {
		// shuffle population to increase convergence it should be turned off
		shufflePopulation(population);
		Individual[] individuals = population.getAllMembers();
		for (int i = 0; i < population.getSize() - 1; i++) {
			if (rand.nextDouble() <= getCrossoverProbability()) {
				individuals[i] = getCrossover().recombine(individuals[i],
						individuals[i + 1]);
			}
			if (rand.nextDouble() <= getMutationProbability()) {
				individuals[i] = getMutation().mutate(individuals[i]);
			}
		}

		population.updatePopulation(individuals, population.getSize());

		return population;
	}

}
