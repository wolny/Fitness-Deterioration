package ki.edu.agh.problem;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;

public class MultimodalRealSpaceProblem implements ProblemDomain {

	private final Domain domain;
	private final FitnessFunction fitnessFunction;

	public MultimodalRealSpaceProblem(Domain domain,
			FitnessFunction fitnessFunction) {
		this.domain = domain;
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

}
