package ki.edu.agh.evolutionary.reproduction;

import ki.edu.agh.population.Individual;

public interface Crossover {
	Individual recombine(Individual i1, Individual i2);
}
