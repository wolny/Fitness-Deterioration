package ki.edu.agh.esss_dof;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ki.edu.agh.evolutionary.algorithm.AbstractEvolutionaryAlgorithm;
import ki.edu.agh.evolutionary.algorithm.EAResult;
import ki.edu.agh.evolutionary.algorithm.EvolutionaryAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.utils.LRUCache;

public class EsssDof extends AbstractEvolutionaryAlgorithm implements
		InitializingBean {

	private static final Logger LOG = Logger.getLogger(EsssDof.class);
	private static final int DEF_NUM_OF_NODES = 200;

	private final SimpleFitnessDeterioration simpleFD = new SimpleFitnessDeterioration();
	private Population pop;
	private List<Individual> bestIndividuals = new LinkedList<Individual>();
	private int maxGenAfterFD;
	private int generation;
	private int nEpoch;
	private double pIncr;

	public int getnEpoch() {
		return nEpoch;
	}

	public void setnEpoch(int nEpoch) {
		this.nEpoch = nEpoch;
	}

	private LRUCache<Double> lruCache;

	public double getpIncr() {
		return pIncr;
	}

	public void setpIncr(double pIncr) {
		this.pIncr = pIncr;
	}

	public int getMaxGenAfterFD() {
		return maxGenAfterFD;
	}

	public void setMaxGenAfterFD(int maxGenAfterFD) {
		this.maxGenAfterFD = maxGenAfterFD;
	}

	@Override
	public EAResult execute() {
		LOG.info("ESSS-DOF started");
		pop = createInitialPopulation(getProblemDomain(), getPopulationSize());
		LOG.info("initial population created; population size: "
				+ getPopulationSize());
		//writePoints();
		while (!checkTerminationCriterion()) {
			// assume that objective function and fitness is the same
			// (maximization problems)
			LOG.debug("assigning fitness ...");
			assignFitness(pop);
			LOG.debug("update mean fitness");
			updateMeanFitnessLRU();
			LOG.debug("Distinguish the best fitted individual");
			Individual x = pop.extractBestIndividual();
			if (testTrap()) {
				LOG.info("TRAP, generation: " + generation);
				bestIndividuals.add(x);
				FitnessFunction detFitness = simpleFD.deteriorateFitness(
						getFitnessFunction(), pop);

				updateFitnessFunction(detFitness);
				// reset local stop criterion for EA
				resetStopCriterion();

				try {
					printFitnessLandscape();
				} catch (IOException e) {
					LOG.error("CANNOT PRINT FITNESS LANDSCAPE");
				}
			}

			// selection
			LOG.debug("selection");
			pop = getSelectionAlgorithm().select(pop, getPopulationSize());
			// reproduction
			LOG.debug("reproduction");
			pop = getReproductionAlgorithm().reproducePopulation(pop);

			// replace not feasible points by random points from domain
			pop = replaceNotFeasible(pop);

			generation++;
			//writePoints();
		}
		// some solutions may not be feasible so assign fitness
		assignFitness(pop);
		LOG.info("found " + bestIndividuals.size() + " local solutions");
		return null;
	}

	private FitnessFunction getFitnessFunction() {
		return getProblemDomain().getFitnessFunction();
	}

	private void updateFitnessFunction(FitnessFunction deterioratedFitness) {
		LOG.info("Updating fitness function");
		// set new fitness for a problem
		getProblemDomain().setFitnessFunction(deterioratedFitness);
	}

	private boolean testTrap() {
		if (lruCache.isFull()) {
			double newF = lruCache.getFirst();
			double oldF = lruCache.getLast();
			if (newF < (1 + pIncr) * oldF) {
				return true;
			}
		}
		return false;
	}

	private void updateMeanFitnessLRU() {
		double meanF = computeMeanFitness(pop);
		lruCache.add(meanF);
	}

	private double computeMeanFitness(Population pop2) {
		double sum = 0.0;

		for (Individual ind : pop2) {
			sum += ind.getFitness();
		}

		return sum / pop2.getSize();
	}

	private void printFitnessLandscape() throws IOException {
		int len = 4;
		int currIter = bestIndividuals.size();
		String iterStr = makeStr(currIter, len);
		PrintUtils.writeProblemPoints("fitnessLand_iter" + iterStr,
				getProblemDomain(), DEF_NUM_OF_NODES);
	}

	private String makeStr(int currIter, int len) {
		int zeroCount = len - (currIter + "").length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < zeroCount; i++) {
			sb.append("0");
		}
		sb.append(currIter);
		return sb.toString();
	}

	@Override
	public Population getPopulation() {
		return pop;
	}

	@Override
	public Individual[] extractBestIndividuals() {
		return bestIndividuals.toArray(new Individual[0]);
	}

	@Override
	public boolean checkTerminationCriterion() {
		return generation > maxGenAfterFD;
	}

	@Override
	public void resetStopCriterion() {
		generation = 0;
		lruCache.clear();
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"esssdof-langer-context.xml");
		EvolutionaryAlgorithm algorithm = context
				.getBean(EvolutionaryAlgorithm.class);
		algorithm.execute();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.lruCache = new LRUCache<Double>(getnEpoch());
	}

	private void writePoints() {
		List<EuclideanSpacePoint> points = new ArrayList<EuclideanSpacePoint>(
				pop.getSize());
		for (Individual ind : pop) {
			EuclideanSpacePhenotype esp = (EuclideanSpacePhenotype) ind
					.getPhenotype();
			points.add(esp.getPoint());
		}
		try {
			PrintUtils.writePoints("generation" + generation + ".dat", points);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
