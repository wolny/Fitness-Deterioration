package ki.edu.agh.algorithm;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.clustering.ClusteringUtils;
import ki.edu.agh.deterioration.FitnessDeterioration;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.evolutionary.algorithm.EvolutionaryAlgorithm;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SequentialDeteriorationEA implements StopCriterion {

	private static final Logger logger = Logger
			.getLogger(SequentialDeteriorationEA.class);

	private class ClusterChecker implements AlgorithmState {
		private int clustersCount;

		public ClusterChecker(int clustersCount) {
			this.clustersCount = clustersCount;
		}

		public int getClustersCount() {
			return clustersCount;
		}
	}

	private ClusteringAlgorithm<IndividualWithRealVectorPhenotype> clusteringAlgorithm;

	private final List<Cluster<PointWithFitness>> clusters = new LinkedList<Cluster<PointWithFitness>>();

	private AlgorithmState currentState;

	private EvolutionaryAlgorithm evolutionaryAlgorithm;

	private FitnessDeterioration fitnessDeterioration;

	public ClusteringAlgorithm<IndividualWithRealVectorPhenotype> getClusteringAlgorithm() {
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
		ClusterChecker checker = (ClusterChecker) state;
		return checker.getClustersCount() > 0;
	}

	public void run() {
		logger.debug("initializing algorithm ...");

		while (true) {
			evolutionaryAlgorithm.execute();
			IndividualWithRealVectorPhenotype[] lastGenerationIndividuals = (IndividualWithRealVectorPhenotype[]) evolutionaryAlgorithm
					.getPopulation().getAllMembers();

			Collection<Cluster<IndividualWithRealVectorPhenotype>> opticsClusters = clusteringAlgorithm
					.cluster(Arrays.asList(lastGenerationIndividuals));

			setCurrentState(new ClusterChecker(opticsClusters.size()));

			if (isDone(getCurrentState())) {
				break;
			} else {
				// TODO: save best individuals from the last run
				evolutionaryAlgorithm.extractBestIndividuals();
			}

			// save clusters
			Collection<Cluster<PointWithFitness>> clusterForDeterioration = ClusteringUtils
					.convertClustersOfIndividuals(opticsClusters);
			clusters.addAll(clusterForDeterioration);

			FitnessFunction deterioratedFitness = fitnessDeterioration
					.deteriorateFitness(
							evolutionaryAlgorithm.getFitnessFunction(),
							clusterForDeterioration);

			// TODO: print fitness landscape

			// set deteriorated fitness
			evolutionaryAlgorithm.setFitnessFunction(deterioratedFitness);
		}
		// save results
	}

	public void setClusteringAlgorithm(
			ClusteringAlgorithm<IndividualWithRealVectorPhenotype> clusteringAlgorithm) {
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
