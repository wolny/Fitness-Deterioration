package ki.edu.agh.population;

import java.util.Comparator;

public class MaximizationIndividualComparator implements Comparator<Individual> {

	/**
	 * from the two individuals, we treat the one with lower value to be fitter
	 */
	@Override
	public int compare(Individual o1, Individual o2) {
		if (o1.getFitness() > o2.getFitness()) {
			return -1;
		} else if (o1.getFitness() < o2.getFitness()) {
			return 1;
		}
		return 0;
	}

}
