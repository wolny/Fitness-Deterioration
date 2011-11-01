package ki.edu.agh.evolutionary.reproduction;

import java.util.Random;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;

public class RealValueMutation implements Mutation {
	private final Random rand = new Random();
	private double standardDeviation;

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	@Override
	public Individual mutate(Individual i) {
		IndividualWithRealVectorPhenotype irvp = (IndividualWithRealVectorPhenotype) i;
		EuclideanSpacePoint mPoint = addNormDistVector(((EuclideanSpacePhenotype) i
				.getPhenotype()).getPoint());
		EuclideanSpacePhenotype mutatedEsp = new EuclideanSpacePhenotype(mPoint);
		irvp.setPhenotype(mutatedEsp);
		return irvp;
	}

	private EuclideanSpacePoint addNormDistVector(EuclideanSpacePoint esp) {
		double[] d = new double[esp.getDimension()];
		for (int i = 0; i < esp.getDimension(); i++) {
			d[i] = rand.nextGaussian() * standardDeviation;
		}
		EuclideanSpacePoint normPoint = new EuclideanSpacePoint(d);
		return esp.add(normPoint);
	}
}
