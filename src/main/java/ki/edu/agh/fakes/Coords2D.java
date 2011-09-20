package ki.edu.agh.fakes;

public class Coords2D {
	private double x;
	private double y;
	private double stdDev;

	public Coords2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coords2D(double x, double y, double stdDev) {
		this.x = x;
		this.y = y;
		this.stdDev = stdDev;
	}

	public double getStdDev() {
		return stdDev;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
