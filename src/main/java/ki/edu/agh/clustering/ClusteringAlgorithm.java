package ki.edu.agh.clustering;

import java.util.Collection;

public interface ClusteringAlgorithm<T> {
	Collection<Cluster<T>> cluster(ClusteringParameterSet parameters);
	void setDataSet(Collection<T> dataSet);
}
