package ki.edu.agh.problem;

import java.util.List;

public class Domain {
	private int spaceDimension;
	private List<Interval> multidimensionalCube;

	public int getSpaceDimension() {
		return spaceDimension;
	}

	public List<Interval> getMultidimensionalCube() {
		return multidimensionalCube;
	}

	public void setMultidimensionalCube(List<Interval> multidimensionalCube) {
		this.multidimensionalCube = multidimensionalCube;
		this.spaceDimension = multidimensionalCube.size();
	}

	public Interval getInterval(int i) {
		return multidimensionalCube.get(i);
	}

}
