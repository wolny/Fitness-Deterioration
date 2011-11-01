package ki.edu.agh.deterioration;

import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;
import ki.edu.agh.statistics.Utils;

/**
 * creates deteriorated fitness function which is a sum of provided fitness
 * function and a nubmer of multidimensional Gaussian functions each of which is
 * an approximation of a cluster
 * 
 * @author slo
 * 
 */
public class SimpleGaussianFitnessDeterioration extends
		AbstractFitnessDeterioration {

	private class GaussianDeterioratedFitness implements FitnessFunction {

		private FitnessFunction fitness;
		private Collection<Functor> crunchingFunctions;

		public GaussianDeterioratedFitness(FitnessFunction currentFitness,
				Collection<Functor> crunchingFunctions) {
			this.fitness = currentFitness;
			this.crunchingFunctions = crunchingFunctions;
		}

		//
		@Override
		public double computeFitness(Phenotype phenotype) {
			EuclideanSpacePoint x = ((EuclideanSpacePhenotype) phenotype)
					.getPoint();
			double fitnessValue = fitness.computeFitness(phenotype);
			double sum = 0.0;
			for (Functor crunchingFunctor : crunchingFunctions) {
				sum += crunchingFunctor.getValue(x);
			}
			int sign = isMinimization() ? 1 : -1;
			return fitnessValue + sign * sum;
		}

	}

	@Override
	public FitnessFunction createDeterioratedFitness(
			FitnessFunction currentFitness,
			Collection<Functor> crunchingFunctions) {
		return new GaussianDeterioratedFitness(currentFitness,
				crunchingFunctions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Functor createCrunchingFunctorForCluster(
			Cluster<? extends PointWithFitness> cluster) {
		return Utils.createGaussianForCluster(
				(Cluster<PointWithFitness>) cluster, getComparator());
	}
}
