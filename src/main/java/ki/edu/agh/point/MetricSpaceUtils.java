package ki.edu.agh.point;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MetricSpaceUtils {

	public static Collection<? extends MetricSpacePoint> getEpsilonNeighbor(
			MetricSpacePoint point,
			Collection<? extends MetricSpacePoint> points, double epsilon) {
		List<MetricSpacePoint> result = new LinkedList<MetricSpacePoint>();

		for (MetricSpacePoint p : points) {
			if (point.getDistance(p) <= epsilon) {
				result.add(p);
			}
		}

		return result;
	}
}
