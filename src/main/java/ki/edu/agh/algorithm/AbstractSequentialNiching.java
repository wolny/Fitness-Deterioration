package ki.edu.agh.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.clustering.SimpleCluster;
import ki.edu.agh.deterioration.AbstractFitnessDeterioration;
import ki.edu.agh.deterioration.FitnessDeterioration;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.evolutionary.algorithm.EvolutionaryAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.problem.ProblemDomain;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractSequentialNiching implements
		InitializingBean {

	protected static final Logger logger = Logger
			.getLogger(AbstractSequentialNiching.class);

	private static final int DEF_NUM_OF_NODES = 200;

	private ProblemDomain problemDomain;

	private ClusteringAlgorithm<MetricSpacePoint> clusteringAlgorithm;

	private final List<Cluster<PointWithFitness>> clusters = new LinkedList<Cluster<PointWithFitness>>();

	private AlgorithmState currentState;

	private EvolutionaryAlgorithm evolutionaryAlgorithm;

	private FitnessDeterioration fitnessDeterioration;

	private int iterationCount;

	private int currentIteration;

	public ProblemDomain getProblemDomain() {
		return problemDomain;
	}

	public void setProblemDomain(ProblemDomain problemDomain) {
		this.problemDomain = problemDomain;
	}

	public int getIterationCount() {
		return iterationCount;
	}

	public void setIterationCount(int iterationCount) {
		this.iterationCount = iterationCount;
	}

	public ClusteringAlgorithm<MetricSpacePoint> getClusteringAlgorithm() {
		return clusteringAlgorithm;
	}

	public AlgorithmState getCurrentState() {
		return currentState;
	}

	public EvolutionaryAlgorithm getEvolutionaryAlgorithm() {
		return evolutionaryAlgorithm;
	}

	public FitnessDeterioration getFitnessDeterioration() {
		return fitnessDeterioration;
	}

	/**
	 * performs clustering and deterioration of fitness function; deterioration
	 * process might be directly dependent on the result of clustering algorithm
	 * or we may execute clustering multiple times in order to find appropriate
	 * crunching functors so we have declared this function as abstract
	 * 
	 * @param currentClusters
	 *            collection to store clusters extracted during clustering
	 *            process
	 * @return
	 */
	protected abstract FitnessFunction performClusteringAndFitnessDeterioration(
			Collection<Cluster<MetricSpacePoint>> currentClusters);

	public void run() throws Exception {
		logger.debug("initializing algorithm ...");
		String fileName = "originalFitnessLand";
		logger.debug("Printing fitness landscape to file: " + fileName);
		PrintUtils.writeProblemPoints(fileName, getProblemDomain(),
				DEF_NUM_OF_NODES);

		for (currentIteration = 0; currentIteration < getIterationCount(); currentIteration++) {
			// perform single evolutionary algorithm execution
			getEvolutionaryAlgorithm().execute();
			Individual[] individuals = getEvolutionaryAlgorithm()
					.getPopulation().getAllMembers();

			List<MetricSpacePoint> pointsToCluster = new ArrayList<MetricSpacePoint>(
					individuals.length);
			for (Individual individual : individuals) {
				pointsToCluster.add((MetricSpacePoint) individual);
			}
			// initialize clustering algorithm
			getClusteringAlgorithm().setDataSet(pointsToCluster);

			logger.info("Performing fitness deterioration ...");

			Collection<Cluster<MetricSpacePoint>> currentClusters = new LinkedList<Cluster<MetricSpacePoint>>();
			FitnessFunction deterioratedFitness = performClusteringAndFitnessDeterioration(currentClusters);

			if (currentClusters.size() == 0) {
				logger.info("Terminating sequential algorithm due to poor clusters quality");
				break;
			}

			if (deterioratedFitness == null) {
				logger.info("Could not create deteriorated fitness function");
				break;
			}

			// save clusters and update fitness function
			saveClusters(currentClusters);
			updateFitnessFunction(deterioratedFitness);

			// print fitness landscape
			printFitnessLandscape();
		}

		// TODO: perform one last run of EA after finishing our meta algorithm

		// save results
		extractBestIndividualsFromClusters();
	}

	/**
	 * save clusters; we treat clusters as a detected sets of individuals;
	 * obviously, the set of the best individuals from each cluster constitute a
	 * solution to a given problem
	 * 
	 * @param opticsClusters
	 */
	private void saveClusters(
			Collection<Cluster<MetricSpacePoint>> opticsClusters) {
		logger.debug("Saving clusters which have given the best deterioration");

		for (Cluster<MetricSpacePoint> cluster : opticsClusters) {
			Cluster<PointWithFitness> simpleCluster = new SimpleCluster<PointWithFitness>();
			for (MetricSpacePoint obj : cluster.getClusterPoints()) {
				simpleCluster.addClusterPoint((PointWithFitness) obj);
			}
			getClusters().add(simpleCluster);
		}
	}

	private void extractBestIndividualsFromClusters() {
		// TODO Auto-generated method stub
		// TODO: print detected sets
	}

	private void updateFitnessFunction(FitnessFunction deterioratedFitness) {
		logger.info("Updating fitness function for the next run");
		// set new fitness for a problem
		getProblemDomain().setFitnessFunction(deterioratedFitness);
	}

	private void printFitnessLandscape() throws IOException {
		int currIter = currentIteration + 1;
		PrintUtils.writeProblemPoints("fitnessLand_iter" + currIter,
				getProblemDomain(), DEF_NUM_OF_NODES);
	}

	public List<Cluster<PointWithFitness>> getClusters() {
		return clusters;
	}

	public void setClusteringAlgorithm(
			ClusteringAlgorithm<MetricSpacePoint> clusteringAlgorithm) {
		this.clusteringAlgorithm = clusteringAlgorithm;
	}

	public void setCurrentState(AlgorithmState currentState) {
		this.currentState = currentState;
	}

	public void setEvolutionaryAlgorithm(
			EvolutionaryAlgorithm evolutionaryAlgorithm) {
		this.evolutionaryAlgorithm = evolutionaryAlgorithm;
	}

	public void setFitnessDeterioration(
			FitnessDeterioration fitnessDeterioration) {
		this.fitnessDeterioration = fitnessDeterioration;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		getEvolutionaryAlgorithm().setProblemDomain(getProblemDomain());

		// set problem domain in fitness deterioration algorithm for
		// visualization
		((AbstractFitnessDeterioration) getFitnessDeterioration())
				.setDomain(getProblemDomain().getDomain());
	}
}
