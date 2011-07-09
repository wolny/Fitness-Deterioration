package ki.edu.agh.algorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.deterioration.FitnessDeterioration;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.evolutionary.algorithm.EvolutionaryAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.problem.ProblemDomain;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SequentialDeteriorationEA implements StopCriterion {

	private static final Logger logger = Logger
			.getLogger(SequentialDeteriorationEA.class);

	private static final int DEF_NUM_OF_NODES = 200;

	private ProblemDomain problemDomain;

	private ClusteringAlgorithm<MetricSpacePoint> clusteringAlgorithm;

	private final List<Cluster<? extends PointWithFitness>> clusters = new LinkedList<Cluster<? extends PointWithFitness>>();

	private AlgorithmState currentState;

	private EvolutionaryAlgorithm evolutionaryAlgorithm;

	private FitnessDeterioration fitnessDeterioration;

	private int iterationCount;

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

	@Override
	public boolean isDone(AlgorithmState state) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public void run() throws IOException {
		logger.debug("initializing algorithm ...");

		int currentIteration = 1;
		while (getIterationCount() > 0) {
			getEvolutionaryAlgorithm().execute();
			List<? extends Individual> individuals = Arrays
					.asList(getEvolutionaryAlgorithm().getPopulation()
							.getAllMembers());

			getClusteringAlgorithm().setDataSet(
					(Collection<MetricSpacePoint>) individuals);

			// TODO: sequential clustering
			Collection<Cluster<MetricSpacePoint>> opticsClusters = getClusteringAlgorithm()
					.cluster(null);

			if (opticsClusters.size() == 0) {
				// TODO: set terminationCriaterion
			}

			// TODO: save best individuals from the last run
			saveBestIndividuals(getEvolutionaryAlgorithm()
					.extractBestIndividuals());

			// save clusters
			for (Cluster<MetricSpacePoint> cluster : opticsClusters) {
				getClusters()
						.add((Cluster<? extends PointWithFitness>) cluster);
			}

			FitnessFunction deterioratedFitness = fitnessDeterioration
					.deteriorateFitness(
							evolutionaryAlgorithm.getFitnessFunction(),
							getClusters());

			// TODO: print fitness landscape
			// TODO: specify problem domain
			PrintUtils.writeProblemPoints("fitnessLand" + currentIteration++,
					getProblemDomain(), DEF_NUM_OF_NODES);

			// set deteriorated fitness
			evolutionaryAlgorithm.setFitnessFunction(deterioratedFitness);

			iterationCount--;
		}
		// save results
	}

	public List<Cluster<? extends PointWithFitness>> getClusters() {
		return clusters;
	}

	private void saveBestIndividuals(Individual[] extractBestIndividuals) {
		// TODO Auto-generated method stub

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

	public static void main(String[] args) {
		logger.debug("main");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SequentialDeteriorationEA algorithm = applicationContext
				.getBean(SequentialDeteriorationEA.class);

	}
}
