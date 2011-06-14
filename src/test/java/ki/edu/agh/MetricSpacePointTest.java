package ki.edu.agh;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.point.MetricSpaceUtils;

import org.junit.Before;
import org.junit.Test;

public class MetricSpacePointTest {
	private List<EuclideanSpacePoint> points;
	private EuclideanSpacePoint point;

	@Before
	public void init() {
		points = new LinkedList<EuclideanSpacePoint>();
		points.add(new EuclideanSpacePoint(new double[] { 2, 1 }));
		points.add(new EuclideanSpacePoint(new double[] { 2, 2 }));
		points.add(new EuclideanSpacePoint(new double[] { -1, 1 }));
		points.add(new EuclideanSpacePoint(new double[] { 1, 1 }));
		points.add(new EuclideanSpacePoint(new double[] { -1, -1 }));
		point = new EuclideanSpacePoint(new double[] { 0, 0 });
	}

	@Test
	public void testNearestNeighbor() {
		Collection<? extends MetricSpacePoint> neigh = MetricSpaceUtils
				.getEpsilonNeighbor(point, points, 2.);
		Assert.assertTrue(neigh.size() == 3);
		Assert.assertTrue(neigh.contains(points.get(2)));
		Assert.assertTrue(neigh.contains(points.get(3)));
		Assert.assertTrue(neigh.contains(points.get(4)));
	}
}
