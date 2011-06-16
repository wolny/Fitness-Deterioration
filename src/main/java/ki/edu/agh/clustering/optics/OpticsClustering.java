package ki.edu.agh.clustering.optics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.clustering.ClusteringParameterSet;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.point.MetricSpaceUtils;

public class OpticsClustering<T extends MetricSpacePoint> implements
		ClusteringAlgorithm<T> {
	private double epsilon;
	private int minPoints;
	private boolean configured;

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public boolean isConfigured() {
		return configured;
	}

	// we have to preserve order of points so list will suffice
	private List<OpticsClusterPoint<T>> setOfObjects;

	@Override
	public Collection<Cluster<T>> cluster(Collection<T> objectsToBeClustered) {
		if (!isConfigured()) {
			throw new IllegalStateException(
					"OPTICS algorithm has not been configured");
		}
		// clear optics algorithm state
		createSetOfObjects(objectsToBeClustered);
		return null;
	}

	private void createSetOfObjects(Collection<T> objectsToBeClustered) {
		setOfObjects = new ArrayList<OpticsClusterPoint<T>>(
				objectsToBeClustered.size());
		for (T object : objectsToBeClustered) {
			setOfObjects.add(new OpticsClusterPoint<T>(object));
		}
	}

	public double getEpsilon() {
		return epsilon;
	}

	@SuppressWarnings("unchecked")
	public Collection<OpticsClusterPoint<T>> getEpsilonNeighbor(
			OpticsClusterPoint<T> point, double epsilon) {
		return (Collection<OpticsClusterPoint<T>>) MetricSpaceUtils
				.getEpsilonNeighbor(point, setOfObjects, epsilon);
	}

	public int getMinPoints() {
		return minPoints;
	}

	public List<OpticsClusterPoint<T>> getSetOfObjects() {
		return setOfObjects;
	}

	@Override
	public void setClusteringParameterSet(ClusteringParameterSet parameters) {
		OpticsParamteres opticsParamters = (OpticsParamteres) parameters;
		setMinPoints(opticsParamters.getMinPoints());
		setEpsilon(opticsParamters.getEpsilon());
		setConfigured(true);
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
}
