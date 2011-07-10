package ki.edu.agh.functors;

import ki.edu.agh.point.EuclideanSpacePoint;
import Jama.Matrix;

/**
 * 
 * @author slo
 * 
 */
public class MultivariateGaussianFunctor implements Functor {
	private Matrix sigmaInverse;
	private Matrix meanVector;
	private double heigh;

	public MultivariateGaussianFunctor(Matrix covarianceMatrix,
			Matrix meanVector, double heigh) {
		this.sigmaInverse = covarianceMatrix.inverse();
		this.meanVector = meanVector;
		this.heigh = heigh;
	}

	@Override
	public double getValue(EuclideanSpacePoint point) {
		Matrix X = new Matrix(point.getCoordinates(), point.getDimension());
		Matrix diff = X.minus(meanVector);

		// exponent = -0.5 * (X - meanVector)' * sigmaInverse * (X - meanVector)
		double exponent = -0.5
				* diff.transpose().times(sigmaInverse).times(diff).get(0, 0);

		// f(p) = a * exp(exponent)
		return heigh * Math.exp(exponent);
	}

}
