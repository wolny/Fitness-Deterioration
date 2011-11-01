package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class RastriginFunction implements Functor {
	private final double A = 10.0;

	@Override
	public double getValue(EuclideanSpacePoint point) {
		int n = point.getDimension();
		double y = 10. * n;

		for (int i = 0; i < n; i++) {
			double x = point.getCoordinate(i);
			y += x * x - A * Math.cos(2. * Math.PI * x);
		}

		return y;
	}
}
