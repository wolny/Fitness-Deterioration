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
// TODO: write proportionate selection (during reproduction check isFeasible)
// and
// reproduction base ond normal
// distribution
public abstract class AbstractEvolutionaryAlgorithm implements
		EvolutionaryAlgorithm {
	private int generationNumber;

	// simple stop criterion
	private int maxGenerationNumber;
	private int populationSize;
	private ProblemDomain problem;
	private SelectionAlgorithm selectionAlgorithm;
	private ReproductionAlgorithm reproductionAlgorithm;

	public int getMaxGenerationNumber() {
		return maxGenerationNumber;
	}

	public void setMaxGenerationNumber(int maxGenerationNumber) {
		this.maxGenerationNumber = maxGenerationNumber;
	}

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
			individual.setFitness(getFitnessFunction().computeFitness(
					individual.getPhenotype()));
		}
	}

	@Override
	public ProblemDomain getProblemDomain() {
		return problem;
	}

	@Override
	public int getPopulationSize() {
		return populationSize;
	}

	@Override
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	@Override
	public void setProblemDomain(ProblemDomain problem) {
		this.problem = problem;
	}

	private FitnessFunction getFitnessFunction() {
		return getProblemDomain().getFitnessFunction();
	}
}
