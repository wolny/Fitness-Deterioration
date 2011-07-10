package ki.edu.agh.clustering.optics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringParameterSet;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.print.PrintUtils;

public class OpticsClustering<T extends MetricSpacePoint> extends
		AbstractOpticsClustering<T> {

	public OpticsClustering(OpticsParamteres opticsParameters) {
		super(opticsParameters);
	}

	@Override
	public Collection<Cluster<T>> cluster(
			ClusteringParameterSet clusteringParameterSet) {
		OpticsParamteres opticsParameters = (OpticsParamteres) clusteringParameterSet;
		if (opticsParameters.getEpsilon() > getEpsilon()) {
			throw new RuntimeException(
					"Epsilon parameter must be lower than generating distance.");
		}
		extractDBSCANClustering(opticsParameters.getEpsilon());

		Map<Integer, Cluster<T>> clusterMap = getClusterMap();
		Collection<Cluster<T>> clusters = clusterMap.values();
		logger.info("cluster count: " + clusters.size());
		// print clusters
		printClusters(clusters);

		return clusters;
	}

	private void printClusters(Collection<Cluster<T>> clusters) {
		String prefix = "cluster";
		int i = 0;
		for (Cluster<T> cluster : clusters) {
			List<EuclideanSpacePoint> points = new ArrayList<EuclideanSpacePoint>(
					cluster.getSize());
			for (T cPoint : cluster.getClusterPoints()) {
				EuclideanSpacePoint p = null;
				if (cPoint instanceof PointWithFitness) {
					p = ((PointWithFitness) cPoint).getPoint();
				} else if (cPoint instanceof EuclideanSpacePoint) {
					p = (EuclideanSpacePoint) cPoint;
				} else {
					logger.error("unsupported point class: "
							+ cPoint.getClass().getName());
				}
				points.add(p);
			}
			try {
				PrintUtils.writePoints(prefix + i++, points);
			} catch (IOException e) {
				logger.warn("Cannot print clusters", e);
			}
		}
	}

	private Map<Integer, Cluster<T>> getClusterMap() {
		Map<Integer, Cluster<T>> clusterMap = new HashMap<Integer, Cluster<T>>();
		for (OpticsClusterPoint<T> oPoint : getOpticsOrdering()) {
			int clusterId = oPoint.getClusterId();
			if (clusterId == OpticsClusterPoint.NOISE) {
				continue;
			}

			Cluster<T> cluster = clusterMap.get(clusterId);
			if (cluster == null) {
				clusterMap.put(clusterId, cluster = new OpticsCluster<T>());
			}
			cluster.addClusterPoint(oPoint.getData());
		}
		return clusterMap;
	}

	private void extractDBSCANClustering(double eps) {
		logger.info("Extraction DBSCAN density clusters whith paramters: epsilon = "
				+ eps + " minPts = " + getMinPoints());

		int clusterId = OpticsClusterPoint.NOISE;
		for (OpticsClusterPoint<T> oPoint : getOpticsOrdering()) {
			if (oPoint.getReachabilityDistance() > eps) {
				// undefined > epsilon
				if (oPoint.getCoreDistance() <= eps) {
					// create new cluster
					oPoint.setClusterId(++clusterId);
				} else {
					oPoint.setClusterId(OpticsClusterPoint.NOISE);
				}
			} else { // oPoint.reachabilityDistance <= eps
				oPoint.setClusterId(clusterId);
			}
		}
	}
}
