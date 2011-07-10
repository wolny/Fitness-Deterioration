package ki.edu.agh.clustering.optics;

import ki.edu.agh.clustering.ClusteringParameterSet;

public class OpticsParamteres implements ClusteringParameterSet {
	private int minPoints;
	private double epsilon;

	public OpticsParamteres() {

	}

	public OpticsParamteres(double epsilon) {
		this.epsilon = epsilon;
	}

	public OpticsParamteres(int minPoints, double epsilon) {
		this.minPoints = minPoints;
		this.epsilon = epsilon;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
}
