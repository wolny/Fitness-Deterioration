package ki.edu.agh.statistics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ki.edu.agh.clustering.optics.OpticsClustering;
import ki.edu.agh.clustering.optics.OpticsParamteres;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.deterioration.PointWithFitnessImpl;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.problem.Domain;
import ki.edu.agh.problem.Interval;
import ki.edu.agh.problem.ProblemDomain;

public class PointGenerator {

	private static final String DATA_SET = "dataSet";

	public static Collection<EuclideanSpacePoint> generateUniformCircle2D(
			EuclideanSpacePoint center, double radius, int count) {

		Random rand = new Random();
		double doubleRadius = 2 * radius;
		double a = center.getCoordinate(0);
		double b = center.getCoordinate(1);

		double lrc_x = a - radius;
		double lrc_y = b - radius;

		List<EuclideanSpacePoint> result = new ArrayList<EuclideanSpacePoint>(
				count);
		while (count > 0) {
			double x = lrc_x + rand.nextDouble() * doubleRadius;
			double y = lrc_y + rand.nextDouble() * doubleRadius;

			if (isInCircle(center, radius, x, y)) {
				result.add(new EuclideanSpacePoint(new double[] { x, y }));
				count--;
			}
		}

		return result;
	}

	public static Collection<EuclideanSpacePoint> generateGaussianPoints2D(
			EuclideanSpacePoint mean, double standardDeviation, int count) {

		Random rand = new Random();

		List<EuclideanSpacePoint> result = new ArrayList<EuclideanSpacePoint>(
				count);

		while (count > 0) {
			double x = rand.nextGaussian();
			double y = rand.nextGaussian();

			EuclideanSpacePoint point = new EuclideanSpacePoint(new double[] {
					x, y });
			point = point.multiply(standardDeviation);
			result.add(point.add(mean));
			count--;
		}

		return result;
	}

	/**
	 * returns collection of points which constitute a mesh in multidimensional
	 * space; mesh consist of noOfMeshPoinstPerDim^domain.dimension
	 * 
	 * @param noOfMeshPointPerDim
	 * @param domain
	 * @return
	 */
	public static Collection<EuclideanSpacePoint> generatePointsFromDomain(
			int noOfMeshPointPerDim, Domain domain) {
		int spaceDimension = domain.getSpaceDimension();
		int numOfPoints = (int) Math.pow(noOfMeshPointPerDim, spaceDimension);
		List<EuclideanSpacePoint> mesh = new ArrayList<EuclideanSpacePoint>(
				numOfPoints);

		List<List<Double>> dimCoords = new ArrayList<List<Double>>(
				spaceDimension);

		for (Interval i : domain.getMultidimensionalCube()) {
			List<Double> coords = new ArrayList<Double>(noOfMeshPointPerDim);
			double intervalStart = i.getIntervalStart();
			double gap = Math.abs(i.getIntervalStop() - intervalStart)
					/ noOfMeshPointPerDim;
			for (int k = 0; k < noOfMeshPointPerDim; k++) {
				coords.add(intervalStart + k * gap);
			}

			dimCoords.add(coords);
		}

		comb(0, new double[spaceDimension], dimCoords, mesh);

		return mesh;
	}

	public static List<PointWithFitness> createPointsWithFitness(
			Collection<EuclideanSpacePoint> points, Functor functor) {
		List<PointWithFitness> result = new ArrayList<PointWithFitness>(
				points.size());

		for (EuclideanSpacePoint point : points) {
			result.add(new PointWithFitnessImpl(point, functor.getValue(point)));
		}
		return result;
	}

	/**
	 * !!! used for printing deteriorated fitness landscape
	 */
	public static List<PointWithFitness> createPointsWithFitnessForProblemDomain(
			ProblemDomain problem, int numOfNodes) {
		Collection<EuclideanSpacePoint> points = generatePointsFromDomain(
				numOfNodes, problem.getDomain());

		List<PointWithFitness> result = new ArrayList<PointWithFitness>(
				points.size());
		FitnessFunction fitnessFunction = problem.getFitnessFunction();
		for (EuclideanSpacePoint point : points) {
			EuclideanSpacePhenotype esp = new EuclideanSpacePhenotype(point);
			double fitness = fitnessFunction.computeFitness(esp);
			PointWithFitness pwf = new PointWithFitnessImpl(point, fitness);
			result.add(pwf);
		}

		return result;
	}

	public static Collection<PointWithFitness> convertPopulationToPointsWithFitness(
			Population population, FitnessFunction fitnessFunction) {
		List<PointWithFitness> result = new ArrayList<PointWithFitness>(
				population.getSize());

		for (Individual individual : population) {
			EuclideanSpacePoint point = ((EuclideanSpacePhenotype) individual
					.getPhenotype()).getPoint();
			PointWithFitness pwf = new PointWithFitnessImpl(point,
					individual.getFitness());
			result.add(pwf);
		}

		return result;
	}

	private static void comb(int i, double[] coords,
			List<List<Double>> dimCoords, List<EuclideanSpacePoint> mesh) {
		int lastDim = dimCoords.size() - 1;
		if (i == lastDim) {
			for (double coord : dimCoords.get(lastDim)) {
				coords[lastDim] = coord;
				mesh.add(new EuclideanSpacePoint(Arrays.copyOf(coords,
						coords.length)));
			}
		} else {
			for (double coord : dimCoords.get(i)) {
				coords[i] = coord;
				comb(i + 1, coords, dimCoords, mesh);
			}
		}

	}

	private static boolean isInCircle(EuclideanSpacePoint center,
			double radius, double x, double y) {
		double a = center.getCoordinate(0);
		double b = center.getCoordinate(1);
		double value = (x - a) * (x - a) + (y - b) * (y - b);
		double rSqr = radius * radius;

		return value <= rSqr;
	}

	public static void main(String[] args) throws IOException {
		// Collection<EuclideanSpacePoint> points = createDataSet1();
		// writePoints(DATA_SET, points);

		Collection<EuclideanSpacePoint> points = readPoints(DATA_SET);

		PrintUtils.writePoints("cluster", points);
		// PrintUtils.writePoints("circle", points);

		double epsilon = 0.5;
		int minPoints = 20;
		OpticsClustering<EuclideanSpacePoint> optics = new OpticsClustering<EuclideanSpacePoint>(
				new OpticsParamteres(minPoints, epsilon));
		optics.setDataSet(points);
		// optics.cluster(new OpticsParamteres(minPoints, 0.1));
		//
		// String prefix = "cluster";
		// int i = 0;
		// for (Cluster<EuclideanSpacePoint> cluster : clusters) {
		// PrintUtils.writePoints(prefix + i++, cluster.getClusterPoints());
		// }

		// List<Double> baseCoords = Arrays.asList(0., 1., 2.);
		// int dim = 3;
		//
		// List<List<Double>> dimCoords = new ArrayList<List<Double>>(dim);
		// for (int i = 0; i < dim; i++) {
		// dimCoords.add(new ArrayList<Double>(baseCoords));
		// }
		//
		// List<EuclideanSpacePoint> mesh = new
		// ArrayList<EuclideanSpacePoint>();
		// comb(0, new double[dim], dimCoords, mesh);
		// for (EuclideanSpacePoint point : mesh) {
		// System.out.println(point);
		// }

		// Domain domain = new Domain();
		// int dim = 2;
		// int numOfNodes = 160;
		// List<Interval> cube = new ArrayList<Interval>(dim);
		// for (int i = 0; i < dim; i++) {
		// cube.add(new Interval(-2., 4.));
		// }
		// domain.setMultidimensionalCube(cube);
		//
		// PrintUtils.writeProblemPoints("pwf", new MultimodalRealSpaceProblem(
		// domain, new StandardFitnessFunction(new BiModalFunction())),
		// numOfNodes);
	}

	public static Collection<EuclideanSpacePoint> createDataSet1() {
		Collection<EuclideanSpacePoint> points = generateUniformCircle2D(
				new EuclideanSpacePoint(new double[] { 0., 0. }), 3., 600);

		Collection<EuclideanSpacePoint> points1 = generateGaussianPoints2D(
				new EuclideanSpacePoint(new double[] { -1., 1. }), 0.2, 200);

		Collection<EuclideanSpacePoint> points2 = generateGaussianPoints2D(
				new EuclideanSpacePoint(new double[] { 1., -1. }), 0.2, 200);

		points.addAll(points1);
		points.addAll(points2);
		return points;
	}

	public static void writePoints(String fileName,
			Collection<EuclideanSpacePoint> points) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(points);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static Collection<EuclideanSpacePoint> readPoints(String fileName)
			throws IOException {
		Collection<EuclideanSpacePoint> result = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fis);
			result = (Collection<EuclideanSpacePoint>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
