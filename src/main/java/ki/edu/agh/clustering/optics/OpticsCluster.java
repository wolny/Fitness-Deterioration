package ki.edu.agh.clustering.optics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.point.MetricSpacePoint;

public class OpticsCluster<T extends MetricSpacePoint> implements Cluster<T> {

	Collection<OpticsClusterPoint<T>> clusterPoints;

	public OpticsCluster() {
		clusterPoints = new ArrayList<OpticsClusterPoint<T>>();
	}

	public OpticsCluster(Collection<OpticsClusterPoint<T>> clusterPoints) {
		this.clusterPoints = clusterPoints;
	}

	@Override
	public Collection<T> getClusterPoints() {
		List<T> result = new ArrayList<T>(clusterPoints.size());
		for (OpticsClusterPoint<T> clusterPoint : clusterPoints) {
			result.add(clusterPoint.getData());
		}
		return result;
	}

	@Override
	public void addClusterPoint(T clusterPoint) {
		clusterPoints.add(new OpticsClusterPoint<T>(clusterPoint));
	}

	@Override
	public int getSize() {
		return clusterPoints.size();
	}

}
