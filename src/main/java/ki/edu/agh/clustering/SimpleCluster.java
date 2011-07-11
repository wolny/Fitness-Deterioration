package ki.edu.agh.clustering;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleCluster<T> implements Cluster<T> {
	Collection<T> clusterPoints;

	public SimpleCluster() {
		clusterPoints = new ArrayList<T>();
	}

	public Collection<T> getClusterPoints() {
		return clusterPoints;
	}

	public void setClusterPoints(Collection<T> clusterPoints) {
		this.clusterPoints = clusterPoints;
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
