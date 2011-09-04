package ki.edu.agh;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;
import ki.edu.agh.clustering.Cluster;
import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.deterioration.SimpleGaussianFitnessDeterioration;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleGaussianFitnessDeteriorationTest {
	private FitnessFunction fitness;
	private double fitnessValue = 1.0;

	private Functor crunchingFunctor;
	private double functorValue = 2.0;

	private int clustersCount = 5;

	private Collection<Cluster<? extends PointWithFitness>> clusters;

	private class MyFitnessDeterioration extends
			SimpleGaussianFitnessDeterioration {

		@Override
		public Functor createCrunchingFunctorForCluster(
				Cluster<? extends PointWithFitness> cluster) {
			return crunchingFunctor;
		}

	}

	@Before
	public void init() {
		clusters = new ArrayList<Cluster<? extends PointWithFitness>>(
				clustersCount);
		for (int i = 0; i < clustersCount; i++) {
			@SuppressWarnings("unchecked")
			Cluster<? extends PointWithFitness> cluster = mock(Cluster.class);
			clusters.add(cluster);
		}

		// mock cruncingFunctor
		crunchingFunctor = mock(Functor.class);
		when(crunchingFunctor.getValue(any(EuclideanSpacePoint.class)))
				.thenReturn(functorValue);

	}

	@Test
	public void testFitnessDeterioration() {
		// mock fitness function
		fitness = mock(FitnessFunction.class);
		when(fitness.computeFitness(any(Phenotype.class))).thenReturn(
				fitnessValue);

		MyFitnessDeterioration fitnessDeterioration = Mockito
				.spy(new MyFitnessDeterioration());

		FitnessFunction deterioratedFitness = fitnessDeterioration
				.deteriorateFitness(fitness, clusters);

		double value = deterioratedFitness
				.computeFitness(new EuclideanSpacePhenotype(null));
		// by default we consider the problem to be maximization problem,
		// so we substract Gaussians from current fitness value
		Assert.assertEquals(fitnessValue - clustersCount * functorValue, value);

	}

}
