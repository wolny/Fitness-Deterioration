package ki.edu.agh.population;

public interface Population extends Iterable<Individual> {
	void add(Individual individual);

	void addAll(Individual[] individuals);

	Individual[] getAllMembers();
	
	Individual get(int i);

	int getSize();
	
	void sortMembers();
}
