package ki.edu.agh.fintess;

import ki.edu.agh.population.Phenotype;

public interface FitnessFunction {
	double computeFitness(Phenotype phenotype);
}
