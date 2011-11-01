package ki.edu.agh.deterioration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;
import ki.edu.agh.statistics.Utils;

public class WeightedGaussianFitnessDeterioration extends
		AbstractFitnessDeterioration {

	private class ClusterCrunchingFunctor implements Functor {
		private Functor crunchingFunctor;
		private EuclideanSpacePoint clusterCenter;

		public ClusterCrunchingFunctor(Functor crunchingFunctor,
				EuclideanSpacePoint clusterCenter) {
			this.crunchingFunctor = crunchingFunctor;
			this.clusterCenter = clusterCenter;
		}

		public EuclideanSpacePoint getClusterCenter() {
			return clusterCenter;
		}

		@Override
		public double getValue(EuclideanSpacePoint point) {
			return crunchingFunctor.getValue(point);
		}

	}

	private class WeightedDeterioratedFitness implements FitnessFunction {

		private FitnessFunction fitness;
		private Collection<ClusterCrunchingFunctor> crunchingFunctions;

		public WeightedDeterioratedFitness(FitnessFunction currentFitness,
				Collection<ClusterCrunchingFunctor> crunchingFunctions) {
			this.fitness = currentFitness;
			this.crunchingFunctions = crunchingFunctions;
		}

		@Override
		public double computeFitness(Phenotype phenotype) {
			EuclideanSpacePoint x = ((EuclideanSpacePhenotype) phenotype)
					.getPoint();
			List<Double> inverseDistances = getClusterCenterInverseDistances(x);

			double xi = computeXi(inverseDistances);
			double sum = 0.0;
			int i = 0;
			for (Functor crunchingFunctor : crunchingFunctions) {
				sum += xi * inverseDistances.get(i++)
						* crunchingFunctor.getValue(x);
			}

			int sign = isMinimization() ? 1 : -1;
			double fitnessValue = fitness.computeFitness(phenotype);
			return fitnessValue + sign * sum;
		}

		private double computeXi(Collection<Double> inverseDistances) {
			double sum = 0.0;
			for (double dist : inverseDistances) {
				sum += dist;
			}
			return 1.0 / sum;
		}

		private List<Double> getClusterCenterInverseDistances(
				EuclideanSpacePoint x) {
			List<Double> result = new ArrayList<Double>(
					crunchingFunctions.size());
			for (ClusterCrunchingFunctor crunchingFunction : crunchingFunctions) {
				double distance = x.getDistance(crunchingFunction
						.getClusterCenter());
				result.add(1.0 / distance);
			}
			return result;
		}

	}

	@Override
	public FitnessFunction createDeterioratedFitness(
			FitnessFunction currentFitness,
			Collection<Functor> crunchingFunctions) {
		Collection<ClusterCrunchingFunctor> clusterCrunchingFunctions = new ArrayList<ClusterCrunchingFunctor>(
				crunchingFunctions.size());
		for (Functor crunchingFunction : crunchingFunctions) {
			clusterCrunchingFunctions
					.add((ClusterCrunchingFunctor) crunchingFunction);
		}
		return new WeightedDeterioratedFitness(currentFitness,
				clusterCrunchingFunctions);
	}

	@Override
	public Functor createCrunchingFunctorForCluster(
			Cluster<? extends PointWithFitness> cluster) {
		EuclideanSpacePoint clusterCenter = getClusterCenter(cluster);
		@SuppressWarnings("unchecked")
		Functor crunchingFunctor = Utils.createGaussianForCluster(
				(Cluster<PointWithFitness>) cluster, getComparator());
		return new ClusterCrunchingFunctor(crunchingFunctor, clusterCenter);
	}

	private EuclideanSpacePoint getClusterCenter(
			Cluster<? extends PointWithFitness> cluster) {

		List<EuclideanSpacePoint> points = new ArrayList<EuclideanSpacePoint>(
				cluster.getSize());
		for (PointWithFitness p : cluster.getClusterPoints()) {
			points.add(p.getPoint());
		}

		return Utils.meanPoint(points, points.get(0).getDimension());
	}
}
