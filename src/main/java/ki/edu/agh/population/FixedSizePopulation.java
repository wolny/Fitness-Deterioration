package ki.edu.agh.population;

import java.util.Arrays;
import java.util.Iterator;

public class FixedSizePopulation implements Population {

	private int currentSize;
	private Individual[] individuals;
	private int populationSizeLimit;

	public FixedSizePopulation(int populationSizeLimit) {
		this.populationSizeLimit = populationSizeLimit;
		this.individuals = new Individual[populationSizeLimit];
		this.currentSize = 0;
	}

	@Override
	public void add(Individual individual) {
		individuals[currentSize++] = individual;
	}

	@Override
	public void addAll(Individual[] individuals) {
		for (Individual i : individuals) {
			add(i);
		}
	}

	@Override
	public Individual get(int i) {
		return individuals[i];
	}

	@Override
	public Individual[] getAllMembers() {
		return individuals;
	}

	@Override
	public int getSize() {
		return currentSize;
	}

	public int getSizeLimit() {
		return populationSizeLimit;
	}

	@Override
	public Iterator<Individual> iterator() {
		return new Iterator<Individual>() {
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < getSize();
			}

			@Override
			public Individual next() {
				return individuals[currentIndex++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public void sortMembers() {
		Arrays.sort(individuals, 0, getSize(), new IndividualComparator());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Individual individual : individuals) {
			sb.append(individual + "\n");
		}

		return sb.toString();
	}

}
