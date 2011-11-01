package ki.edu.agh.evolutionary.selection;

import java.util.Random;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public class TournamentSelection implements SelectionAlgorithm {

	/**
	 * number of elements picked for tournament
	 */
	private int k = 4; // default value is 4

	/**
	 * we need uniform random number generator
	 */
	private Random rand = new Random();

	public TournamentSelection() {
	}

	public TournamentSelection(int k) {
		this.k = k;
	}

	public int getK() {
		return k;
	}

	@Override
	public Population select(Population population, int mateSize) {
		Individual[] mate = new Individual[mateSize];
		int size = 0;

		for (int i = 0; i < mateSize; i++) {
			int index = rand.nextInt(population.getSize());
			Individual winner = population.get(index);
			for (int j = 0; j < k; j++) {
				int index2 = rand.nextInt(population.getSize());
				Individual opponent = population.get(index2);
				if (opponent.getFitness() < winner.getFitness()) {
					winner = opponent;
				}
			}
			mate[size++] = winner;
		}
		population.updatePopulation(mate, mateSize);
		return population;
	}

	public void setK(int k) {
		this.k = k;
	}

}
