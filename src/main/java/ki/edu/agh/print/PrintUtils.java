package ki.edu.agh.print;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.fintess.FitnessFunction;
import ki.edu.agh.fintess.StandardFitnessFunction;
import ki.edu.agh.functors.Functor;
import ki.edu.agh.functors.LangermannFunction;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.Domain;
import ki.edu.agh.problem.Interval;
import ki.edu.agh.problem.MultimodalRealSpaceProblem;
import ki.edu.agh.problem.ProblemDomain;
import ki.edu.agh.statistics.PointGenerator;

public class PrintUtils {
	public static final String DIAGRAM_DIR = "diagrams";

	public static void writePopulation(String fileName, Population population)
			throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(DIAGRAM_DIR + "/" + fileName));
		try {
			StringBuilder sb = new StringBuilder();
			for (Individual individual : population) {
				sb.append(individual + "\n");
			}
			byte[] bytes = sb.toString().getBytes();
			out.write(bytes);
		} finally {
			out.close();
		}
	}

	public static void writePoints(String fileName,
			Collection<EuclideanSpacePoint> points) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(DIAGRAM_DIR + "/" + fileName));
		try {
			StringBuilder sb = new StringBuilder();
			for (EuclideanSpacePoint point : points) {
				sb.append(point + "\n");
			}
			byte[] bytes = sb.toString().getBytes();
			out.write(bytes);
		} finally {
			out.close();
		}
	}

	public static void writeProblemPoints(String fileName,
			ProblemDomain problem, int numOfNodes) throws IOException {
		List<PointWithFitness> pwfs = PointGenerator
				.createPointsWithFitnessForProblemDomain(problem, numOfNodes);
		writePointsWithFitness(fileName, pwfs);
	}

	public static void writePointsWithFitness(String fileName,
			List<PointWithFitness> points) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(DIAGRAM_DIR + "/" + fileName));
		try {
			StringBuilder sb = new StringBuilder();
			double firstCoord = points.get(0).getPoint().getCoordinate(0);
			for (PointWithFitness point : points) {
				double fc = point.getPoint().getCoordinate(0);
				if (fc != firstCoord) {
					sb.append("\n");
					firstCoord = fc;
				}
				sb.append(point + "\n");
			}
			byte[] bytes = sb.toString().getBytes();
			out.write(bytes);
		} finally {
			out.close();
		}
	}

	public static void main(String[] args) throws IOException {
		Functor functor = new LangermannFunction();
		FitnessFunction fitnessFunction = new StandardFitnessFunction(functor);
		Domain domain = new Domain();
		Interval i1 = new Interval(0, 4);
		Interval i2 = new Interval(-1, 3);
		domain.setMultidimensionalCube(Arrays.asList(i1, i2));
		ProblemDomain problem = new MultimodalRealSpaceProblem(domain,
				fitnessFunction);
		writeProblemPoints("langermann", problem, 400);
	}
}
