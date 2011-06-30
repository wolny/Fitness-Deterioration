package ki.edu.agh.clustering.optics;

import java.util.ArrayList;
import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.point.MetricSpacePoint;

public class OpticsCluster<T extends MetricSpacePoint> implements Cluster<T> {

	Collection<T> clusterPoints;

	public Collection<T> getClusterPoints() {
		return clusterPoints;
	}

	public void setClusterPoints(Collection<T> clusterPoints) {
		this.clusterPoints = clusterPoints;
	}

	public OpticsCluster() {
		clusterPoints = new ArrayList<T>();
	}

	@Override
	public void addClusterPoint(T clusterPoint) {
		clusterPoints.add(clusterPoint);
	}

	@Override
	public int getSize() {
		return clusterPoints.size();
	}

}
