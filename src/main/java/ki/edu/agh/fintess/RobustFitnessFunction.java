package ki.edu.agh.fintess;

import ki.edu.agh.population.Phenotype;

/**
 * fitness algorithm implementation which increase probability of finding robust
 * solution of a given optimization problem; it takes a problem-dependent
 * fitness function f as a parameter and for each phenotype computes mean value
 * of fitness f for a number of noisy phenotype generated from a given one
 * 
 * @author slo
 * 
 */
public class RobustFitnessFunction implements FitnessFunction {
	/**
	 * the number of noisy phenotype to be taken to compute mean fitness value
	 */
	public static final int sampleCount = 10;

	/**
	 * concrete problem-dependent fitness function algorithm
	 */
	private FitnessFunction fitness;

	/**
	 * default noise generator
	 */
	private PhenotypeNoiseGenerator noiseGenerator = new NormalyDistributedNoiseGenerator(
			1.0);

	public RobustFitnessFunction(double standardDeviation,
			FitnessFunction fitness) {
		this(fitness);
		this.noiseGenerator = new NormalyDistributedNoiseGenerator(
				standardDeviation);
	}

	public RobustFitnessFunction(FitnessFunction fitness) {
		this.fitness = fitness;
	}

	/**
	 * computes fintess function sampleCount times and returns mean value
	 */
	@Override
	public double computeFitness(Phenotype phenotype) {
		double meanFitness = 0.;

		for (int i = 0; i < sampleCount; i++) {
			Phenotype noisyPhenotype = noiseGenerator.addRandomNoise(phenotype);
			meanFitness += fitness.computeFitness(noisyPhenotype);
		}

		return meanFitness / sampleCount;
	}

	public void setNoiseGenerator(PhenotypeNoiseGenerator noiseGenerator) {
		this.noiseGenerator = noiseGenerator;
	}

}
