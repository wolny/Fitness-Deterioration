package ki.edu.agh.deterioration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import ki.edu.agh.algorithm.AbstractSequentialDeteriorationEA;
import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.fintess.StandardFitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.problem.Domain;
import ki.edu.agh.problem.MultimodalRealSpaceProblem;
import ki.edu.agh.problem.ProblemDomain;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * implementation of the basic fitness deterioration schema with one crunching
 * functor per cluster of points
 * 
 * @author slo
 * 
 */
public abstract class AbstractFitnessDeterioration implements
		FitnessDeterioration, ApplicationListener<ContextStartedEvent> {

	protected static final Logger logger = Logger
			.getLogger(FitnessDeterioration.class);

	/**
	 * minimization or maximization problem
	 */
	private boolean minimization;

	/**
	 * problem space used when visualizing deterioration process
	 */
	private Domain domain;

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public boolean isMinimization() {
		return minimization;
	}

	public void setMinimization(boolean minimization) {
		this.minimization = minimization;
	}

	/**
	 * creates deteriorated fitness by using current fitness and information
	 * about basins of attraction provided by collection of clustered points
	 * with its fitness values
	 */
	@Override
	public FitnessFunction deteriorateFitness(FitnessFunction currentFitness,
			Collection<Cluster<? extends PointWithFitness>> clusters) {

		logger.info("Performing fitness deterioration for " + clusters.size()
				+ " clusters");

		if (clusters == null || clusters.size() == 0) {
			return currentFitness;
		}

		Collection<Functor> crunchingFunctions = new ArrayList<Functor>(
				clusters.size());

		for (Cluster<? extends PointWithFitness> cluster : clusters) {
			Functor crunchingFunctor = createCrunchingFunctorForCluster(cluster);
			// printCrunchingFunctor(now, i, crunchingFunctor);
			crunchingFunctions.add(crunchingFunctor);
		}

		return createDeterioratedFitness(currentFitness, crunchingFunctions);
	}

	protected void printCrunchingFunctor(Date now, int i,
			Functor crunchingFunctor) {
		try {
			String fileName = "crunchFun" + i + "_" + now.getTime();
			logger.debug("Print crunching functor to: " + fileName);
			ProblemDomain problem = new MultimodalRealSpaceProblem(getDomain(),
					new StandardFitnessFunction(crunchingFunctor));
			PrintUtils.writeProblemPoints(fileName, problem, 200);
		} catch (IOException e) {
			logger.warn("Cannot write crunching functor to file", e);
		}
	}

	/**
	 * creates deteriorated fitness function by combining current fitness and
	 * crunching functions
	 * 
	 * @param currentFitness
	 * @param crunchingFunctions
	 * @return
	 */
	public abstract FitnessFunction createDeterioratedFitness(
			FitnessFunction currentFitness,
			Collection<Functor> crunchingFunctions);

	/**
	 * creates crunching functor for cluster of multidimensional vectors
	 * 
	 * @param cluster
	 * @return
	 */
	public abstract Functor createCrunchingFunctorForCluster(
			Cluster<? extends PointWithFitness> cluster);

	public Comparator<PointWithFitness> getComparator() {
		if (isMinimization()) {
			return MIN_CMP;
		}
		// maximization
		return MAX_CMP;
	}

	private static final Comparator<PointWithFitness> MIN_CMP = new Comparator<PointWithFitness>() {
		@Override
		public int compare(PointWithFitness o1, PointWithFitness o2) {
			if (o1.getFitness() < o2.getFitness()) {
				return -1;
			} else if (o1.getFitness() > o2.getFitness()) {
				return 1;
			}
			return 0;
		}
	};

	private static final Comparator<PointWithFitness> MAX_CMP = new Comparator<PointWithFitness>() {
		@Override
		public int compare(PointWithFitness o1, PointWithFitness o2) {
			if (o1.getFitness() > o2.getFitness()) {
				return -1;
			} else if (o1.getFitness() < o2.getFitness()) {
				return 1;
			}
			return 0;
		}
	};

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		AbstractSequentialDeteriorationEA algorithm = (AbstractSequentialDeteriorationEA) event
				.getApplicationContext().getBean("algorithm");

		setMinimization(algorithm.getProblemDomain().isMinimization());
	};
}
