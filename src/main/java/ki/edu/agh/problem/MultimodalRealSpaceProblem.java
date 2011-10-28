package ki.edu.agh.problem;

import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.Phenotype;

public class MultimodalRealSpaceProblem implements ProblemDomain {

	private Domain domain;
	private FitnessFunction fitnessFunction;
	private boolean minimization;

	public MultimodalRealSpaceProblem() {
	}

	public MultimodalRealSpaceProblem(Domain domain,
			FitnessFunction fitnessFunction) {
		this.domain = domain;
		this.fitnessFunction = fitnessFunction;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	@Override
	public Functor getFunctor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Domain getDomain() {
		return domain;
	}

	@Override
	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public boolean isMinimization() {
		return minimization;
	}

	@Override
	public void setMinimization(boolean minimization) {
		this.minimization = minimization;
	}

	@Override
	public boolean isFeasible(Phenotype phenotype) {
		EuclideanSpacePhenotype esp = (EuclideanSpacePhenotype) phenotype;
		EuclideanSpacePoint point = esp.getPoint();
		for (int i = 0; i < getDomain().getSpaceDimension(); i++) {
			if(!getDomain().getInterval(i).isInside(point.getCoordinate(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public EuclideanSpacePoint[] getRandomPoints(int popSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
