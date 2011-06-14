package ki.edu.agh;

import ki.edu.agh.point.EuclideanSpacePoint;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {
	private final EuclideanSpacePoint p1 = new EuclideanSpacePoint(new double[] { 1., 1. });
	private final EuclideanSpacePoint p2 = new EuclideanSpacePoint(new double[] { 4., 5. });

	@Test
	public void testDistance() {
		double dist = p1.getDistance(p2);
		Assert.assertEquals(5, dist, .000000001);
	}

	@Test
	public void testAdd() {
		EuclideanSpacePoint result1 = p1.add(p2);
		EuclideanSpacePoint result2 = p2.add(p1);

		double[] expecteds = new double[] { 5, 6 };
		Assert.assertArrayEquals(expecteds, result1.getCoordinates(),
				.000000001);

		Assert.assertArrayEquals(expecteds, result2.getCoordinates(),
				.000000001);
	}

	@Test
	public void testSubstract() {
		EuclideanSpacePoint result1 = p1.substract(p2);
		EuclideanSpacePoint result2 = p2.substract(p1);

		Assert.assertArrayEquals(new double[] { -3, -4 },
				result1.getCoordinates(), .000000001);

		Assert.assertArrayEquals(new double[] { 3, 4 },
				result2.getCoordinates(), .000000001);
	}

	@Test
	public void testDivide() {
		EuclideanSpacePoint result = p1.divide(2);
		Assert.assertArrayEquals(new double[] { .5, .5 },
				result.getCoordinates(), .000000001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void coordinateIndexOutOfBound() {
		p1.getCoordinate(2);
	}

	@Test
	public void testGetCoordinate() {
		double coord = p2.getCoordinate(1);
		Assert.assertEquals(5., coord, .000000001);
	}

}
