package ki.edu.agh.deterioration;

import java.util.ArrayList;
import java.util.Collection;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.statistics.Utils;

public class SimpleGaussianFitnessDeterioration implements FitnessDeterioration {

	@Override
	public FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Collection<Cluster<PointWithFitness>> clusters) {

		Collection<DeteriorationFunction> deteriorationFunctions = new ArrayList<DeteriorationFunction>(
				clusters.size());

		for (Cluster<PointWithFitness> cluster : clusters) {
			deteriorationFunctions.add(Utils.createGaussianForCluster(cluster));
		}

		return new DeterioratedFitness(currentFitness, deteriorationFunctions);
	}

}
