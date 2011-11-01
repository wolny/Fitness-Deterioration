package ki.edu.agh.evolutionary.reproduction;

import java.util.Random;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;

public class RealValueCrossover implements Crossover {
	private final Random rand = new Random();
	private double standardDeviation;

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	@Override
	public Individual recombine(Individual i1, Individual i2) {
		EuclideanSpacePoint p1 = ((EuclideanSpacePhenotype) i1.getPhenotype())
				.getPoint();
		EuclideanSpacePoint p2 = ((EuclideanSpacePhenotype) i2.getPhenotype())
				.getPoint();
		EuclideanSpacePoint recombined = recombinePoint(p1, p2,
				p1.getDimension());
		EuclideanSpacePhenotype phenotype = new EuclideanSpacePhenotype(
				recombined);
		IndividualWithRealVectorPhenotype result = new IndividualWithRealVectorPhenotype(
				phenotype);
		return result;
	}

	/**
	 * returns new normally distributed point with mean = (p1+p2)/2 and stdDev =
	 * standardDeviation
	 * 
	 * @param p1
	 * @param p2
	 * @param dimension
	 * @return
	 */
	private EuclideanSpacePoint recombinePoint(EuclideanSpacePoint p1,
			EuclideanSpacePoint p2, int dimension) {
		double norm = rand.nextGaussian() * standardDeviation;
		return p1.add(p2.substract(p1).multiply(norm));
	}

}
