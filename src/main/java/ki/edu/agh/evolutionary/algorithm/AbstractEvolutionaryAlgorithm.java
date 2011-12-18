package ki.edu.agh.evolutionary.algorithm;

import org.apache.log4j.Logger;

import ki.edu.agh.evolutionary.reproduction.ReproductionAlgorithm;
import ki.edu.agh.evolutionary.selection.SelectionAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;

/**
 * 
 * @author slo
 * 
 */
public abstract class AbstractEvolutionaryAlgorithm implements
		EvolutionaryAlgorithm {
	private static final Logger LOG = Logger
			.getLogger(AbstractEvolutionaryAlgorithm.class);
	private int populationSize;
	private ProblemDomain problemDomain;
	private SelectionAlgorithm selectionAlgorithm;
	private ReproductionAlgorithm reproductionAlgorithm;

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

	protected void assignFitness(Population population) {
		for (Individual individual : population) {
			individual.setFitness(getFitnessFunction().computeFitness(
					individual.getPhenotype()));
		}
	}

	@Override
	public ProblemDomain getProblemDomain() {
		return problemDomain;
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
		this.problemDomain = problem;
	}

	private FitnessFunction getFitnessFunction() {
		return getProblemDomain().getFitnessFunction();
	}

	@Override
	public Population replaceNotFeasible(Population population) {
		ProblemDomain problem = getProblemDomain();
		for (Individual individual : population) {
			if (!problem.isFeasible(individual.getPhenotype())) {
				LOG.debug("phenotype: " + individual.getPhenotype()
						+ " not feasible");
				IndividualWithRealVectorPhenotype ind = (IndividualWithRealVectorPhenotype) individual;
				ind.setPhenotype(new EuclideanSpacePhenotype(problem
						.createRandomPoint()));
			}
		}
		return population;
	}

	protected Population createInitialPopulation(ProblemDomain problemDomain, int popSize) {
		EuclideanSpacePoint[] points = problemDomain.getRandomPoints(popSize);
		Individual[] individuals = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			individuals[i] = new IndividualWithRealVectorPhenotype(
					new EuclideanSpacePhenotype(points[i]));
		}
		return new FixedSizePopulation(individuals);
	}
}
