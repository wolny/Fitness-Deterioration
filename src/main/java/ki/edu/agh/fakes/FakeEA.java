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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FakeEA extends AbstractEvolutionaryAlgorithm implements
		InitializingBean {

	private static final Logger logger = Logger.getLogger(FakeEA.class);
	private static final int EXEC1 = 1;
	private static final int EXEC2 = 2;
	private static final int FIN = 3;

	private int clusterSize;
	private double standardDev;
	private List<Coords2D> clusterCenters;
	private Functor functor;
	private int state = EXEC1;

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

	public double getStandardDev() {
		return standardDev;
	}

	public void setStandardDev(double standardDev) {
		this.standardDev = standardDev;
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

			switch (state) {
			case EXEC1:
				logger.info("FakeEA - first execution: generating Gaussian samples in basins of attraction");
				// generate Gaussian cluster
				for (Coords2D c : clusterCenters) {
					EuclideanSpacePoint mean = new EuclideanSpacePoint(
							new double[] { c.getX(), c.getY() });
					Collection<EuclideanSpacePoint> points = PointGenerator
							.generateGaussianPoints2D(mean, standardDev,
									clusterSize);
					assignFitnessToPoints(points);
				}
				state = EXEC2;

				PrintUtils.writePopulation("fakePopulation1", getPopulation());
				break;

			case EXEC2:
				logger.info("FakeEA - second execution: generate uniformly distributed noise");
				// generate random cluster
				for (Coords2D c : clusterCenters) {
					EuclideanSpacePoint mean = new EuclideanSpacePoint(
							new double[] { c.getX(), c.getY() });
					Collection<EuclideanSpacePoint> points = PointGenerator
							.generateUniformCircle2D(mean, 8 * standardDev,
									clusterSize);
					assignFitnessToPoints(points);
				}
				state = FIN;

				PrintUtils.writePopulation("fakePopulation2", getPopulation());
				break;

			default:
				logger.warn("Execution of FakeEA is performed for the third time");
				break;
			}
		} catch (Exception e) {
			logger.error("FakeEA execution error", e);
		}

		return null;
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
		logger.debug("FakeEA created. clusterSize: " + clusterSize
				+ ", standardDev: " + standardDev + ", clusterCenters: "
				+ clusterCenters);
	}

	private void setIndividuals(
			List<IndividualWithRealVectorPhenotype> individuals) {
		this.individuals = individuals;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		FakeEA fakeEA = applicationContext.getBean(FakeEA.class);
		fakeEA.execute();
		fakeEA.execute();
	}

	@Override
	public boolean checkTerminationCriterion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Population replaceNotFeasible(Population pop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetStopCriterion() {
		// TODO Auto-generated method stub
		
	}
}
