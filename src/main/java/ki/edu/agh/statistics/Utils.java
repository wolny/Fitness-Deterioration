package ki.edu.agh.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.deterioration.DeteriorationFunction;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.fintess.StandardFitnessFunction;
import ki.edu.agh.functors.UniModalFunction;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public class Utils {
	private static Random rand = new Random();

	public static EuclideanSpacePoint populationVariance(
			Collection<EuclideanSpacePoint> points, int dimension) {
		int size = points.size();

		EuclideanSpacePoint mean = meanPoint(points, dimension);
		ArrayList<EuclideanSpacePoint> ps = new ArrayList<EuclideanSpacePoint>(
				size);
		for (EuclideanSpacePoint point : points) {
			ps.add(mean.substract(point));
		}

		ArrayList<EuclideanSpacePoint> sqrPoints = new ArrayList<EuclideanSpacePoint>(
				size);
		for (EuclideanSpacePoint point : ps) {
			sqrPoints.add(point.sqr());
		}
		return sum(sqrPoints, dimension).divide((double) size);
	}

	public static EuclideanSpacePoint meanPoint(
			Collection<EuclideanSpacePoint> points, int dimension) {
		return sum(points, dimension).divide((double) points.size());
	}

	public static EuclideanSpacePoint sum(
			Collection<EuclideanSpacePoint> points, int dimension) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			double d = 0.0;
			for (EuclideanSpacePoint point : points) {
				d += point.getCoordinate(i);
			}
			coords[i] = d;
		}
		return new EuclideanSpacePoint(coords);
	}

	public static EuclideanSpacePoint createNullPoint(int dimension) {
		double[] coordinates = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coordinates[i] = 0.0;
		}
		return new EuclideanSpacePoint(coordinates);
	}

	public static EuclideanSpacePoint generateRandomPoint(int dimension) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = rand.nextDouble();
		}
		return new EuclideanSpacePoint(coords);
	}

	/**
	 * returns point of a given dimension, whose coordinates are inititialized
	 * with initValue
	 * 
	 * @param dimension
	 * @param initValue
	 * @return
	 */
	public static EuclideanSpacePoint createPoint(int dimension,
			double initValue) {
		double[] coords = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			coords[i] = initValue;
		}
		return new EuclideanSpacePoint(coords);
	}

	public static Population createRandomPopulation(int dimension,
			FitnessFunction fitness, int size) {
		FixedSizePopulation population = new FixedSizePopulation(size);
		for (int i = 0; i < size; i++) {
			Individual indiv = createRandomIndividual(dimension);
			indiv.setFitness(fitness.computeFitness(indiv.getPhenotype()));
			population.add(indiv);
		}
		return population;
	}

	public static Individual createRandomIndividual(int dimension) {
		return new Individual(new EuclideanSpacePhenotype(
				generateRandomPoint(dimension)));
	}

	public static Population createRandomUniModalPopulation(int dimension,
			int size) {
		FitnessFunction fitness = new StandardFitnessFunction(
				new UniModalFunction());
		return createRandomPopulation(dimension, fitness, size);
	}

	public static DeteriorationFunction createGaussianForCluster(
			Cluster<PointWithFitness> cluster) {
		// TODO: implement
		throw new RuntimeException("not implemented");
	}

	public static void main(String[] args) {
		int dimension = 3;
		EuclideanSpacePoint p = generateRandomPoint(dimension);
		System.out.println(p);

		System.out.println(createNullPoint(dimension));
		EuclideanSpacePoint p1 = new EuclideanSpacePoint(new double[] { -2.1 });
		EuclideanSpacePoint p2 = new EuclideanSpacePoint(new double[] { -1.3 });
		EuclideanSpacePoint p3 = new EuclideanSpacePoint(new double[] { -0.4 });
		EuclideanSpacePoint p4 = new EuclideanSpacePoint(new double[] { 1.9 });
		EuclideanSpacePoint p5 = new EuclideanSpacePoint(new double[] { 5.1 });
		EuclideanSpacePoint p6 = new EuclideanSpacePoint(new double[] { 6.2 });
		System.out.println(meanPoint(Arrays.asList(p1, p2, p3, p4, p5, p6), 1));
		System.out.println(populationVariance(
				Arrays.asList(p1, p2, p3, p4, p5, p6), 1));
	}
}
