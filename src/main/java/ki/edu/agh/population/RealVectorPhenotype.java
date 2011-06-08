package ki.edu.agh.population;

public class RealVectorPhenotype implements Phenotype {

	private Point point;

	public RealVectorPhenotype(Point point) {
		this.point = point;
	}

	public RealVectorPhenotype() {
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public Point getPoint() {
		return point;
	}

}
