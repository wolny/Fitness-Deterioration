package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.statistics.Utils;

/**
 * exp(-(x^2 + y^2)) + exp(-((x-1.7)^2 + (y-1.7)^2))
 * @author slo
 *
 */
public class BiModalFunction implements Functor {

	// ranges: x[-2:4] y[-2:4]
	@Override
	public double getValue(EuclideanSpacePoint point) {
		double sqrSum = 0.;
		for (double coord : point.getCoordinates()) {
			sqrSum += coord * coord;
		}

		EuclideanSpacePoint p = point.substract(Utils.createPoint(point.getDimension(), 1.7));
		double sqrSum1 = 0.;
		for (double coord : p.getCoordinates()) {
			sqrSum1 += coord * coord;
		}

		return Math.exp(-sqrSum) + 1.4*Math.exp(-sqrSum1);
	}

}
