package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class GriewangkFunction implements Functor {

	@Override
	public double getValue(EuclideanSpacePoint point) {
		double a = 1.0 / 4000;
		int n = point.getDimension();
		double sum = 0.0;
		double prod = 1.0;
		for (int i = 0; i < n; i++) {
			double x = point.getCoordinate(i);
			sum += x * x;
			prod *= Math.cos(x / Math.sqrt(i + 1));
		}
		return a * sum - prod + 1;
	}

}
