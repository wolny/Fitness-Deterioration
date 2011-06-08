package ki.edu.agh.fintess;

import ki.edu.agh.functors.Functor;
import ki.edu.agh.population.Phenotype;

public class StandardFitnessFunction implements FitnessFunction {
	private Functor functor;

	public StandardFitnessFunction(Functor functor) {
		this.functor = functor;
	}

	public Functor getFunctor() {
		return functor;
	}

	public void setFunctor(Functor functor) {
		this.functor = functor;
	}

	@Override
	public double computeFitness(Phenotype phenotype) {
		return functor.getValue(phenotype.getPoint());
	}

}
