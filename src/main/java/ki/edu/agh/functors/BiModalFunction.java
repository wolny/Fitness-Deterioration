package ki.edu.agh.functors;

import ki.edu.agh.population.Point;
import ki.edu.agh.statistics.Utils;

public class BiModalFunction implements Functor {

	@Override
	public double getValue(Point point) {
		double sqrSum = 0.;
		for (double coord : point.getCoordinates()) {
			sqrSum += coord * coord;
		}

		Point p = point.substract(Utils.createPoint(point.getDimension(), 1.7));
		double sqrSum1 = 0.;
		for (double coord : p.getCoordinates()) {
			sqrSum1 += coord * coord;
		}

		return Math.exp(-sqrSum) + Math.exp(-sqrSum1);
	}

}
