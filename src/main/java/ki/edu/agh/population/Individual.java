package ki.edu.agh.population;

public interface Individual {
	public double getFitness();

	public Phenotype getPhenotype();

	public void setFitness(double fitness);
}
