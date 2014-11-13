package convexHull;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuickHull implements ConvexHullFinder {
	private Comparator<Point2D> compare;

	public static void main(String[] args) {
//		QuickHull qh = new QuickHull();
//		List<Point2D> testPoints = new ArrayList<Point2D>();

//		testPoints.add(new Point2D.Double(0, 25.0));
//		testPoints.add(new Point2D.Double(50, 50));
//		testPoints.add(new Point2D.Double(0.0, 50.0));
//		testPoints.add(new Point2D.Double(25.0, 75.0));
//		testPoints.add(new Point2D.Double(25.0, 100.0));
//
//		testPoints.add(new Point2D.Double(50.0, 0.0));
//		testPoints.add(new Point2D.Double(51.0, 50.0));
//		testPoints.add(new Point2D.Double(50.0, 75.0));
//		testPoints.add(new Point2D.Double(50.0, 100.0));

		// testPoints.add(new Point2D.Double(75.0, 25.0));
		// testPoints.add(new Point2D.Double(75.0, 50.0));
		// testPoints.add(new Point2D.Double(75.0, 75.0));
		// testPoints.add(new Point2D.Double(75.0,100.0));

		// testPoints.add(new Point2D.Double(100.0, 25.0));
		// testPoints.add(new Point2D.Double(100.0, 50.0));
		// testPoints.add(new Point2D.Double(100.0, 75.0));
		// testPoints.add(new Point2D.Double(100.0,100.0));

//		System.out.println("Done:" + qh.computeHull(testPoints));
		// for(Point2D testPoint: testPoints){
		// System.out.println(testPoint.getX()+","+testPoint.getY() +" "+
		// testLine.relativeCCW(testPoint));
		// }
		new ConvexHullGUI();
	}

	public QuickHull() {
		compare = new Xsortdouble();
	}

	@Override
	public List<Point2D> computeHull(List<Point2D> thePoints) {
		Collections.sort(thePoints, compare);// Sort
		if(thePoints.size() <= 2){
			return thePoints;
		} else if (thePoints.size() == 3) {
			return sortCCW(thePoints);
		} else {
			// Define lineAB
			Line2D.Double lineAB = defineAB(thePoints);
//			Line2D.Double lineBA = new Line2D.Double(lineAB.getP2(),lineAB.getP1());
//			System.out.println("LineAB" + lineAB.getP1() + lineAB.getP2());
			// Split set of all points into top and bottom
			List<Point2D> topPoints = new ArrayList<Point2D>();
			List<Point2D> botPoints = new ArrayList<Point2D>();
			splitOnLine(thePoints, lineAB, topPoints, botPoints);
			// Recursive FunctionCalls
			List<Point2D> botHull = new ArrayList<Point2D>();
			botHull.add(lineAB.getP1());
			botHull.addAll(recursiveQuickHull(lineAB, botPoints));
			botHull.addAll(recursiveQuickHull(lineAB, topPoints));
			botHull.add(lineAB.getP2());
			// Combine Tophull and Bottomhull
			sortCCW(botHull);
			return botHull;
		}
	}

	private List<Point2D> recursiveQuickHull(Line2D lineAB,
			List<Point2D> pointsAB) {
		if (pointsAB.isEmpty() || pointsAB.size() == 1) {
			return pointsAB;
		} else if (pointsAB.size() == 2) {
			List<Point2D> theNewList = new ArrayList<Point2D>();
			theNewList.add(findFarthest(lineAB, pointsAB));
			if (!(pointsInside(
					new Line2D.Double(lineAB.getP1(), theNewList.get(0)),
					pointsAB).isEmpty())) {
				return pointsAB;
			} else {
				return theNewList;
			}
		}
		Point2D c = findFarthest(lineAB, pointsAB);
		Line2D lineAC = new Line2D.Double(lineAB.getP1(), c);
		// System.out.println(lineAC.getP1() +","+ lineAC.getP2());
		Line2D lineCB = new Line2D.Double(c, lineAB.getP2());
		// System.out.println(lineCB.getP1() +","+ lineCB.getP2());
		List<Point2D> pointsAC = pointsInside(lineAC, pointsAB);
		List<Point2D> pointsCB = pointsInside(lineCB, pointsAB);
		// System.out.println("AC:" + pointsAC);
		// System.out.println("BC:" + pointsCB);
		List<Point2D> hull = recursiveQuickHull(lineAC, pointsAC);
		hull.addAll(recursiveQuickHull(lineCB, pointsCB));
		Collections.sort(hull, compare);
		// System.out.println(hull);
		return hull;
	}

	private void splitOnLine(List<Point2D> thePoints, Line2D ab,
			List<Point2D> list1, List<Point2D> list2) {
		for (Point2D currentPoint : thePoints) {
			if (ab.relativeCCW(currentPoint) == 1) {
				list1.add(currentPoint);
			} else if (ab.relativeCCW(currentPoint) == -1) {
				list2.add(currentPoint);
			}
		}
	}

	private Line2D.Double defineAB(List<Point2D> PointsAB) {
		Line2D.Double lineAB = new Line2D.Double(PointsAB.get(0),
				PointsAB.get(PointsAB.size() - 1));
		return lineAB;
	}

	private Point2D findFarthest(Line2D lineAB, List<Point2D> pointsAB) {
		Point2D farthest = pointsAB.get(0);
		for (Point2D currentPoint : pointsAB) {
			if (lineAB.ptLineDist(currentPoint) > lineAB.ptLineDist(farthest)) {
				farthest = currentPoint;
			}
		}
		return farthest;
	}

	private List<Point2D> pointsInside(Line2D lineab, List<Point2D> points) {
		List<Point2D> pointsAbove = new ArrayList<Point2D>();
		for (Point2D currentPoint : points) {
			if (lineab.relativeCCW(currentPoint) != 1) {
				pointsAbove.add(currentPoint);
			}
		}
		return pointsAbove;
	}

	// Do Not Use w/ more than or less than three points.
	private List<Point2D> sortCCW(List<Point2D> thePoints) {
		List<Point2D> sortedCCW = new ArrayList<Point2D>();
		Collections.sort(thePoints, compare);
		System.out.println("1: " + thePoints);
		Line2D lineAB = defineAB(thePoints);
		for (Point2D current : thePoints) {
			if (lineAB.relativeCCW(thePoints.get(1)) == 1) {
				sortedCCW.add(current);
			}
		}
		Collections.reverse(thePoints);
		for (Point2D current : thePoints) {
			if (lineAB.relativeCCW(thePoints.get(1)) == -1) {
				sortedCCW.add(current);
			}
		}
		return sortedCCW;
	}
}
