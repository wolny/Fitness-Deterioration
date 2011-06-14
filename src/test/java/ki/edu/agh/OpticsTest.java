package ki.edu.agh;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import ki.edu.agh.clustering.optics.BasicOptics;
import ki.edu.agh.clustering.optics.OpticsClusterPoint;
import ki.edu.agh.point.EuclideanSpacePoint;

import org.junit.Before;
import org.junit.Test;

public class OpticsTest {
	private List<OpticsClusterPoint<EuclideanSpacePoint>> pop;
	private OpticsClusterPoint<EuclideanSpacePoint> cPoint;

	@Before
	public void init() {
		pop = new LinkedList<OpticsClusterPoint<EuclideanSpacePoint>>();
		pop.add(new OpticsClusterPoint<EuclideanSpacePoint>(
				new EuclideanSpacePoint(new double[] { 2, 2 })));
		pop.add(new OpticsClusterPoint<EuclideanSpacePoint>(
				new EuclideanSpacePoint(new double[] { 1, 1 })));
		pop.add(new OpticsClusterPoint<EuclideanSpacePoint>(
				new EuclideanSpacePoint(new double[] { -1, 1 })));
		pop.add(new OpticsClusterPoint<EuclideanSpacePoint>(
				new EuclideanSpacePoint(new double[] { 2, 1 })));
		cPoint = new OpticsClusterPoint<EuclideanSpacePoint>(
				new EuclideanSpacePoint(new double[] { 0, 0 }));
	}

	@Test
	public void testNearestNeighbor() {
		BasicOptics<EuclideanSpacePoint> optics = new BasicOptics<EuclideanSpacePoint>();
		optics.setSetOfObjects(pop);
		Collection<OpticsClusterPoint<EuclideanSpacePoint>> result = optics
				.getEpsilonNeighbor(cPoint, 2.0);
		Assert.assertTrue(result.size() == 2);
		Assert.assertTrue(result.contains(pop.get(1)));
		Assert.assertTrue(result.contains(pop.get(2)));
	}
}
