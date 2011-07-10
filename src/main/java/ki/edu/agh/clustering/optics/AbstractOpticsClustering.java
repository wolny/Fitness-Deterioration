package ki.edu.agh.clustering.optics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import ki.edu.agh.clustering.ClusteringAlgorithm;
import ki.edu.agh.point.EuclideanSpacePoint;
import ki.edu.agh.point.MetricSpacePoint;
import ki.edu.agh.print.PrintUtils;

public abstract class AbstractOpticsClustering<T extends MetricSpacePoint>
		implements ClusteringAlgorithm<T> {

	protected static final Logger logger = Logger
			.getLogger(AbstractOpticsClustering.class);
	private double epsilon;
	private int minPoints;

	/**
	 * list of opticsClusterPoints to be clustered, more precisely we want to
	 * find augmented order (optics order) of this data set which contains
	 * sufficient information for further cluster extraction
	 * 
	 */
	private List<OpticsClusterPoint<T>> setOfObjects;

	/**
	 * optics ordering of initial data set
	 */
	private List<OpticsClusterPoint<T>> opticsOrdering;

	/**
	 * stores opticsClusterPoints sorted by their reachabilityDistance to the
	 * closest core object from which they have been directly density-reachable
	 */
	private PriorityQueue<OpticsClusterPoint<T>> orderedSeeds;

	public AbstractOpticsClustering(OpticsParamteres opticsParameters) {
		setEpsilon(opticsParameters.getEpsilon());
		setMinPoints(opticsParameters.getMinPoints());

		// for reachability plot
		OpticsClusterPoint.UNDEFINED = 1.1 * getEpsilon();
	}

	public List<OpticsClusterPoint<T>> getOpticsOrdering() {
		return opticsOrdering;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public List<OpticsClusterPoint<T>> getSetOfObjects() {
		return setOfObjects;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}

	/**
	 * wraps opticsCluserPoint and its distance to the arbitrarily chosen
	 * opticsClusterPoint
	 * 
	 * @author slo
	 * 
	 * @param <R>
	 */
	public static class PointDist<R extends MetricSpacePoint> {
		private OpticsClusterPoint<R> opticsPoint;
		private double distance;

		public PointDist(OpticsClusterPoint<R> opticsPoint, double distance) {
			this.opticsPoint = opticsPoint;
			this.distance = distance;
		}

		public OpticsClusterPoint<R> getOpticsPoint() {
			return opticsPoint;
		}

		public double getDistance() {
			return distance;
		}

		@Override
		public String toString() {
			return "distance: " + distance;
		}
	}

	/**
	 * returns points and their distance {@see PointDist} which lie in the
	 * epsion-neighbor of a given point
	 * 
	 * @param point
	 * @param epsilon
	 * @return
	 */
	protected List<PointDist<T>> getEpsilonNeighbor(
			OpticsClusterPoint<T> point, double epsilon) {

		List<PointDist<T>> result = new ArrayList<PointDist<T>>();

		for (OpticsClusterPoint<T> p : getSetOfObjects()) {
			double distance = point.getDistance(p);
			if (distance <= epsilon) {
				result.add(new PointDist<T>(p, distance));
			}
		}

		return result;
	}

	/**
	 * computes the core-distance of a given point based on its neighbors and
	 * minPts value
	 * 
	 * @param point
	 * @param neighbors
	 * @return core-distance or UNDEFINED if the core-distance cannot be defined
	 *         for the point
	 */
	protected double findCoreDistance(OpticsClusterPoint<T> point,
			List<PointDist<T>> neighbors) {

		if (neighbors.size() < getMinPoints()) {
			return OpticsClusterPoint.UNDEFINED;
		}

		// TODO: make it faster by using median algorithm (k-th element in a
		// collection)
		Collections.sort(neighbors, new Comparator<PointDist<T>>() {
			@Override
			public int compare(PointDist<T> o1, PointDist<T> o2) {
				return o1.getDistance() < o2.getDistance() ? -1 : 1;
			}
		});

		// return distance from MinPts-nearest neighbor (core distance)
		return neighbors.get(getMinPoints() - 1).getDistance();
	}

	/**
	 * sets set of objects to be clustered, creates orderedSeeds queue and
	 * crates OPTICS ordering
	 */
	@Override
	public void setDataSet(Collection<T> dataSet) {
		logger.debug("initilizing OPTICS algorithm ...");

		setOfObjects = new ArrayList<OpticsClusterPoint<T>>(dataSet.size());
		for (T object : dataSet) {
			setOfObjects.add(new OpticsClusterPoint<T>(object));
		}

		opticsOrdering = new ArrayList<OpticsClusterPoint<T>>(
				setOfObjects.size());

		logger.debug("initializing OrderedSeeds ...");

		orderedSeeds = new PriorityQueue<OpticsClusterPoint<T>>(
				setOfObjects.size(), new Comparator<OpticsClusterPoint<T>>() {
					@Override
					public int compare(OpticsClusterPoint<T> o1,
							OpticsClusterPoint<T> o2) {
						if (o1.getReachabilityDistance() < o2
								.getReachabilityDistance()) {
							return -1;
						}
						if (o1.getReachabilityDistance() > o2
								.getReachabilityDistance()) {
							return 1;
						}
						return 0;
					}
				});

		performOPTICS();

		if (getSetOfObjects().size() != getOpticsOrdering().size()) {
			throw new RuntimeException(
					"The list of ordered points is not complete");
		}

		// reachabilityPlot
		reachabilityPlot();
	}

	private void reachabilityPlot() {
		List<EuclideanSpacePoint> reachabilityPlot = new ArrayList<EuclideanSpacePoint>(
				getOpticsOrdering().size());
		double i = 1.;
		for (OpticsClusterPoint<T> esp : getOpticsOrdering()) {
			double reachabilityDistance = esp.getReachabilityDistance();
			reachabilityPlot.add(new EuclideanSpacePoint(new double[] { i++,
					reachabilityDistance }));
		}
		try {
			PrintUtils.writePoints("reachabilityPlot", reachabilityPlot);
		} catch (IOException e) {
			logger.warn("reachabilityPlot failed", e);
		}
	}

	protected void performOPTICS() {
		logger.debug("creating OPTICS ordering ...");
		for (OpticsClusterPoint<T> oPoint : getSetOfObjects()) {
			if (!oPoint.isProcessed()) {
				expandClusterOrder(oPoint);
			}
		}
	}

	private void expandClusterOrder(OpticsClusterPoint<T> oPoint) {
		oPoint.setProcessed(true);
		oPoint.setReachabilityDistance(OpticsClusterPoint.UNDEFINED);
		List<PointDist<T>> neighbors = getEpsilonNeighbor(oPoint, getEpsilon());
		double coreDistance = findCoreDistance(oPoint, neighbors);
		oPoint.setCoreDistance(coreDistance);
		getOpticsOrdering().add(oPoint);

		if (coreDistance != OpticsClusterPoint.UNDEFINED) {
			updateOrderedSeeds(oPoint, neighbors);
			while (!orderedSeeds.isEmpty()) {
				OpticsClusterPoint<T> currentObject = orderedSeeds.remove();
				neighbors = getEpsilonNeighbor(currentObject, getEpsilon());
				currentObject.setProcessed(true);
				coreDistance = findCoreDistance(currentObject, neighbors);
				currentObject.setCoreDistance(coreDistance);
				getOpticsOrdering().add(currentObject);

				if (coreDistance != OpticsClusterPoint.UNDEFINED) {
					updateOrderedSeeds(currentObject, neighbors);
				}

			}
		}
	}

	private void updateOrderedSeeds(OpticsClusterPoint<T> point,
			List<PointDist<T>> neighbors) {
		double coreDistance = point.getCoreDistance();
		for (PointDist<T> neighbor : neighbors) {
			OpticsClusterPoint<T> oPoint = neighbor.getOpticsPoint();
			if (!oPoint.isProcessed()) {
				double reachabilityDistance = Math.max(coreDistance,
						neighbor.getDistance());
				if (oPoint.getReachabilityDistance() == OpticsClusterPoint.UNDEFINED) {
					oPoint.setReachabilityDistance(reachabilityDistance);
					orderedSeeds.offer(oPoint);
				} else { // oPoint already in orderedSeeds
					if (reachabilityDistance < oPoint.getReachabilityDistance()) {
						orderedSeeds.remove(oPoint);
						oPoint.setReachabilityDistance(reachabilityDistance);
						orderedSeeds.offer(oPoint);
					}
				}
			}
		}
	}
}
