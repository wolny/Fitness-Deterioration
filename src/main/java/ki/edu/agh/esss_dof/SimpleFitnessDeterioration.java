package ki.edu.agh.esss_dof;

import java.util.Comparator;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.SimpleCluster;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;
import ki.edu.agh.population.Population;
import ki.edu.agh.statistics.Utils;

public class SimpleFitnessDeterioration {
	private final Comparator<PointWithFitness> cmp = new Comparator<PointWithFitness>() {
		@Override
		public int compare(PointWithFitness o1, PointWithFitness o2) {
			if (o1.getFitness() > o2.getFitness()) {
				return -1;
			} else if (o1.getFitness() < o2.getFitness()) {
				return 1;
			}
			return 0;
		}
	};

	public FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Population pop) {

		Cluster<PointWithFitness> cluster = new SimpleCluster<PointWithFitness>();
		for (Individual ind : pop) {
			IndividualWithRealVectorPhenotype irvp = (IndividualWithRealVectorPhenotype) ind;
			cluster.addClusterPoint(irvp);
		}
		Functor functor = createCrunchingFunctorForCluster(cluster);
		return new GaussianFitness(currentFitness, functor);

	}

	private Functor createCrunchingFunctorForCluster(
			Cluster<PointWithFitness> cluster) {
		return Utils.createGaussianForCluster(cluster, cmp);
	}
}
