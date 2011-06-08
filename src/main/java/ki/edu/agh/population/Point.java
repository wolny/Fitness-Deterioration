package ki.edu.agh.population;

/**
 * General class which represents point in a multidimensional space. Generic
 * algorithm must apply to all numeric data types not only to Double
 * 
 * @author slo
 */
public class Point {
	private double[] coordinates;
	private int dimension;

	public Point(double[] coordinates, int dimension) {
		this.coordinates = coordinates;
		this.dimension = dimension;
	}

	public Point(double[] coordinates) {
		this.coordinates = coordinates;
		this.dimension = coordinates.length;
	}

	public double euclideanDistance(Point point) {
		double distance = 0.0;

		for (int i = 0; i < dimension; i++) {
			double x_i = coordinates[i];
			double y_i = point.getCoordinate(i);
			distance += (x_i - y_i) * (x_i - y_i);
		}

		return Math.sqrt(distance);
	}

	public Point add(Point point) {
		if (point.getDimension() != dimension) {
			throw new IllegalArgumentException(
					"Dimension of points must be equal");
		}
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] + point.getCoordinate(i);
		}
		return new Point(coords);
	}

	public Point substract(Point point) {
		if (point.getDimension() != dimension) {
			throw new IllegalArgumentException(
					"Dimension of points must be equal");
		}
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] - point.getCoordinate(i);
		}
		return new Point(coords);
	}

	public Point divide(double divisor) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = coordinates[i] / divisor;
		}
		return new Point(coords);
	}

	/**
	 * 
	 * @return dot product p*p
	 */
	public Point sqr() {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = ((Double) coordinates[i]) * ((Double) coordinates[i]);
		}
		return new Point(coords);
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
