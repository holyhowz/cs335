package convexHull;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class Xsortdouble implements Comparator<Point2D> {
	public Xsortdouble(){
		return;
	}
	@Override
	public int compare(Point2D arg0, Point2D arg1) {
		if(arg0.getX() == arg1.getX()){
			return 0;
		}else if(arg0.getX() < arg1.getX()){
			return -1;
		} else {
			return 1;
		}
	}
	public boolean equals(Point2D arg0, Point2D arg1){
		return arg0 == arg1 || (arg0.getX() == arg1.getX());
	}
}
