package ki.edu.agh.fintess;

import ki.edu.agh.functors.Functor;
import ki.edu.agh.population.Phenotype;

public class RobustFitnessFunction extends StandardFitnessFunction {
	private static final int sampleCount = 10;
	private PhenotypeNoiseGenerator noiseGenerator;

	public RobustFitnessFunction(double standardDeviation, Functor functor) {
		super(functor);
		this.noiseGenerator = new NormalyDistributedNoiseGenerator(
				standardDeviation);
	}

	/**
	 * computes fintess function sampleCount times and returns mean value
	 */
	@Override
	public double computeFitness(Phenotype phenotype) {
		double meanFitness = 0.;

		for (int i = 0; i < sampleCount; i++) {
			Phenotype noisyPhenotype = noiseGenerator.addRandomNoise(phenotype);
			meanFitness += super.computeFitness(noisyPhenotype);
		}

		return meanFitness / sampleCount;
	}

}
