package ki.edu.agh.evolutionary.algorithm;

import java.util.Comparator;

import ki.edu.agh.evolutionary.reproduction.ReproductionAlgorithm;
import ki.edu.agh.evolutionary.selection.SelectionAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.MaximizationIndividualComparator;
import ki.edu.agh.population.MinimizationIndividualComparator;
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
	private FitnessFunction fitnessFunction;
	private SelectionAlgorithm selectionAlgorithm;
	private ReproductionAlgorithm reproductionAlgorithm;

	private Population pop;
	private Population mate;

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

	@Override
	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
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
			individual.setFitness(fitnessFunction.computeFitness(individual
					.getPhenotype()));
		}
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

	@Override
	public EAResult execute() {
		Comparator<Individual> comparator = problem.isMinimization() ? new MinimizationIndividualComparator()
				: new MaximizationIndividualComparator();
		generateInitialPopulation(problem, populationSize);
		while (getGenerationNumber() < getMaxGenerationNumber()) {
			assignFitness(pop);
			Individual[] individuals = getSelectionAlgorithm().select(pop,
					getPopulationSize());
			mate = new FixedSizePopulation(individuals, comparator);
			pop = getReproductionAlgorithm().reproducePopulation(mate);
		}
		return null;
	}

	private void generateInitialPopulation(ProblemDomain problemDomain,
			int popSize) {
		// assign pop
	}
}
