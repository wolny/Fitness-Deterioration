package ki.edu.agh.deterioration;

import java.util.Collection;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;

public class DeterioratedFitness implements FitnessFunction {

	private FitnessFunction fitness;
	private Collection<DeteriorationFunction> deteriorationFunctions;

	public DeterioratedFitness(FitnessFunction currentFitness,
			Collection<DeteriorationFunction> deteriorationFunctions) {
		this.fitness = currentFitness;
		this.deteriorationFunctions = deteriorationFunctions;
	}

	//
	@Override
	public double computeFitness(Phenotype phenotype) {
		// TODO: +/- depends on optimalization (min or max)
		double sum = 0.0;
		for (DeteriorationFunction detFun : deteriorationFunctions) {
			sum += detFun.getValue(((EuclideanSpacePhenotype) phenotype)
					.getPoint());
		}
		return fitness.computeFitness(phenotype) + sum;
	}

}
