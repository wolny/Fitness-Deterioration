package ki.edu.agh.deterioration;

import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;

public interface FitnessDeterioration {
	/**
	 * alters fitness function in a way that will prevent exploration of basins
	 * of attraction which were found for currentFitness by the optimization
	 * algorithm
	 * 
	 * @param currentFitness
	 * @param clusters
	 * @return
	 */
	FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Collection<Cluster<? extends PointWithFitness>> clusters);
}
