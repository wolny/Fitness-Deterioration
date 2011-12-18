package ki.edu.agh.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.clustering.SimpleCluster;
import ki.edu.agh.deterioration.FitnessDeterioration;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.evolutionary.algorithm.EvolutionaryAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.problem.ProblemDomain;

import org.apache.log4j.Logger;

public abstract class AbstractSequentialNiching {

	protected static final Logger LOG = Logger
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
		LOG.debug("initializing algorithm ...");
		String fileName = "originalFitnessLand";
		LOG.debug("Printing fitness landscape to file: " + fileName);
		PrintUtils.writeProblemPoints(fileName, getProblemDomain(),
				DEF_NUM_OF_NODES);

		for (currentIteration = 0; currentIteration < getIterationCount(); currentIteration++) {
			// perform single evolutionary algorithm execution
			LOG.info("Iteration: " + currentIteration);
			LOG.info("Executing EA ...");
			getEvolutionaryAlgorithm().execute();

			// convert individuals to metric space points
			Population population = getEvolutionaryAlgorithm().getPopulation();
			List<MetricSpacePoint> msPoints = convertIndividualsToMetricSpacePoints(population);

			// initialize clustering algorithm
			getClusteringAlgorithm().setDataSet(msPoints);

			LOG.info("Performing clustering and fitness deterioration ...");

			// perform clustering and fitness deterioration
			Collection<Cluster<MetricSpacePoint>> currentClusters = new LinkedList<Cluster<MetricSpacePoint>>();
			FitnessFunction deterioratedFitness = performClusteringAndFitnessDeterioration(currentClusters);

			if (currentClusters.size() == 0) {
				LOG.info("Terminating Cluster Supported Fitness Deterioration due to poor clusters quality");
				break;
			}

			if (deterioratedFitness == null) {
				LOG.info("Could not create deteriorated fitness function");
				break;
			}

			// save clusters and update fitness function
			saveClusters(currentClusters);
			updateFitnessFunction(deterioratedFitness);

			// reset local stop criterion for EA
			getEvolutionaryAlgorithm().resetStopCriterion();

			// print fitness landscape
			printFitnessLandscape();
		}

		// save results
		extractBestIndividualsFromClusters();
	}

	private List<MetricSpacePoint> convertIndividualsToMetricSpacePoints(
			Population population) {
		List<MetricSpacePoint> msPoints = new ArrayList<MetricSpacePoint>(
				population.getSize());
		for (Individual individual : population) {
			msPoints.add((MetricSpacePoint) individual);
		}
		return msPoints;
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
		LOG.debug("Saving clusters which have given the best deterioration");

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
		LOG.info("Updating fitness function for the next run");
		// set new fitness for a problem
		getProblemDomain().setFitnessFunction(deterioratedFitness);
	}

	private void printFitnessLandscape() throws IOException {
		int len = (iterationCount + "").length();
		int currIter = currentIteration + 1;
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
}
