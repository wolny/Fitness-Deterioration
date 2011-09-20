package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class TriModalFunction implements Functor {

	@Override
	public double getValue(EuclideanSpacePoint point) {
		double x = point.getCoordinate(0);
		double y = point.getCoordinate(1);

		double result = 2.0
				* Math.exp(-((x + 1.) * (x + 1.) + (y + 1.) * (y + 1.)))
				+ 1.5
				* Math.exp(-((x - 1.1) * (x - 1.1) + y * y))
				+ 4.0
				* Math.exp(-3.0
						* ((x + 1.5) * (x + 1.5) + (y - 1.5) * (y - 1.5)));
		return result;
	}

}
