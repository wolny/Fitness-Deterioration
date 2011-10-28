package ki.edu.agh.evolutionary.reproduction;

import ki.edu.agh.population.Individual;

public interface Mutation {
	Individual mutate(Individual i);
}
