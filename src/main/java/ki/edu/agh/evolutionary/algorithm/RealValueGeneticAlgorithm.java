package ki.edu.agh.evolutionary.algorithm;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;

public class RealValueGeneticAlgorithm extends AbstractEvolutionaryAlgorithm {

	private Population pop;
	private int populationSize;
	private int maxGenNum = 500;

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	@Override
	public EAResult execute() {
		pop = createInitialPopulation(getProblemDomain(), getPopulationSize());

		while (!checkTerminationCriterion()) {
			// assume that objective function and fitness is the same
			// (maximization problems)
			assignFitness(pop);
			// selection
			Individual[] matePool = getSelectionAlgorithm().select(pop,
					populationSize);
			pop.updatePopulation(matePool, populationSize);
			// reproduction
			getReproductionAlgorithm().reproducePopulation(pop);
		}

		return null;
	}

	private Population createInitialPopulation(ProblemDomain problemDomain,
			int popSize) {
		EuclideanSpacePoint[] points = problemDomain.getRandomPoints(popSize);
		Individual[] individuals = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			individuals[i] = new IndividualWithRealVectorPhenotype(
					new EuclideanSpacePhenotype(points[i]));
		}
		return new FixedSizePopulation(individuals);
	}

	@Override
	public Population getPopulation() {
		return pop;
	}

	@Override
	public Individual[] extractBestIndividuals() {
		// sample implementation
		return pop.getAllMembers();
	}

	@Override
	public boolean checkTerminationCriterion() {
		// TODO: sth clever
		return getGenerationNumber() > maxGenNum;
	}
}
