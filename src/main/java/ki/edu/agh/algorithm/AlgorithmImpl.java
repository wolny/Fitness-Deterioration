package ki.edu.agh.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.Point;

public class AlgorithmImpl implements Algorithm {
	public void choose() {
		double epsilon = 0;
		double treshold = 0;
		double delta = 0;
		List<Point> clusterOrderedObjs = null;
		int minPts = 0;
		double minDistance = 0.0;

		for (double eps = epsilon; eps > treshold; eps -= delta) {
			Collection<Cluster> clusters = extractDbScanClustering(
					clusterOrderedObjs, epsilon, minPts);
			Set<DeteriorationFunction> deteriorationFunctions = new HashSet<DeteriorationFunction>();
			for (Cluster cluster : clusters) {
				DeteriorationFunction df = getDeterioratingModule().createDF(
						cluster);
				deteriorationFunctions.add(df);
			}
			FitnessFunction augmentedFitness = getDeterioratingModule()
					.createAugmentedFitness(getFitness(),
							deteriorationFunctions);
			double distance = getDistance(augmentedFitness, getFitness());

			if (distance < minDistance) {
				setNewFitness(augmentedFitness);
			}
		}
	}

	private void setNewFitness(FitnessFunction augmentedFitness) {
		// TODO Auto-generated method stub

	}

	private double getDistance(FitnessFunction augmentedFitness,
			FitnessFunction fitness) {
		// TODO Auto-generated method stub
		return 0;
	}

	private FitnessFunction getFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	private DeteriorationModule getDeterioratingModule() {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<Cluster> extractDbScanClustering(
			List<Point> clusterOrderedObjs, double epsilon, int minPts) {
		// TODO Auto-generated method stub
		return null;
	}
}
