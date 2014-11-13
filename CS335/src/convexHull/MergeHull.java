package convexHull;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeHull implements ConvexHullFinder {
	private Comparator<Point2D> compare;

	public static void main(String[] args) {
		MergeHull merger = new MergeHull();
		List<Point2D> testPoints = new ArrayList<Point2D>();

		testPoints.add(new Point2D.Double(10, 10));
		testPoints.add(new Point2D.Double(15, 10));
		testPoints.add(new Point2D.Double(10, 20));

		Line2D testLine = new Line2D.Double(testPoints.get(0),
				testPoints.get(1));

		System.out.println(testLine.relativeCCW(testPoints.get(2)));

		merger.sortCCW(testPoints);
	}

	public MergeHull() {
		compare = new Xsortdouble();
	}

	@Override
	public List<Point2D> computeHull(List<Point2D> thePoints) {
		Collections.sort(thePoints, compare);
		return recursiveMergeHull(thePoints);
	}

	private List<Point2D> recursiveMergeHull(List<Point2D> pointsAB) {
		if (pointsAB.size() > 3) {
			List<Point2D> pointsLeft = pointsAB.subList(0, pointsAB.size() / 2);
			List<Point2D> pointsRight = pointsAB.subList(pointsAB.size() / 2,
					pointsAB.size());
			pointsLeft = recursiveMergeHull(pointsLeft);
			pointsRight = recursiveMergeHull(pointsRight);
			int[] toptangent = findTangent(pointsLeft, pointsRight);
			flipPoints(pointsLeft, pointsRight);
			int[] bottangent = findTangent(pointsLeft, pointsRight);
			unflipPoints(pointsLeft, pointsRight);
			List<Point2D> done = merge(pointsLeft, pointsRight, toptangent,
					bottangent);
			return done;
		} else
			return sortCCW(pointsAB);
	}

	private List<Point2D> merge(List<Point2D> pointsLeft,
			List<Point2D> pointsRight, int[] toptangent, int[] bottangent) {
		List<Point2D> merged = new ArrayList<Point2D>();
		int current = toptangent[0];
		Point2D tester = new Point2D.Double();
		while (current != bottangent[0]) {
			merged.add(pointsLeft.get(current));
			tester.setLocation(pointsLeft.get(current));
			current = inc(pointsLeft, current);
		}
		current = bottangent[1];
		while (current != toptangent[1]) {
			merged.add(pointsRight.get(current));
			tester.setLocation(pointsRight.get(current));
			current = inc(pointsRight, current);
		}
		merged.add(pointsRight.get(toptangent[1]));
		return merged;
	}

	private int[] findTangent(List<Point2D> pointsLeft,
			List<Point2D> pointsRight) {
		int A = findRightmost(pointsLeft);
		int B = findLeftmost(pointsRight);
		Line2D tangent = new Line2D.Double(pointsLeft.get(A),
				pointsRight.get(B));
		while (isntBestEver(tangent, pointsLeft, pointsRight)) {
			while (isntBestTangent(tangent, pointsLeft)) {
					A=inc(pointsLeft,A);
				tangent = new Line2D.Double(pointsLeft.get(A),
						pointsRight.get(B));
			}
			while (isntBestTangent(tangent, pointsRight)) {
				B=inc(pointsRight,B);
				tangent = new Line2D.Double(pointsLeft.get(A),
						pointsRight.get(B));
			}
		}
		return new int[] { A, B };
	}

	private void flipPoints(List<Point2D> pointsLeft, List<Point2D> pointsRight) {
		for (Point2D point : pointsLeft) {
			point.setLocation(point.getX(), 1000 - point.getY());
		}
		for (Point2D point : pointsRight) {
			point.setLocation(point.getX(), 1000 - point.getY());
		}
	}
	
	private void unflipPoints(List<Point2D> pointsLeft,
			List<Point2D> pointsRight) {
		for (Point2D point : pointsLeft) {
			point.setLocation(point.getX(), -point.getY() + 1000);
		}
		for (Point2D point : pointsRight) {
			point.setLocation(point.getX(), -point.getY() + 1000);
		}
	}

	private boolean isntBestTangent(Line2D tangent, List<Point2D> points) {
		return isAbove(tangent,
				points.get(inc(points, points.indexOf(tangent.getP1()))))
				|| isAbove(tangent, points.get(dec(points,
						points.indexOf(tangent.getP1()))));
	}

	private boolean isAbove(Line2D tangent, Point2D test) {
		return (tangent.relativeCCW(test) == 1);
	}

	private boolean isntBestEver(Line2D tangent, List<Point2D> pointsLeft,
			List<Point2D> pointsRight) {
		return (isntBestTangent(tangent, pointsLeft) || isntBestTangent(
				tangent, pointsRight));
	}

	// Seriously, only use this on groups of three points.
	private List<Point2D> sortCCW(List<Point2D> thePoints) {
		if (thePoints.size() < 3) {
			Collections.sort(thePoints, compare);
			return thePoints;
		} else if (thePoints.size() > 3) {
			return thePoints;
		}
		List<Point2D> sortedCCW = new ArrayList<Point2D>();
		Collections.sort(thePoints, compare);
		sortedCCW.add(thePoints.get(0));
		Line2D theDecider = new Line2D.Double(thePoints.get(0),
				thePoints.get(2));
		if (theDecider.relativeCCW(thePoints.get(1)) == -1) {
			sortedCCW.add(thePoints.get(1));
			sortedCCW.add(thePoints.get(2));
		} else {
			sortedCCW.add(thePoints.get(2));
			sortedCCW.add(thePoints.get(1));
		}
		return sortedCCW;
	}

	private int findRightmost(List<Point2D> points) {
		Point2D max = new Point2D.Double();
		for (Point2D current : points) {
			if (current.getX() > max.getX()) {
				max = current;
			}
		}
		return points.indexOf(max);
	}

	private int findLeftmost(List<Point2D> points) {
		Point2D min = new Point2D.Double();
		for (Point2D current : points) {
			if (current.getX() > min.getX()) {
				min = current;
			}
		}
		return points.indexOf(min);
	}

	private int inc(List theList, int i) {
		if (i >= theList.size() - 1) {
			return 0;
		} else
			return i + 1;
	}

	private int dec(List theList, int i) {
		if (i <= 0) {
			return theList.size() - 1;
		} else {
			return i - 1;
		}
	}
}
