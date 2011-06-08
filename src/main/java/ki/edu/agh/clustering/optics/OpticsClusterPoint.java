package ki.edu.agh.clustering.optics;

import ki.edu.agh.clustering.ClusterPoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Point;

public class OpticsClusterPoint implements ClusterPoint {
	public static final double UNDEFINED = Double.MAX_VALUE;
	public static final int NOISE = Integer.MAX_VALUE;

	private boolean processed;
	private int clusterId;
	private Individual individual;
	private double coreDistance;
	private double reachabilityDistance;

	public OpticsClusterPoint(Individual individual) {
		this.individual = individual;
		this.processed = false;
		this.coreDistance = UNDEFINED;
		this.reachabilityDistance = UNDEFINED;
	}

	@Override
	public double getDistance(ClusterPoint clusterPoint) {
		OpticsClusterPoint cPoint = (OpticsClusterPoint) clusterPoint;
		Point spatialPoint = cPoint.getIndividual().getPhenotype().getPoint();
		return individual.getPhenotype().getPoint()
				.euclideanDistance(spatialPoint);
	}

	@Override
	public int getClusterId() {
		if (!processed) {
			throw new IllegalStateException(
					"This point has not beed assigned to any cluster");
		}
		return clusterId;
	}

	public double getCoreDistance() {
		return coreDistance;
	}

	public Individual getIndividual() {
		return individual;
	}

	public double getReachabilityDistance() {
		return reachabilityDistance;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public void setCoreDistance(double coreDistance) {
		this.coreDistance = coreDistance;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public void setReachabilityDistance(double reachabilityDistance) {
		this.reachabilityDistance = reachabilityDistance;
	}
}
