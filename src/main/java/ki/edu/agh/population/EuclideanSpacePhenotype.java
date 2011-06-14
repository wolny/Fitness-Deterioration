package ki.edu.agh.population;

import ki.edu.agh.point.EuclideanSpacePoint;

public class EuclideanSpacePhenotype implements Phenotype {

	private EuclideanSpacePoint point;

	public EuclideanSpacePhenotype(EuclideanSpacePoint point) {
		this.point = point;
	}

	public EuclideanSpacePoint getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return point.toString();
	}
}
