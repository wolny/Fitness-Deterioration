package ki.edu.agh;

import ki.edu.agh.population.Individual;
import ki.edu.agh.statistics.Utils;

import org.junit.Before;
import org.junit.Test;

public class IndividualTest {
	private Individual individual;

	@Before
	public void init() {
		individual = Utils.createRandomIndividual(2);
	}

	@Test(expected = IllegalStateException.class)
	public void testIndividual() {
		individual.getFitness();
	}

	@Test
	public void testGetFitness() {
		individual.setFitness(.12);
		individual.getFitness();
	}
}
