package fr.gms.sdk.navigation.waypath;

import java.util.Collection;

import fr.gms.navigation.waypath.WayPath;
import fr.java.math.geometry.plane.Point2D;

public final class WayPaths {

	public static WayPath.Single newSimplePath(Point2D... _pts) {
		return new WayPathSimple(_pts, false);
	}
	public static WayPath.Single newSimplePath(Collection<Point2D> _pts) {
		return new WayPathSimple(_pts, false);
	}
	public static WayPath.Single newSimplePath(boolean _loop, Point2D... _pts) {
		return new WayPathSimple(_pts, _loop);
	}
	public static WayPath.Single newSimplePath(boolean _loop, Collection<Point2D> _pts) {
		return new WayPathSimple(_pts, _loop);
	}
	public static WayPath.Single newSimpleLoop(Point2D... _pts) {
		return new WayPathSimple(_pts, true);
	}
	public static WayPath.Single newSimpleLoop(Collection<Point2D> _pts) {
		return new WayPathSimple(_pts, true);
	}
	public static WayPath.Single newSimpleTrip(Point2D... _pts) {
		return new WayPathSimple(_pts, false);
	}
	public static WayPath.Single newSimpleTrip(Collection<Point2D> _pts) {
		return new WayPathSimple(_pts, false);
	}

	public static WayPath.Multi newDualPath(Point2D[] _right, Point2D[] _left) {
		return new WayPathDouble(_right, _left, false);
	}
	public static WayPath.Multi newDualPath(Collection<Point2D> _right, Collection<Point2D> _left) {
		return new WayPathDouble(_right, _left, false);
	}
	public static WayPath.Multi newDualPath(boolean _loop, Point2D[] _right, Point2D[] _left) {
		return new WayPathDouble(_right, _left, _loop);
	}
	public static WayPath.Multi newDualPath(boolean _loop, Collection<Point2D> _right, Collection<Point2D> _left) {
		return new WayPathDouble(_right, _left, _loop);
	}
	public static WayPath.Multi newDualLoop(Point2D[] _right, Point2D[] _left) {
		return new WayPathDouble(_right, _left, true);
	}
	public static WayPath.Multi newDualLoop(Collection<Point2D> _right, Collection<Point2D> _left) {
		return new WayPathDouble(_right, _left, true);
	}
	public static WayPath.Multi newDualTrip(Point2D[] _right, Point2D[] _left) {
		return new WayPathDouble(_right, _left, false);
	}
	public static WayPath.Multi newDualTrip(Collection<Point2D> _right, Collection<Point2D> _left) {
		return new WayPathDouble(_right, _left, false);
	}

}
