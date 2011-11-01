package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class UniModalFunction implements Functor {

	@Override
	public double getValue(EuclideanSpacePoint point) {
		double x = point.getCoordinate(0);
		double y = point.getCoordinate(1);
		double result = 2.0 * Math.exp(-((x + 1.) * (x + 1.) + (y + 1.)
				* (y + 1.)));

		return result;
	}
}
