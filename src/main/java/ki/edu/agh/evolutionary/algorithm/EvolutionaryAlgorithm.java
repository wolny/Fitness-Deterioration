package ki.edu.agh.evolutionary.algorithm;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;

public interface EvolutionaryAlgorithm {
	/**
	 * 
	 * @return
	 */
	EAResult execute();

	/**
	 * 
	 * @return
	 */
	Population getPopulation();
	
	void setProblemDomain(ProblemDomain problem);
	
	void setPopulationSize(int size);
	
	int getPopulationSize();

	/**
	 * 
	 * @return
	 */
	Individual[] extractBestIndividuals();

	/**
	 * we have to be able to substitute current fitness for deteriorated fitness
	 * 
	 * @param fitnessFunction
	 */
	void setFitnessFunction(FitnessFunction fitnessFunction);

	/**
	 * 
	 * @return
	 */
	FitnessFunction getFitnessFunction();
}
