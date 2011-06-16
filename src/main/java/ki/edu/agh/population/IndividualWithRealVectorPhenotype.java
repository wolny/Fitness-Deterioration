package ki.edu.agh.population;

import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.point.MetricSpacePoint;

public class IndividualWithRealVectorPhenotype implements Individual,
		MetricSpacePoint, PointWithFitness {
	private double fitness;
	private EuclideanSpacePhenotype phenotype;
	private boolean isFitnessAssigned = false;

	public IndividualWithRealVectorPhenotype(EuclideanSpacePhenotype phenotype) {
		this.phenotype = phenotype;
	}

	@Override
	public double getFitness() {
		if (!isFitnessAssigned) {
			throw new IllegalStateException(
					"fitness has not been assigned to the individual");
		}
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
		isFitnessAssigned = true;
	}

	@Override
	public Phenotype getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(EuclideanSpacePhenotype phenotype) {
		this.phenotype = phenotype;
	}

	@Override
	public String toString() {
		return getPhenotype() + "	" + getFitness();
	}

	@Override
	public double getDistance(MetricSpacePoint point) {
		return point.getDistance(phenotype.getPoint());
	}

	@Override
	public EuclideanSpacePoint getPoint() {
		return phenotype.getPoint();
	}
}
