package ki.edu.agh.evolutionary.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.print.PrintUtils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RealValueGeneticAlgorithm extends AbstractEvolutionaryAlgorithm {
	static final Logger LOG = Logger.getLogger(RealValueGeneticAlgorithm.class);

	private Population pop;
	private int maxGenNum;
	private int generation;
	private int addIndNum;
	private boolean plotGeneration = true;

	public boolean isPlotGeneration() {
		return plotGeneration;
	}

	public void setPlotGeneration(boolean plotGeneration) {
		this.plotGeneration = plotGeneration;
	}

	public int getAddIndNum() {
		return addIndNum;
	}

	public void setAddIndNum(int addIndNum) {
		this.addIndNum = addIndNum;
	}

	public int getMaxGenNum() {
		return maxGenNum;
	}

	public void setMaxGenNum(int maxGenNum) {
		this.maxGenNum = maxGenNum;
	}

	@Override
	public EAResult execute() {
		LOG.info("executing real-value GA");
		pop = createInitialPopulation(getProblemDomain(), getPopulationSize());
		LOG.info("initial population created; population size: "
				+ getPopulationSize());
		writePoints();
		while (!checkTerminationCriterion()) {
			// assume that objective function and fitness is the same
			// (maximization problems)
			LOG.debug("assigning fitness ...");
			assignFitness(pop);
			// selection
			LOG.debug("selection");
			pop = getSelectionAlgorithm().select(pop, getPopulationSize());
			// reproduction
			LOG.debug("reproduction");
			pop = getReproductionAlgorithm().reproducePopulation(pop);

			// replace not feasible points by random points from domain
			pop = replaceNotFeasible(pop);

			generation++;
			if (isPlotGeneration()) {
				writePoints();
			}
		}
		// some solutions may not be feasible so assign fitness
		assignFitness(pop);
		return null;
	}

	private void writePoints() {
		LOG.debug("printing generation ...");
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
		return generation > maxGenNum;
	}

	@Override
	public void resetStopCriterion() {
		generation = 0;
		int size = getPopulationSize();
		setPopulationSize(size + getAddIndNum());
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ga-rastrigin-context.xml");
		EvolutionaryAlgorithm algorithm = context
				.getBean(EvolutionaryAlgorithm.class);
		algorithm.execute();
	}

}
