package ki.edu.agh.clustering;

import java.util.Collection;

public interface Cluster<T> {
	Collection<T> getClusterPoints();

	void addClusterPoint(T clusterPoint);

	int getSize();
}
