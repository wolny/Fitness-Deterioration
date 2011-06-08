package ki.edu.agh.population;

/**
 * TODO: unit tests
 * 
 * @author slo
 * 
 */
public class Individual {

	private double fitness;
	private Phenotype phenotype;
	private boolean isFitnessAssigned = false;

	public Individual() {
	}

	public Individual(Phenotype phenotype) {
		this.phenotype = phenotype;
	}

	public double getFitness() {
		if (!isFitnessAssigned) {
			throw new IllegalStateException(
					"fitness has not been assigned to the individual");
		}
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
		isFitnessAssigned = true;
	}

	public Phenotype getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}

	@Override
	public String toString() {
		return getPhenotype().getPoint() + "	" + getFitness();
	}
}
