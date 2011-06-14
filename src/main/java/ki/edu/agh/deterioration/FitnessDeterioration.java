package ki.edu.agh.deterioration;

import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;

public interface FitnessDeterioration {
	FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Collection<Cluster<PointWithFitness>> clusters);
}
