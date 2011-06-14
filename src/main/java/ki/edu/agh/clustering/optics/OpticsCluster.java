package ki.edu.agh.clustering.optics;

import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusterPoint;

public class OpticsCluster<T> implements Cluster<T> {

	Collection<? extends ClusterPoint<T>> clusterPoints;

	public OpticsCluster(Collection<? extends ClusterPoint<T>> clusterPoints) {
		this.clusterPoints = clusterPoints;
	}

	@Override
	public Collection<? extends ClusterPoint<T>> getClusterPoints() {
		return clusterPoints;
	}

}
