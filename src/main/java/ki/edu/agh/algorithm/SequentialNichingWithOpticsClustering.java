package ki.edu.agh.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.clustering.SimpleCluster;
import ki.edu.agh.clustering.optics.OpticsClustering;
import ki.edu.agh.clustering.optics.OpticsParamteres;
import ki.edu.agh.deterioration.AbstractFitnessDeterioration;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.point.MetricSpacePoint;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SequentialNichingWithOpticsClustering extends
		AbstractSequentialNiching implements InitializingBean {

	@Override
	protected FitnessFunction performClusteringAndFitnessDeterioration(
			Collection<Cluster<MetricSpacePoint>> currentClusters) {
		// sequential niching
		OpticsClustering<MetricSpacePoint> optics = (OpticsClustering<MetricSpacePoint>) getClusteringAlgorithm();

		// double delta = 0.5;
		Collection<Cluster<MetricSpacePoint>> extractedClusters = optics
				.cluster(new OpticsParamteres(optics.getEpsilon() * 0.75));

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

	@Override
	public void afterPropertiesSet() throws Exception {
		// set problem domain in fitness deterioration algorithm for
		// visualization
		((AbstractFitnessDeterioration) getFitnessDeterioration())
				.setDomain(getProblemDomain().getDomain());
		// set optics.minPts; should be 10 <= minPts <= 20
		OpticsClustering<?> optics = (OpticsClustering<?>) getClusteringAlgorithm();
		int minPts = getEvolutionaryAlgorithm().getPopulationSize() / 4;
		if (minPts > 20) {
			minPts = 20;
		}
		if (minPts < 10) {
			minPts = 10;
		}
		optics.setMinPoints(minPts);
	}

	public static void main(String[] args) throws Exception {
		LOG.debug("main");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"csfd-3mod-context.xml");

		applicationContext.publishEvent(new ContextStartedEvent(
				applicationContext));

		AbstractSequentialNiching algorithm = (AbstractSequentialNiching) applicationContext
				.getBean("algorithm");

		algorithm.run();
	}

}
