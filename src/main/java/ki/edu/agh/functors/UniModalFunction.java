package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;

public class UniModalFunction implements Functor {

	@Override
	public double getValue(EuclideanSpacePoint point) {
		double sqrSum = 0.;
		for (double coord : point.getCoordinates()) {
			sqrSum += coord * coord;
		}

		return 2. * Math.exp(-sqrSum);
	}

}
