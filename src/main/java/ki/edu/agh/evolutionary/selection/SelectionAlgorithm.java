package ki.edu.agh.evolutionary.selection;

import ki.edu.agh.population.Population;

public interface SelectionAlgorithm {
	Population select(Population population, int mateSize);
}
