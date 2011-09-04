package ki.edu.agh.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.SimpleCluster;
import ki.edu.agh.clustering.optics.OpticsClustering;
import ki.edu.agh.clustering.optics.OpticsParamteres;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.MetricSpacePoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SequentialDeteriorationEAWithOpticsClustering extends
		AbstractSequentialDeteriorationEA {

	@Override
	protected FitnessFunction performClusteringAndFitnessDeterioration(
			Collection<Cluster<MetricSpacePoint>> currentClusters) {
		// TODO: sequential clustering
		OpticsClustering<MetricSpacePoint> optics = (OpticsClustering<MetricSpacePoint>) getClusteringAlgorithm();

		double delta = 0.5;
		Collection<Cluster<MetricSpacePoint>> extractedClusters = optics
				.cluster(new OpticsParamteres(optics.getEpsilon() - delta));

		if (extractedClusters.size() == 0) {
			return null;
		}

		currentClusters.addAll(extractedClusters);

		// cast clusters to pointsWithFitness
		List<Cluster<? extends PointWithFitness>> clustersForDeterioration = new ArrayList<Cluster<? extends PointWithFitness>>(
				extractedClusters.size());

		for (Cluster<MetricSpacePoint> cluster : extractedClusters) {
			SimpleCluster<PointWithFitness> simpleCluster = new SimpleCluster<PointWithFitness>();
			for (MetricSpacePoint obj : cluster.getClusterPoints()) {
				simpleCluster.addClusterPoint((PointWithFitness) obj);
			}
			clustersForDeterioration.add(simpleCluster);
		}

		FitnessFunction deterioratedFitness = getFitnessDeterioration()
				.deteriorateFitness(getProblemDomain().getFitnessFunction(),
						clustersForDeterioration);

		return deterioratedFitness;
	}

	public static void main(String[] args) throws Exception {
		logger.debug("main");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"test-context.xml");
		
		applicationContext.publishEvent(new ContextStartedEvent(applicationContext));
		
		AbstractSequentialDeteriorationEA algorithm = (AbstractSequentialDeteriorationEA) applicationContext
				.getBean("algorithm");

		algorithm.run();

	}
}
