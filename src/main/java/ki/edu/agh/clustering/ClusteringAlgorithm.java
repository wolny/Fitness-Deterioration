package ki.edu.agh.clustering;

import java.util.Collection;

public interface ClusteringAlgorithm<T> {
	Collection<Cluster<T>> cluster(Collection<T> setOfPoints);

	void setClusteringParameterSet(ClusteringParameterSet parameters);
}
