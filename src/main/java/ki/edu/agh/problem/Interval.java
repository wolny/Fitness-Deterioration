package ki.edu.agh.problem;

public class Interval {

	private double intervalStart;
	private double intervalStop;

	public Interval(double intervalStart, double intervalStop) {
		this.intervalStart = intervalStart;
		this.intervalStop = intervalStop;
	}

	public double getIntervalStart() {
		return intervalStart;
	}

	public double getIntervalStop() {
		return intervalStop;
	}

	public boolean isInside(double x) {
		return intervalStart < x && x < intervalStop;
	}

	@Override
	public String toString() {
		return "[" + intervalStart + ", " + intervalStop + "]";
	}
}
