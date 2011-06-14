package ki.edu.agh.clustering.optics;

import java.util.Collection;
import java.util.List;

import ki.edu.agh.point.MetricSpaceUtils;

public class BasicOptics<T> {
	private List<OpticsClusterPoint<T>> setOfObjects;
	private int minPoints;
	private double epsilon;

	public List<OpticsClusterPoint<T>> getSetOfObjects() {
		return setOfObjects;
	}

	public void setSetOfObjects(List<OpticsClusterPoint<T>> setOfObjects) {
		this.setOfObjects = setOfObjects;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	@SuppressWarnings("unchecked")
	public Collection<OpticsClusterPoint<T>> getEpsilonNeighbor(
			OpticsClusterPoint<T> point, double epsilon) {
		return (Collection<OpticsClusterPoint<T>>) MetricSpaceUtils
				.getEpsilonNeighbor(point, setOfObjects, epsilon);
	}
}
