package ki.edu.agh.deterioration;

import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;

public class AdaptiveGaussianDeterioratedFitness extends
		AbstractFitnessDeterioration {

	private List<Cluster<? extends PointWithFitness>> clusters;

	@Override
	public FitnessFunction createDeterioratedFitness(
			FitnessFunction currentFitness,
			Collection<Functor> crunchingFunctions) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	// TODO: test wiht mockito and cgLib
	@Override
	public Functor createCrunchingFunctorForCluster(
			Cluster<? extends PointWithFitness> cluster) {
		// TODO Auto-generated method stub
		// we need information about clusters for fitness computation
		clusters.add(cluster);
		throw new RuntimeException("not implemented");
	}

}
