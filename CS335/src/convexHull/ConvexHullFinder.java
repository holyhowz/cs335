package convexHull;

import java.awt.geom.Point2D;
import java.util.List;

public interface ConvexHullFinder {
	public abstract List<Point2D> computeHull(List<Point2D> thePoints);
}
