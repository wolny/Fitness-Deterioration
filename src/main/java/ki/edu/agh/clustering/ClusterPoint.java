package ki.edu.agh.clustering;

public interface ClusterPoint {
	int getClusterId();

	double getDistance(ClusterPoint clusterPoint);
}
