package ki.edu.agh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;
import ki.edu.agh.fintess.PhenotypeNoiseGenerator;
import ki.edu.agh.fintess.RobustFitnessFunction;
import ki.edu.agh.fintess.StandardFitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

public class TestRobustFitness {

	private EuclideanSpacePhenotype phenotype = new EuclideanSpacePhenotype(
			new EuclideanSpacePoint(new double[] { 1., 2. }));
	private EuclideanSpacePoint noise = new EuclideanSpacePoint(new double[] {
			.5, .5 });
	private EuclideanSpacePoint noisyPoint = phenotype.getPoint().add(noise);

	@Test
	public void testRobustFitness() {
		PhenotypeNoiseGenerator noiseGenerator = mock(PhenotypeNoiseGenerator.class);
		when(noiseGenerator.addRandomNoise(phenotype)).thenReturn(
				new EuclideanSpacePhenotype(noisyPoint));

		Functor functor = mock(Functor.class);
		// setup mock to return chain of specified values on subsequent
		// getValue() invocation
		when(functor.getValue(noisyPoint)).thenReturn(1.).thenReturn(2.)
				.thenReturn(3.).thenReturn(4.).thenReturn(5.);

		// 10 times: 1+2+3+4+5+5+5+5+5+5 = 40
		// mean = 40 / 10 = 4 - this is a fitness value
		double expectedValue = 4.;

		// create roubstFitness with mocked functor
		RobustFitnessFunction fitness = new RobustFitnessFunction(
				new StandardFitnessFunction(functor));
		// set mocked noise generator
		fitness.setNoiseGenerator(noiseGenerator);

		double fitnessValue = fitness.computeFitness(phenotype);
		// check if expectedValue is equal to fitnessValue
		Assert.assertEquals(expectedValue, fitnessValue);
		// check if functor.getValue was invoked exactly sampleCount times
		Mockito.verify(functor, new Times(RobustFitnessFunction.sampleCount))
				.getValue(noisyPoint);
	}
}
