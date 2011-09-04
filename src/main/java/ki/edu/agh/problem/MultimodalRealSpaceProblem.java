package ki.edu.agh.problem;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;

public class MultimodalRealSpaceProblem implements ProblemDomain {

	private Domain domain;
	private FitnessFunction fitnessFunction;
	private boolean minimization;

	public MultimodalRealSpaceProblem() {
	}

	public MultimodalRealSpaceProblem(Domain domain,
			FitnessFunction fitnessFunction) {
		this.domain = domain;
		this.fitnessFunction = fitnessFunction;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	@Override
	public Functor getFunctor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Domain getDomain() {
		return domain;
	}

	@Override
	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public boolean isMinimization() {
		return minimization;
	}

	@Override
	public void setMinimization(boolean minimization) {
		this.minimization = minimization;
	}

}
