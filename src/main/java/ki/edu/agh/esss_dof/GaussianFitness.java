package ki.edu.agh.esss_dof;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;

public class GaussianFitness implements FitnessFunction {

	private FitnessFunction fitness;
	private Functor crunchingFunction;

	public GaussianFitness(FitnessFunction currentFitness,
			Functor crunchingFunction) {
		this.fitness = currentFitness;
		this.crunchingFunction = crunchingFunction;
	}

	//
	@Override
	public double computeFitness(Phenotype phenotype) {
		EuclideanSpacePoint x = ((EuclideanSpacePhenotype) phenotype)
				.getPoint();
		double fitnessValue = fitness.computeFitness(phenotype);
		return fitnessValue - crunchingFunction.getValue(x);
	}

}
