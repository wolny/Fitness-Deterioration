package ki.edu.agh.functors;

import ki.edu.agh.population.Point;

public class UniModalFunction implements Functor {

	@Override
	public double getValue(Point point) {
		double sqrSum = 0.;
		for (double coord : point.getCoordinates()) {
			sqrSum += coord * coord;
		}

		return 2. * Math.exp(-sqrSum);
	}

}
