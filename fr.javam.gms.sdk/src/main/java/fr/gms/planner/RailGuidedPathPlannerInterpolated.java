package fr.gms.planner;

import java.util.Arrays;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.gis.GisPath;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.service.PathPlanner;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.exceptions.WhatTheFuckException;
import fr.java.math.algebra.NumberVector.Norm;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.interpolation.CoordinateInterpolator;
import fr.java.maths.algebra.Vectors;

public class RailGuidedPathPlannerInterpolated implements PathPlanner {
	Gis.Path		reference;

	int 			nbWayPoints    = 100;
	double			interWayPoints = 0.33;
	
	CoordinateInterpolator.TwoDims interpolator;

	public RailGuidedPathPlannerInterpolated() {
		this(null, 100, 0.33);
	}
	public RailGuidedPathPlannerInterpolated(int _nbWayPoints, double _interWayPoints) {
		this(null, _nbWayPoints, _interWayPoints);
	}
	public RailGuidedPathPlannerInterpolated(Gis.Path _trajectory) {
		this(_trajectory, 100, 0.33);
	}
	public RailGuidedPathPlannerInterpolated(Gis.Path _trajectory, int _nbWayPoints, double _interWayPoints) {
		super();
		reference      = _trajectory;

		nbWayPoints    = _nbWayPoints;
		interWayPoints = _interWayPoints;
	}

	public void				setReference(Gis.Path _path) {
		reference = _path;
	}
	public Gis.Path			getReference() {
		return reference;
	}

	public void				setWayPointCount(int _nbWayPoints) {
		nbWayPoints = _nbWayPoints;
	}
	public int				getWayPointCount() {
		return nbWayPoints;
	}

	public void				setWayPointInterDistance(double _interWayPoints) {
		interWayPoints = _interWayPoints;
	}
	public double			getWayPointInterDistance() {
		return interWayPoints;
	}

	@Override
	public Gis.Path 		compute(EgoVehicle _ego) {
		if(_ego == null || reference == null)
			throw new IllegalArgumentException("ego is null !!!");

		GeoCoordinate ego = _ego.getPosition();

		List<GeoCoordinate> forwardPath  = generateForwardVectorFromPath  (ego, reference, nbWayPoints, interWayPoints);

		return new GisPath(IDs.newUTF8(_ego.getId() + "-path"), forwardPath);
	}

	List<GeoCoordinate> 	generateForwardVectorFromPath(GeoCoordinate _pos, Gis.Path _path, int _nbDots, double _interDots) {
//		if(reference.isLoop())
			return generateForwardVectorFromLoop(_pos, _path, _nbDots, _interDots);

//		return generateForwardVectorFromCurve(_pos, _path, _nbDots, _interDots, true);
	}
	List<GeoCoordinate> 	generateBackwardVectorFromPath(GeoCoordinate _pos, Gis.Path _path, int _nbDots, double _interDots) {
/*
		if(reference.isLoop())
			return generateBackwardVectorFromLoop(_pos, _path, _nbDots);

		return generateBackwardVectorFromCurve(_pos, _path, _nbDots, true);
*/
		return null;
	}

	List<GeoCoordinate> 	generateForwardVectorFromLoop(GeoCoordinate _pos, Gis.Path _path, int _nbDots, double _interDots) {
		List<GeoCoordinate> path      = _path.getGeometry();
		GeoCoordinate[]     trajPath  = new GeoCoordinate[_nbDots];
		int                 trajBegin = getClosestIndex(_pos, _path), 
			                trajEnd   = path.size();
		int                 trajCount = 0;

		if(trajBegin == -1)
			throw new WhatTheFuckException();

		// We begin our journey where we are
		trajPath[0] = _pos; trajCount = 1;

		int           idBeg  =  trajBegin, 
			          idEnd  = (trajBegin + 1) % (trajEnd);

		GeoCoordinate cur    = trajPath[0],
					  beg    = path.get(idBeg), 
					  end    = null;

		while(trajCount < _nbDots) { System.out.println("idBeg = " + idBeg + "\t" + trajEnd + "\t" + trajCount);
			cur    = trajPath[trajCount - 1];
			beg    = path.get(idBeg);
			end    = path.get(idEnd);

			double 
			segLength = GeoCoordinates.computeDistanceUTM(beg, end);

			if(segLength >= _interDots) {
				double N = segLength / _interDots;
				int    i = 0, 
					   n = (int) Math.floor(N);

				while(trajCount < _nbDots && i < n) {
					trajPath[trajCount++] = interpolateLinearlyWithDistance(cur, end, _interDots);
					cur                   = trajPath[trajCount - 1];

					i++;
				}

			}

			idBeg = (idBeg+1) % (trajEnd);
			idEnd = (idEnd+1) % (trajEnd);
		}

		return Arrays.asList(trajPath);
		
	}
	List<GeoCoordinate> 	generateForwardVectorFromCurve(GeoCoordinate _pos, Gis.Path _path, int _nbDots, double _interDots, boolean _fillWithLast) {
		List<GeoCoordinate> path = _path.getGeometry();

		int pathBegin    = getClosestIndex(_pos, _path), 
			pathEnd      = path.size() - 1;

		if(pathBegin == -1)
			return null;
		
		int coordCounter = 0;
		int idBeg = pathBegin;

		GeoCoordinate[] forwardTraj = new GeoCoordinate[_nbDots];
		while(coordCounter < _nbDots && idBeg < pathEnd - pathBegin - 1) {

			GeoCoordinate beg = path.get(idBeg), end = path.get(idBeg+1);
			double length = GeoCoordinates.computeDistanceUTM(beg, end);
			if(length > _interDots) {
				forwardTraj[coordCounter++] = beg.clone();

				double N = length / _interDots;
				int i = 0, n = (int) Math.ceil(N);
				while(coordCounter < _nbDots && i < n)
					forwardTraj[coordCounter++] = interpolateLinearlyWithDistance(forwardTraj[coordCounter-2], beg, end, _interDots);

				if(coordCounter < _nbDots && n < N)
					forwardTraj[coordCounter++] = end.clone();

			} else {
				forwardTraj[coordCounter++] = beg.clone();
				if(coordCounter < _nbDots)
					forwardTraj[coordCounter++] = end.clone();
			}

			idBeg++;
		}

		if(_fillWithLast)
			for(int j = idBeg; j < _nbDots; ++j)
				forwardTraj[j] = path.get(pathEnd - 1).clone();
/*
		Point2D[]       interpTraj     = Arrays.asList(forwardTraj).stream().map(gc -> Points.of(gc.asUTM().getX(), gc.asUTM().getY())).toArray(s -> new Point2D[s]);
		Point2D[]       interpolated   = new ParametricInterpolator2D(interpTraj).interpolate(_nbDots);
		GeoCoordinate[] forwardTrajNew = Arrays.asList(interpolated).stream().map(pt -> GeoCoordinates.newUTM(pt.getX(), pt.getY())).toArray(s -> new GeoCoordinate[s]);
*/
		return Arrays.asList(forwardTraj);
	}

	int 					getClosestIndex(GeoCoordinate _pos, Gis.Path _path) {
		double dCandidate = Double.MAX_VALUE;
		int    iCandidate = -1;

		for(int i = 0; i < _path.getGeometry().size(); ++i) {
			GeoCoordinate candidate = _path.getGeometry().get(i);
			double        distance  = GeoCoordinates.computeDistanceUTM(_pos, candidate);
			if(distance < dCandidate) {
				dCandidate = distance;
				iCandidate = i;
			}
		}

		return iCandidate;
	}

	GeoCoordinate 			interpolateLinearlyWithDistance(GeoCoordinate _from, GeoCoordinate _to, double _distanceFrom) {
		Point2D from = _from.asUTM().as2D(), to = _to.asUTM().as2D();

		double alpha2 = _distanceFrom / Vectors.delta(to, from).norm(Norm.EuclidianSquare);
		double alpha  = Math.sqrt(alpha2);

		double x = alpha * to.getX() + (1 - alpha) * from.getX();
		double y = alpha * to.getY() + (1 - alpha) * from.getY();
		
		return GeoCoordinates.newUTM(x, y);
	}
	GeoCoordinate 			interpolateLinearlyWithDistance(GeoCoordinate _from, GeoCoordinate _to_a, GeoCoordinate _to_b, double _distanceFrom) {
		Point2D from = _from.asUTM().as2D(), 
				to_a = _to_a.asUTM().as2D(),
				to_b = _to_b.asUTM().as2D();

		//  AH  = k AB
		// |PH| = d
		double xp = from.getX(),
			   xa = to_a.getX(),
			   xb = to_b.getX(),
			   yp = from.getY(),
			   ya = to_a.getY(),
			   yb = to_b.getY(),
			   d  = _distanceFrom;
		double Xa = xb - xa,
			   Xb = xa - xp,
			   Ya = yb - ya,
			   Yb = ya - yp;
		double A  = Xa*Xa+Ya*Ya,
			   B  = 2*Xa*Xb+2*Ya*Yb,
			   C  = Xb*Xb+Yb*Yb-d*d;
		// tq: A² . k + B . k + C = 0

		double delta = B*B - 4 * A * C;
		double k = 0;

		if(delta == 0)
			k = - B / (2 * A);
		else if(delta > 0){
			double k1 = - (B + Math.sqrt(delta)) / (2 * A);
			double k2 = - (B - Math.sqrt(delta)) / (2 * A);
		
			k = k1 > 0 ? k1 : k2 > 0 ? k2 : Double.NaN;
		} else
			throw new IllegalArgumentException();

		double x = k * xb + (1d - k) * xa;
		double y = k * yb + (1d - k) * ya;
		
		System.out.println("k= " + k);
		return GeoCoordinates.newUTM(x, y).asWGS84();
	}

	/*
	 * Assume that distance between _from and line (_to_a, _to_b) is less than _distanceFrom
	 */
	double 					getLinearInterpolationFactor(GeoCoordinate _from, GeoCoordinate _to_a, GeoCoordinate _to_b, double _distanceFrom) {
		Point2D from = _from.asUTM().as2D(), 
				to_a = _to_a.asUTM().as2D(),
				to_b = _to_b.asUTM().as2D();

		//  AH  = k AB
		// |PH| = d
		double xp = from.getX(),
			   xa = to_a.getX(),
			   xb = to_b.getX(),
			   yp = from.getY(),
			   ya = to_a.getY(),
			   yb = to_b.getY(),
			   d  = _distanceFrom;
		double Xa = xb - xa,
			   Xb = xa - xp,
			   Ya = yb - ya,
			   Yb = ya - yp;
		double A  = Xa*Xa+Ya*Ya,
			   B  = 2*Xa*Xb+2*Ya*Yb,
			   C  = Xb*Xb+Yb*Yb-d*d;
		// tq: A² . k + B . k + C = 0

		double delta = B*B - 4 * A * C;
		double k = 0;

		if(delta == 0)
			k = - B / (2 * A);
		else {
			double k1 = - (B + Math.sqrt(delta)) / (2 * A);
			double k2 = - (B - Math.sqrt(delta)) / (2 * A);
		
			k = k1 > 0 ? k1 : k2 > 0 ? k2 : Double.NaN;
		}

		return k;
	}
	
}
