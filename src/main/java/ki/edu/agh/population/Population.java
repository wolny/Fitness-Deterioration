package ki.edu.agh.population;

public interface Population extends Iterable<Individual> {
	void add(Individual individual);

	void addAll(Individual[] individuals);

	Individual[] getAllMembers();

	int getSize();
}
