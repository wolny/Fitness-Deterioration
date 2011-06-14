package ki.edu.agh.clustering;

import java.util.Collection;

public interface Cluster<T> {
	Collection<? extends ClusterPoint<T>> getClusterPoints();
}
