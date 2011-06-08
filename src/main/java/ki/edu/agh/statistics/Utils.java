package ki.edu.agh.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.fintess.StandardFitnessFunction;
import ki.edu.agh.functors.UniModalFunction;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Point;
import ki.edu.agh.population.Population;
import ki.edu.agh.population.RealVectorPhenotype;

public class Utils {
	private static Random rand = new Random();

	public static Point populationVariance(Collection<Point> points,
			int dimension) {
		int size = points.size();

		Point mean = meanPoint(points, dimension);
		ArrayList<Point> ps = new ArrayList<Point>(size);
		for (Point point : points) {
			ps.add(mean.substract(point));
		}

		ArrayList<Point> sqrPoints = new ArrayList<Point>(size);
		for (Point point : ps) {
			sqrPoints.add(point.sqr());
		}
		return sum(sqrPoints, dimension).divide((double) size);
	}

	public static Point meanPoint(Collection<Point> points, int dimension) {
		return sum(points, dimension).divide((double) points.size());
	}

	public static Point sum(Collection<Point> points, int dimension) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			double d = 0.0;
			for (Point point : points) {
				d += point.getCoordinate(i);
			}
			coords[i] = d;
		}
		return new Point(coords);
	}

	public static Point createNullPoint(int dimension) {
		double[] coordinates = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coordinates[i] = 0.0;
		}
		return new Point(coordinates);
	}

	public static Point generateRandomPoint(int dimension) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = rand.nextDouble();
		}
		return new Point(coords);
	}

	/**
	 * returns point of a given dimension, whose coordinates are inititialized
	 * with initValue
	 * 
	 * @param dimension
	 * @param initValue
	 * @return
	 */
	public static Point createPoint(int dimension, double initValue) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = initValue;
		}
		return new Point(coords);
	}

	public static Population createRandomPopulation(int dimension,
			FitnessFunction fitness) {
		int size = 10;
		FixedSizePopulation population = new FixedSizePopulation(size);
		for (int i = 0; i < size; i++) {
			Individual indiv = new Individual(new RealVectorPhenotype(
					generateRandomPoint(dimension)));
			indiv.setFitness(fitness.computeFitness(indiv.getPhenotype()));
			population.add(indiv);
		}
		return population;
	}

	public static Population createRandomUniModalPopulation(int dimension) {
		FitnessFunction fitness = new StandardFitnessFunction(
				new UniModalFunction());
		return createRandomPopulation(dimension, fitness);
	}

	public static void main(String[] args) {
		int dimension = 3;
		Point p = generateRandomPoint(dimension);
		System.out.println(p);

		System.out.println(createNullPoint(dimension));
		Point p1 = new Point(new double[] { -2.1 });
		Point p2 = new Point(new double[] { -1.3 });
		Point p3 = new Point(new double[] { -0.4 });
		Point p4 = new Point(new double[] { 1.9 });
		Point p5 = new Point(new double[] { 5.1 });
		Point p6 = new Point(new double[] { 6.2 });
		System.out.println(meanPoint(Arrays.asList(p1, p2, p3, p4, p5, p6), 1));
		System.out.println(populationVariance(
				Arrays.asList(p1, p2, p3, p4, p5, p6), 1));
	}
}
