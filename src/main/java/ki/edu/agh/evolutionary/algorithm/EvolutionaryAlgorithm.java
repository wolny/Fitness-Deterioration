package ki.edu.agh.evolutionary.algorithm;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;

public interface EvolutionaryAlgorithm {

	EAResult execute();

	Population getPopulation();

	void setPopulationSize(int size);

	int getPopulationSize();

	Individual[] extractBestIndividuals();

	void setProblemDomain(ProblemDomain problem);

	ProblemDomain getProblemDomain();

	boolean checkTerminationCriterion();

	Population replaceNotFeasible(Population pop);
	
	void resetStopCriterion();
}
