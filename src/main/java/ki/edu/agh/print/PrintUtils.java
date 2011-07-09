package ki.edu.agh.print;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import ki.edu.agh.deterioration.PointWithFitness;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
import ki.edu.agh.problem.ProblemDomain;
import ki.edu.agh.statistics.PointGenerator;
import ki.edu.agh.statistics.Utils;

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
		List<PointWithFitness> pwfs = PointGenerator.createPointsWithFitnessForProblemDomain(
				problem, numOfNodes);
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
		Population population = Utils.createRandomUniModalPopulation(2, 10);
		writePopulation("population", population);

		double x = 0.11303800698725097;
		double y = 0.19836672073738826;
		// 1.8984167202331006
		double exp = 2. * Math.exp(-(x * x + y * y));
		System.out.println(exp);
	}
}
