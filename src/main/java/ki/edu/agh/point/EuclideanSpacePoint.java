package ki.edu.agh.point;

/**
 * General class which represents point in a multidimensional space. Generic
 * algorithm must apply to all numeric data types not only to Double
 * 
 * @author slo
 */
public class EuclideanSpacePoint implements MetricSpacePoint {
	private double[] coordinates;
	private int dimension;

	public EuclideanSpacePoint(double[] coordinates, int dimension) {
		this.coordinates = coordinates;
		this.dimension = dimension;
	}

	public EuclideanSpacePoint(double[] coordinates) {
		this.coordinates = coordinates;
		this.dimension = coordinates.length;
	}

	@Override
	public double getDistance(MetricSpacePoint point) {
		// if point is instance of EuclideanSpacePoint return Euclidean distance
		if (point instanceof EuclideanSpacePoint) {
			EuclideanSpacePoint euclideanPoint = (EuclideanSpacePoint) point;
			double distance = 0.0;
			for (int i = 0; i < dimension; i++) {
				double x_i = coordinates[i];
				double y_i = euclideanPoint.getCoordinate(i);
				distance += (x_i - y_i) * (x_i - y_i);
			}

			return Math.sqrt(distance);
		}
		// otherwise we may easily assume that point instance knows how to
		// compute distance and we use that information
		return point.getDistance(this);
	}

	public EuclideanSpacePoint add(EuclideanSpacePoint point) {
		if (point.getDimension() != dimension) {
			throw new IllegalArgumentException(
					"Dimension of points must be equal");
		}
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] + point.getCoordinate(i);
		}
		return new EuclideanSpacePoint(coords);
	}

	public EuclideanSpacePoint substract(EuclideanSpacePoint point) {
		if (point.getDimension() != dimension) {
			throw new IllegalArgumentException(
					"Dimension of points must be equal");
		}
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] - point.getCoordinate(i);
		}
		return new EuclideanSpacePoint(coords);
	}

	public EuclideanSpacePoint divide(double divisor) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] / divisor;
		}
		return new EuclideanSpacePoint(coords);
	}

	public EuclideanSpacePoint multiply(double v) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] * v;
		}
		return new EuclideanSpacePoint(coords);
	}

	/**
	 * 
	 * @return dot product p*p
	 */
	public EuclideanSpacePoint sqr() {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] * coordinates[i];
		}
		return new EuclideanSpacePoint(coords);
	}

	public double getCoordinate(int i) {
		if (i < 0 || i >= dimension) {
			throw new IllegalArgumentException(
					"Coordinate's index must be between 0 and dimension - 1");
		}
		return coordinates[i];
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public int getDimension() {
		return dimension;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < dimension - 1; i++) {
			builder.append(coordinates[i] + "	");
		}
		builder.append(coordinates[dimension - 1]);
		return builder.toString();
	}
}
