package ki.edu.agh.fintess;

import java.util.Random;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.Phenotype;
import ki.edu.agh.population.EuclideanSpacePhenotype;

/**
 * adds normally distributed vector \delta to a given phenotype;
 * 
 * @author slo
 * 
 */
public class NormalyDistributedNoiseGenerator implements
		PhenotypeNoiseGenerator {

	private Random random = new Random();
	private double standardDeviation;

	public NormalyDistributedNoiseGenerator(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	/**
	 * we use the fact that if Z is a normal random variable with parameters
	 * mi=0 and sigma=1 then X = a * Z + b is a normal random variable with
	 * expected value = b and standard deviation = a. We get the random variable
	 * Z from Random.nextGaussian()
	 */
	@Override
	public Phenotype addRandomNoise(Phenotype phenotype) {
		if (!(phenotype instanceof EuclideanSpacePhenotype)) {
			throw new RuntimeException(getClass().getName()
					+ " can only be applied to "
					+ EuclideanSpacePhenotype.class.getName());
		}
		EuclideanSpacePoint point = ((EuclideanSpacePhenotype) phenotype)
				.getPoint();
		int dimension = point.getDimension();
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = random.nextGaussian() * standardDeviation;
		}

		EuclideanSpacePoint noisyPoint = point.add(new EuclideanSpacePoint(
				coords));
		return new EuclideanSpacePhenotype(noisyPoint);
	}

}
