package ki.edu.agh.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.optics.OpticsClustering;
import ki.edu.agh.clustering.optics.OpticsParamteres;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.print.PrintUtils;

public class PointGenerator {
	// gererate metricSpacePoints using Gaussian Distribution
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

	private static boolean isInCircle(EuclideanSpacePoint center,
			double radius, double x, double y) {
		double a = center.getCoordinate(0);
		double b = center.getCoordinate(1);
		double value = (x - a) * (x - a) + (y - b) * (y - b);
		double rSqr = radius * radius;

		return value <= rSqr;
	}

	public static void main(String[] args) throws IOException {
		Collection<EuclideanSpacePoint> points = generateUniformCircle2D(
				new EuclideanSpacePoint(new double[] { 0., 0. }), 3., 600);

		Collection<EuclideanSpacePoint> points1 = generateGaussianPoints2D(
				new EuclideanSpacePoint(new double[] { -1., 1. }), 0.2, 200);

		Collection<EuclideanSpacePoint> points2 = generateGaussianPoints2D(
				new EuclideanSpacePoint(new double[] { 1., -1. }), 0.2, 200);

		points.addAll(points1);
		points.addAll(points2);
		PrintUtils.writePoints("circle", points);

		double epsilon = 1.2;
		int minPoints = 10;
		OpticsClustering<EuclideanSpacePoint> optics = new OpticsClustering<EuclideanSpacePoint>(
				new OpticsParamteres(minPoints, epsilon));
		optics.setDataSet(points);
		Collection<Cluster<EuclideanSpacePoint>> clusters = optics
				.cluster(new OpticsParamteres(minPoints, 0.1));

//		String prefix = "cluster";
//		int i = 0;
//		for (Cluster<EuclideanSpacePoint> cluster : clusters) {
//			PrintUtils.writePoints(prefix + i++, cluster.getClusterPoints());
//		}

	}
}
