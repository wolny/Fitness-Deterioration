package ki.edu.agh.clustering.optics;

import ki.edu.agh.clustering.ClusterPoint;
import ki.edu.agh.point.MetricSpacePoint;

public class OpticsClusterPoint<T extends MetricSpacePoint> implements
		ClusterPoint<T>, MetricSpacePoint {
	public static final double UNDEFINED = Double.MAX_VALUE;
	public static final int NOISE = Integer.MAX_VALUE;

	private boolean processed;
	private int clusterId;
	private double coreDistance;
	private double reachabilityDistance;
	private T data;

	public OpticsClusterPoint(T data) {
		this.data = data;
		this.processed = false;
		this.coreDistance = UNDEFINED;
		this.reachabilityDistance = UNDEFINED;
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

	@Override
	public double getDistance(MetricSpacePoint point) {
		return data.getDistance(point);
	}

	@Override
	public T getData() {
		return data;
	}
}
