package ki.edu.agh.print;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ki.edu.agh.population.Individual;
import ki.edu.agh.population.Population;
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

	public static void main(String[] args) throws IOException {
		Population population = Utils.createRandomUniModalPopulation(2);
		writePopulation("population", population);

		double x = 0.11303800698725097;
		double y = 0.19836672073738826;
		// 1.8984167202331006
		double exp = 2. * Math.exp(-(x * x + y * y));
		System.out.println(exp);
	}
}
