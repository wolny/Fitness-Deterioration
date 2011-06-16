package ki.edu.agh.deterioration;

import java.util.ArrayList;
import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;

/**
 * implementation of the basic fitness deterioration schema with one crunching
 * functor per cluster of points
 * 
 * @author slo
 * 
 */
public abstract class AbstractFitnessDeterioration implements
		FitnessDeterioration {

	/**
	 * creates deteriorated fitness by using current fitness and information
	 * about basins of attraction provided by collection of clustered points
	 * with its fitness values
	 */
	@Override
	public FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Collection<Cluster<PointWithFitness>> clusters) {

		if (clusters == null || clusters.size() == 0) {
			return currentFitness;
		}

		Collection<Functor> crunchingFunctions = new ArrayList<Functor>(
				clusters.size());

		for (Cluster<PointWithFitness> cluster : clusters) {
			crunchingFunctions.add(createCrunchingFunctorForCluster(cluster));
		}

		return createDeterioratedFitness(currentFitness, crunchingFunctions);
	}

	/**
	 * creates deteriorated fitness function by combining current fitness and
	 * crunching functions
	 * 
	 * @param currentFitness
	 * @param crunchingFunctions
	 * @return
	 */
	public abstract FitnessFunction createDeterioratedFitness(
			FitnessFunction currentFitness,
			Collection<Functor> crunchingFunctions);

	/**
	 * creates crunching functor for cluster of multidimensional vectors
	 * 
	 * @param cluster
	 * @return
	 */
	public abstract Functor createCrunchingFunctorForCluster(
			Cluster<PointWithFitness> cluster);

}
