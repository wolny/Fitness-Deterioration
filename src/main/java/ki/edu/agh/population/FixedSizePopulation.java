package ki.edu.agh.population;

import java.util.Iterator;

public class FixedSizePopulation implements Population {

	private Individual[] individuals;
	private int populationSizeLimit;
	private int currentSize;

	public FixedSizePopulation(int populationSizeLimit) {
		this.populationSizeLimit = populationSizeLimit;
		this.individuals = new Individual[populationSizeLimit];
		this.currentSize = 0;
	}

	@Override
	public Iterator<Individual> iterator() {
		return new Iterator<Individual>() {
			private int currentIndex;

			@Override
			public boolean hasNext() {
				return currentIndex < individuals.length;
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

}
