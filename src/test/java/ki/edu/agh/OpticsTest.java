package ki.edu.agh;

import java.util.Collection;
import java.util.LinkedList;

import junit.framework.Assert;
import ki.edu.agh.clustering.optics.OpticsClustering;
import ki.edu.agh.clustering.optics.OpticsClusterPoint;
import ki.edu.agh.clustering.optics.OpticsParamteres;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;

import org.junit.Before;
import org.junit.Test;

public class OpticsTest {

	private Collection<? extends Individual> individuals;

	private Collection<? extends Individual> createPopulation(
			EuclideanSpacePoint... points) {
		Collection<Individual> result = new LinkedList<Individual>();
		for (EuclideanSpacePoint point : points) {
			result.add(new IndividualWithRealVectorPhenotype(
					new EuclideanSpacePhenotype(point)));
		}
		return result;
	}

	@Before
	public void init() {
		individuals = createPopulation(new EuclideanSpacePoint(new double[] {
				2, 2 }), new EuclideanSpacePoint(new double[] { 2, 2 }),
				new EuclideanSpacePoint(new double[] { 1, 1 }),
				new EuclideanSpacePoint(new double[] { -1, 1 }),
				new EuclideanSpacePoint(new double[] { 2, 1 }));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testNearestNeighbor() {
		OpticsClusterPoint<IndividualWithRealVectorPhenotype> cPoint = new OpticsClusterPoint<IndividualWithRealVectorPhenotype>(
				new IndividualWithRealVectorPhenotype(
						new EuclideanSpacePhenotype(new EuclideanSpacePoint(
								new double[] { 0., 0. }))));
		OpticsClustering<IndividualWithRealVectorPhenotype> optics = new OpticsClustering<IndividualWithRealVectorPhenotype>();
		optics.setClusteringParameterSet(new OpticsParamteres());
		optics.cluster((Collection<IndividualWithRealVectorPhenotype>) individuals);
		Collection<OpticsClusterPoint<IndividualWithRealVectorPhenotype>> result = optics
				.getEpsilonNeighbor(cPoint, 2.0);
		Assert.assertTrue(result.size() == 2);
	}
}
