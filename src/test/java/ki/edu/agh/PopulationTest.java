package ki.edu.agh;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualComparator;
import ki.edu.agh.population.Population;
import ki.edu.agh.statistics.Utils;

import org.junit.Before;
import org.junit.Test;

public class PopulationTest {

	private Population population;
	private int size = 10;
	private Comparator<Individual> comparator = new IndividualComparator();

	private boolean isSorted(Population population) {
		if (population.getSize() <= 1) {
			return true;
		}
		Individual[] individuals = population.getAllMembers();
		for (int i = 0; i < population.getSize() - 1; i++) {
			if (comparator.compare(individuals[i], individuals[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	@Before
	public void init() {
		population = new FixedSizePopulation(size);
		population.add(Utils.createRandomIndividual(2));
		population.add(Utils.createRandomIndividual(2));
		population.add(Utils.createRandomIndividual(2));
		population.add(Utils.createRandomIndividual(2));
		int k = 0;
		for (Individual i : population) {
			i.setFitness(++k + 1.1);
		}
	}

	@Test
	public void testSize() {
		Assert.assertEquals(4, population.getSize());
	}

	@Test
	public void testIterator() {
		List<Individual> list = new LinkedList<Individual>();
		for (Individual i : population) {
			Assert.assertNotNull(i);
			list.add(i);
		}
		Assert.assertEquals(population.getSize(), list.size());
	}

	@Test
	public void testSort() {
		population.sortMembers();
		Assert.assertTrue(isSorted(population));
	}
}
