package ki.edu.agh.population;

public interface Population extends Iterable<Individual> {

	/**
	 * adds an individual the the population
	 * 
	 * @param individual
	 */
	void add(Individual individual);

	/**
	 * 
	 * @param individuals
	 */
	void addAll(Individual[] individuals);

	/**
	 * 
	 * @return all individuals in the population
	 */
	Individual[] getAllMembers();

	/**
	 * 
	 * @param i
	 * @return i-th individual from the population
	 */
	Individual get(int i);

	/**
	 * 
	 * @return the number of individuals in the population
	 */
	int getSize();

	/**
	 * 
	 * used for special types of selection, e.g. OrderedSelection
	 */
	void sortMembers();

	/**
	 * should be in selection/reproduction algorithm to update matePool
	 * 
	 * @param matePool
	 * @param mateSize
	 */
	void updatePopulation(Individual[] matePool, int mateSize);
}
