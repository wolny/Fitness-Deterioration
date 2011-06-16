package ki.edu.agh.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.deterioration.PointWithFitness;

public class ClusteringUtils {

	private static class ClusterImpl implements Cluster<PointWithFitness> {
		Collection<PointWithFitness> clusterPoints;

		public ClusterImpl(int clusterSize) {
			clusterPoints = new ArrayList<PointWithFitness>(clusterSize);
		}

		@Override
		public int getSize() {
			return clusterPoints.size();
		}

		@Override
		public Collection<PointWithFitness> getClusterPoints() {
			return clusterPoints;
		}

		@Override
		public void addClusterPoint(PointWithFitness clusterPoint) {
			clusterPoints.add(clusterPoint);
		}

	}

	public static <T> Collection<Cluster<PointWithFitness>> convertClustersOfIndividuals(
			Collection<Cluster<T>> clusters) {
		List<Cluster<PointWithFitness>> result = new ArrayList<Cluster<PointWithFitness>>();
		for (Cluster<T> cluster : clusters) {
			Cluster<PointWithFitness> convertedCluster = new ClusterImpl(
					cluster.getSize());
			for (T clusterPoint : cluster.getClusterPoints()) {
				convertedCluster
						.addClusterPoint((PointWithFitness) clusterPoint);
			}
			result.add(convertedCluster);
		}
		return result;
	}
}
