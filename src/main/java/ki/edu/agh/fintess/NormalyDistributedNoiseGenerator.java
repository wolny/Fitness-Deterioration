package ki.edu.agh.fintess;

import java.util.Random;

import ki.edu.agh.population.Phenotype;
import ki.edu.agh.population.Point;
import ki.edu.agh.population.RealVectorPhenotype;

/**
 * adds normally distributed vector \delta to a given phenotype;
 * 
 * @author slo
 * 
 */
public class NormalyDistributedNoiseGenerator implements
		PhenotypeNoiseGenerator {

	private Random random = new Random();
	double standardDeviation;

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
		Point point = phenotype.getPoint();
		int dimension = point.getDimension();
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = random.nextGaussian() * standardDeviation;
		}

		Point noisyPoint = point.add(new Point(coords));
		return new RealVectorPhenotype(noisyPoint);
	}

}
