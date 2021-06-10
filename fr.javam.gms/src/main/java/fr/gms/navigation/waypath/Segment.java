package fr.gms.navigation.waypath;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.maths.Angles;
import fr.java.maths.algebra.Vectors;

// public record Segment(Point2D a, Point2D b) {
public class Segment {
	Point2D a, b;
	
	public Segment(Point2D _a, Point2D _b) {
		a = _a;
		b = _b;
	}

	public Point2D a() { return a; }
	public Point2D b() { return b; }

	public static boolean 	isValid(Segment _seg) {
		return _seg != null && (_seg.a != null && _seg.b != null) && _seg.a != _seg.b;
	}

	public double 			length() {
		return Math.sqrt( (b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY()));
	}

	public Vector2D 		normal(boolean _left) {
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();

		return _left ? 
				Vectors.of(- dy,   dx)
				:
				Vectors.of(  dy, - dx);
	}
	public double 			heading() {
		double A = b.getY() - a.getY();
		double O = b.getX() - a.getX();
		
		double H = A == 0 && O == 0 ? 0 : (double) (90 - Angles.Radian2Degree(Math.atan2(A, O)));

		return H;
	}

}
