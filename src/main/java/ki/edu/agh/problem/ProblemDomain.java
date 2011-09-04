package ki.edu.agh.problem;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;

public interface ProblemDomain {
	Functor getFunctor();
	Domain getDomain();
	FitnessFunction getFitnessFunction();
	void setFitnessFunction(FitnessFunction fitnessFunction);
	boolean isMinimization();
	void setMinimization(boolean minimization);
}
