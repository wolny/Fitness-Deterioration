package ki.edu.agh.evolutionary.algorithm;

import ki.edu.agh.evolutionary.reproduction.ReproductionAlgorithm;
import ki.edu.agh.evolutionary.selection.SelectionAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;

/**
 * TODO: implement
 * 
 * @author slo
 * 
 */
public abstract class AbstractEvolutionaryAlgorithm implements
		EvolutionaryAlgorithm {
	private int generationNumber;
	private ProblemDomain problem;
	private SelectionAlgorithm selectionAlgorithm;
	private ReproductionAlgorithm reproductionAlgorithm;

	public int getGenerationNumber() {
		return generationNumber;
	}

	public void increaseGenerationNumber() {
		generationNumber++;
	}

	public SelectionAlgorithm getSelectionAlgorithm() {
		return selectionAlgorithm;
	}

	public void setSelectionAlgorithm(SelectionAlgorithm selectionAlgorithm) {
		this.selectionAlgorithm = selectionAlgorithm;
	}

	public ReproductionAlgorithm getReproductionAlgorithm() {
		return reproductionAlgorithm;
	}

	public void setReproductionAlgorithm(
			ReproductionAlgorithm reproductionAlgorithm) {
		this.reproductionAlgorithm = reproductionAlgorithm;
	}

	public void assignFitness(Population population) {
		for (Individual individual : population) {
			individual.setFitness(getFitnessFunction().computeFitness(individual
					.getPhenotype()));
		}
	}
	
	@Override
	public ProblemDomain getProblemDomain() {
		return problem;
	}
	
	@Override
	public void setProblemDomain(ProblemDomain problem) {
		this.problem = problem;
	}

	private FitnessFunction getFitnessFunction() {
		return getProblemDomain().getFitnessFunction();
	}
}
