package ki.edu.agh;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import junit.framework.Assert;

import ki.edu.agh.clustering.optics.OpticsClusterPoint;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;

import org.junit.Before;
import org.junit.Test;

public class OpticsTest<T> {

	private Collection<? extends Individual> createPopulation(
			EuclideanSpacePoint... points) {
		Collection<Individual> result = new LinkedList<Individual>();
		for (EuclideanSpacePoint point : points) {
			result.add(new IndividualWithRealVectorPhenotype(
					new EuclideanSpacePhenotype(point)));
		}
		return result;
	}

	@Before
	public void init() {
		createPopulation(new EuclideanSpacePoint(new double[] { 2, 2 }),
				new EuclideanSpacePoint(new double[] { -2, 2 }),
				new EuclideanSpacePoint(new double[] { 2, -2 }),
				new EuclideanSpacePoint(new double[] { -2, -2 }),
				new EuclideanSpacePoint(new double[] { 2, 1 }),
				new EuclideanSpacePoint(new double[] { 1, 2 }),
				new EuclideanSpacePoint(new double[] { -2, 1 }),
				new EuclideanSpacePoint(new double[] { 1, 1 }),
				new EuclideanSpacePoint(new double[] { -1, 1 }),
				new EuclideanSpacePoint(new double[] { 2, -1 }));
	}

	@Test
	public void testQueue() {
		PriorityQueue<OpticsClusterPoint<MetricSpacePoint>> priorityQueue = new PriorityQueue<OpticsClusterPoint<MetricSpacePoint>>(
				10, new Comparator<OpticsClusterPoint<MetricSpacePoint>>() {
					@Override
					public int compare(OpticsClusterPoint<MetricSpacePoint> o1,
							OpticsClusterPoint<MetricSpacePoint> o2) {
						if (o1.getReachabilityDistance() < o2
								.getReachabilityDistance()) {
							return -1;
						}
						if (o1.getReachabilityDistance() > o2
								.getReachabilityDistance()) {
							return 1;
						}
						return 0;
					}
				});

		double priority = 14.0;
		for (int i = 0; i < 11; i++) {
			OpticsClusterPoint<MetricSpacePoint> point = new OpticsClusterPoint<MetricSpacePoint>(
					null);
			point.setReachabilityDistance(priority -= 1);
			priorityQueue.add(point);
		}

		List<Double> priorities = new LinkedList<Double>();
		while (!priorityQueue.isEmpty()) {
			priorities.add(priorityQueue.remove().getReachabilityDistance());
		}

		Assert.assertEquals(
				Arrays.asList(3., 4., 5., 6., 7., 8., 9., 10., 11., 12., 13.),
				priorities);
	}
}
