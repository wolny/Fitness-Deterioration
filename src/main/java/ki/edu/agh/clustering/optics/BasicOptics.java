package ki.edu.agh.clustering.optics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public class BasicOptics {

	private Set<OpticsClusterPoint> clusterPointSet;

	public BasicOptics(Set<OpticsClusterPoint> clusterPointSet) {
		this.clusterPointSet = clusterPointSet;
	}

	public BasicOptics(Population population) {
		Set<OpticsClusterPoint> cPointSet = new HashSet<OpticsClusterPoint>();
		for (Individual individual : population) {
			cPointSet.add(new OpticsClusterPoint(individual));
		}
		this.clusterPointSet = cPointSet;
	}

	public List<OpticsClusterPoint> getEpsilonNeighbor(
			OpticsClusterPoint clusterPoint, double epsilon) {

		ArrayList<OpticsClusterPoint> result = new ArrayList<OpticsClusterPoint>();

		for (OpticsClusterPoint cPoint : clusterPointSet) {
			if (clusterPoint.getDistance(cPoint) <= epsilon) {
				result.add(cPoint);
			}
		}

		return result;
	}
}
