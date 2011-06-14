package ki.edu.agh.deterioration;

import ki.edu.agh.point.EuclideanSpacePoint;

public class PointWithFitnessImpl implements PointWithFitness {
	private EuclideanSpacePoint point;
	private double fitness;

	public PointWithFitnessImpl(EuclideanSpacePoint point, double fitness) {
		this.point = point;
		this.fitness = fitness;
	}

	@Override
	public EuclideanSpacePoint getPoint() {
		return point;
	}

	@Override
	public double getFintess() {
		return fitness;
	}

}
