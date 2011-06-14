package ki.edu.agh.evolutionary.algorithm;

import java.util.Collection;

import ki.edu.agh.evolutionary.reproduction.ReproductionAlgorithm;
import ki.edu.agh.evolutionary.selection.SelectionAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Phenotype;
import ki.edu.agh.population.Population;

public abstract class EvolutionaryAlgorithm implements StopCriterion {
	private int generationNumber;
	private FitnessFunction fitnessFunction;
	private SelectionAlgorithm selectionAlgorithm;
	private ReproductionAlgorithm reproductionAlgorithm;

	public int getGenerationNumber() {
		return generationNumber;
	}

	public void increaseGenerationNumber() {
		generationNumber++;
	}

	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

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

	public abstract Collection<? extends Phenotype> extractBestPhenotypes();

	public abstract void execute();

}
