package ki.edu.agh.fintess;

import ki.edu.agh.population.Phenotype;

public interface PhenotypeNoiseGenerator {
	/**
	 * adds ranodm noise to the phenotype properties; probability distribution
	 * of the noise depends on the implementation
	 * 
	 * @param phenotype
	 * @return
	 */
	Phenotype addRandomNoise(Phenotype phenotype);
}
