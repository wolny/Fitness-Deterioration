package ki.edu.agh.evolutionary.selection;

import java.util.Arrays;
import java.util.Random;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualComparator;
import ki.edu.agh.population.Population;
import ki.edu.agh.statistics.Utils;

/**
 * The probability of an individual to be selected is proportional to (a power
 * of) its position (rank) in the sorted list of all individuals in the
 * population
 * 
 * @author slo
 * 
 */
public class OrderedSelection implements SelectionAlgorithm {

	/**
	 * The implicit parameter k of the ordered selection algorithm determines
	 * the selection pressure. It equals to the number of expected offspring of
	 * the best individual
	 */
	private double k = 4.; // default value

	private Random rand = new Random();

	public OrderedSelection() {

	}

	public OrderedSelection(double k) {
		this.k = k;
	}

	@Override
	public Population select(Population population, int mateSize) {
		double power = 1 / (1 - (Math.log(k) / Math.log(mateSize)));
		Individual[] mate = new Individual[mateSize];
		int size = 0;
		population.sortMembers();
		int populationSize = population.getSize();
		for (int i = 0; i < mateSize; i++) {
			int index = (int) Math.floor(Math.pow(rand.nextDouble(), power)
					* populationSize);
			Individual individual = population.get(index);
			mate[size++] = individual;
		}
		population.updatePopulation(mate, mateSize);
		return population;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public static void main(String[] args) {
		Population pop = Utils.createRandomUniModalPopulation(2, 100);
		System.out.println(pop);

		System.out.println();
		OrderedSelection selection = new OrderedSelection(6);
		pop = selection.select(pop, 100);
		Arrays.sort(pop.getAllMembers(), new IndividualComparator());
		for (Individual i : pop) {
			System.out.println(i);
		}
	}

}
