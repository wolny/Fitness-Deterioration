package ki.edu.agh.problem;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.Phenotype;

public interface ProblemDomain {
	Functor getFunctor();
	Domain getDomain();
	FitnessFunction getFitnessFunction();
	void setFitnessFunction(FitnessFunction fitnessFunction);
	boolean isMinimization();
	void setMinimization(boolean minimization);
	boolean isFeasible(Phenotype phenotype);
	EuclideanSpacePoint[] getRandomPoints(int popSize);
}
