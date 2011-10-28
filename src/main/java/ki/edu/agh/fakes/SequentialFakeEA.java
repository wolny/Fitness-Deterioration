package ki.edu.agh.fakes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.evolutionary.algorithm.AbstractEvolutionaryAlgorithm;
import ki.edu.agh.evolutionary.algorithm.EAResult;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.EuclideanSpacePhenotype;
import ki.edu.agh.population.FixedSizePopulation;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.IndividualWithRealVectorPhenotype;
import ki.edu.agh.population.Population;
import ki.edu.agh.print.PrintUtils;
import ki.edu.agh.statistics.PointGenerator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class SequentialFakeEA extends AbstractEvolutionaryAlgorithm implements
		InitializingBean {

	private static final Logger logger = Logger
			.getLogger(SequentialFakeEA.class);

	private int clusterSize;
	private int currentIteration;
	private List<Coords2D> clusterCenters;
	private Functor functor;

	private List<IndividualWithRealVectorPhenotype> individuals;

	public Functor getFunctor() {
		return functor;
	}

	public void setFunctor(Functor functor) {
		this.functor = functor;
	}

	public int getClusterSize() {
		return clusterSize;
	}

	public void setClusterSize(int clusterSize) {
		this.clusterSize = clusterSize;
	}

	public List<Coords2D> getClusterCenters() {
		return clusterCenters;
	}

	public void setClusterCenters(List<Coords2D> clusterCenters) {
		this.clusterCenters = clusterCenters;
	}

	@Override
	public EAResult execute() {
		try {
			setIndividuals(new ArrayList<IndividualWithRealVectorPhenotype>(
					clusterCenters.size() * clusterSize));
			logger.info("SequentialFakeEA - iteration: " + currentIteration);
			if (currentIteration == clusterCenters.size()) {
				generateNoise();
				return null;
			}

			// generate Gaussian cluster
			Coords2D c = clusterCenters.get(currentIteration);
			double stdDev = c.getStdDev();
			logger.debug("generating cluster - stdDev: " + stdDev);
			generateGaussianCluster(c, stdDev);

			PrintUtils.writePopulation("fakePopulation" + currentIteration,
					getPopulation());
			currentIteration++;
		} catch (Exception e) {
			logger.error("FakeEA execution error", e);
		}

		return null;
	}

	private void generateGaussianCluster(Coords2D c, double stdDev) {
		EuclideanSpacePoint mean = new EuclideanSpacePoint(new double[] {
				c.getX(), c.getY() });
		Collection<EuclideanSpacePoint> points = PointGenerator
				.generateGaussianPoints2D(mean, stdDev, clusterSize);
		assignFitnessToPoints(points);
	}

	private void generateNoise() {
		EuclideanSpacePoint mean = new EuclideanSpacePoint(new double[] { 0.0,
				0.0 });
		Collection<EuclideanSpacePoint> points = PointGenerator
				.generateUniformCircle2D(mean, 3.0, clusterSize);
		assignFitnessToPoints(points);
	}

	private void assignFitnessToPoints(Collection<EuclideanSpacePoint> points) {
		for (EuclideanSpacePoint p : points) {
			IndividualWithRealVectorPhenotype phenotype = new IndividualWithRealVectorPhenotype(
					new EuclideanSpacePhenotype(p));
			phenotype.setFitness(functor.getValue(p));
			getIndividuals().add(phenotype);
		}
	}

	@Override
	public Population getPopulation() {
		Population population = new FixedSizePopulation(getIndividuals()
				.toArray(new IndividualWithRealVectorPhenotype[0]));
		return population;
	}

	@Override
	public Individual[] extractBestIndividuals() {
		return getIndividuals().subList(0, 10).toArray(
				new IndividualWithRealVectorPhenotype[0]);
	}

	protected List<IndividualWithRealVectorPhenotype> getIndividuals() {
		return individuals;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("SequentialFakeEA created. clusterSize: " + clusterSize);
	}

	private void setIndividuals(
			List<IndividualWithRealVectorPhenotype> individuals) {
		this.individuals = individuals;
	}

	@Override
	public boolean checkTerminationCriterion() {
		// TODO Auto-generated method stub
		return false;
	}
}
