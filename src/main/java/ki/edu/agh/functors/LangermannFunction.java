package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class LangermannFunction implements Functor {
	private static final int M = 5;
	private final double[] a = { 3, 5, 2, 1, 7 };
	private final double[] b = { 5, 2, 1, 4, 9 };
	private final double[] c = { 1, 2, 5, 2, 3 };

	@Override
	public double getValue(EuclideanSpacePoint point) {
		double x = point.getCoordinate(0);
		double y = point.getCoordinate(1);

		double sum = 0.0;
		for (int i = 0; i < M; i++) {
			double xa = x - a[i];
			double yb = y - b[i];

			double z = c[i] * Math.exp(-1 / Math.PI * (xa * xa + yb * yb))
					* Math.cos(Math.PI * (xa * xa + yb * yb));
			sum += z;
		}
		return sum + 6.0;
	}

}
