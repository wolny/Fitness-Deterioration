package ki.edu.agh.evolutionary.selection;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;

public interface SelectionAlgorithm {
	Individual[] select(Population population, int mateSize);
}
