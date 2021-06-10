package fr.gis.utils.tools.road;

import static fr.gis.api.Gis.Direction.BOTH;
import static fr.gis.api.Gis.Direction.DIRECT;
import static fr.gis.api.Gis.Direction.INDIRECT;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.utils.tools.AbstractMapToolBox;
import fr.java.maths.Angles;
import fr.java.maths.geometry.plane.shapes.SimpleEllipse2D;

public abstract class AbstractRoadToolBox extends AbstractMapToolBox implements RoadToolBox {
	static final double 	THETA_TEST = 135;

	protected AbstractRoadToolBox() {
		super();
	}

	@Override
	public final Collection<Road.Element> 		getSurroundingRoadElements	(GeoCoordinate _p, double _radius) {
		Set<Road.Element> 		roadElements 	= new HashSet<Road.Element>();
		SimpleEllipse2D  		influenceArea 	= new SimpleEllipse2D(_p.asUTM().as2D(), _radius, _radius);

		for(Road.Element roadElt : getAllRoadElements()) {
			for(GeoCoordinate pt : roadElt.getTops())
				if(	influenceArea.contains(pt.asUTM().as2D()) ) {
					roadElements.add(roadElt);
					break ;
				}
		}

		return roadElements;
	}
	@Override
	public final Collection<Road.TraficSign> 	getSurroundingTrafficSigns	(GeoCoordinate _p, double _radius) {
		Set<Road.TraficSign> 	trafficSigns 		 = new HashSet<Road.TraficSign>();
		SimpleEllipse2D 	 	influenceArea 		 = new SimpleEllipse2D(_p.asUTM().as2D(), _radius, _radius);

		for(Road.TraficSign trafficSign : getAllTrafficSigns()) {
			if( influenceArea.contains(trafficSign.getPosition().asUTM().as2D()) )
				trafficSigns.add(trafficSign);
		}

		return trafficSigns;
	}

	public final Set<Road.Element> 				getReachableElements(RoadCoordinate _p, double _r) {
		return buildInfluenceArea(_p, _r);
	}

	@Override
	public final Gis.Direction 					getDrivingWay(RoadCoordinate _p, Gis.Curve _e) {
		if(!_p.getRoadElement().equals(_e.getId()))
			throw new RuntimeException();

		double headSeg = GeoCoordinates.computeHeading(_e.getTops().get(0).asUTM(), _e.getTops().get(1).asUTM());
		int    colin   = Angles.colinear(_p.getHeading(), headSeg, 50);
		return colin == 1 ? Gis.Direction.DIRECT : colin == -1 ? Gis.Direction.INDIRECT : Gis.Direction.UNKNOWN;
	}
	@Override
	public final boolean   						isValidDrivingWay(double _heading, Road.Element _elt, int idSeg) {
		double segmentHeading = _elt.getHeading(idSeg);
		switch(_elt.getDrivingWay()) {
		case BOTH:		return Angles.colinear(_heading, segmentHeading, 45) != 0;
		case DIRECT:	return Angles.colinear(_heading, segmentHeading, 45) == 1;
		case INDIRECT:	return Angles.colinear(_heading, segmentHeading, 45) == -1;
		case NONE:
		case UNKNOWN:
		default:		return false;
		}
	}

	@Override
	public final Collection<Road.Element> 		getOrientedRoadTree(RoadCoordinate _position, double _radius) {
		Road.Element elt = _position.getRoadElement();

		Set<Road.Element> ort = new HashSet<Road.Element>();
		if(elt != null) {
			ort.add(elt);
			buildOrientedRoadTree(elt, _radius - (elt.getLength() - _position.getCurvilinearAbscissa()), _position.getRoadElementDirection(), ort);
		}

		return ort;
	}
	@Override
	public final Collection<Road.Element> 		getOrientedRoadVector(RoadCoordinate _position, double _length, Gis.Path _trajectory) {
		return null;
	}

	@Override
	public final Collection<Road.TraficSign> 	getForwardTraficSigns(RoadCoordinate _position, double _length, Collection<Road.Element> _forwardTree) {
		Set<Road.TraficSign> forwardSigns = new HashSet<Road.TraficSign>();

		Collection<Road.TraficSign> trafficSigns = getAllTrafficSigns();

		for(Road.TraficSign tse : trafficSigns)
			for(Road.Element elt : _forwardTree)
				tse.getMapInfos().map(mp -> mp.getRoadElement().getId())
									.filter(id -> id.equals(elt.getId()))
									.ifPresent(id -> forwardSigns.add(tse));
//				if(tse.hasMapPosition() && tse.getMapPosition().getRoad.Element().getId().equals(elt.getId()))
//					forwardSigns.add(tse);
		
		return forwardSigns;
	}
	@Override
	public final Collection<Gis.Dynamics> 		getForwardMobiles(RoadCoordinate _position, double _length, Collection<Road.Element> _elts) {
		return null;
	}

	public final boolean  						isNextDrivingElement(Road.Element _elt0, Road.Element _elt1, Gis.Direction _dir, double _thetaTest) {
		if(_elt1 == null) return false;

		if( _elt0.getNextElements().stream().map(elt -> elt.getId()).filter(id -> id.equals( _elt1.getId() )).findAny().isEmpty() )
			return false;
//			throw new RuntimeException("_elt1 is not next to _elt0");

		double  theta0 = _elt0.getHeading(),
				theta1 = _elt0.getHeading();

		Gis.Direction DIR0 = _elt0.getDrivingWay(), DIR1 = _elt1.getDrivingWay();

		if(Angles.colinear(theta0, theta1, _thetaTest) != 1)
			return false;

		if(_dir == DIRECT && (DIR0 == DIRECT || DIR0 == BOTH)) {
			if(DIR1 == DIRECT   && (_elt0.getEnd().equals(_elt1.getStart())))
				return true;
			if(DIR1 == INDIRECT && (_elt0.getEnd().equals(_elt1.getEnd())))
				return true;
			if(DIR1 == BOTH     && (_elt0.getEnd().equals(_elt1.getStart()) || _elt0.getEnd().equals(_elt1.getEnd())))
				return true;
		}
		if(_dir == DIRECT && DIR0 == INDIRECT) {
			System.err.println("WRONG WAY !!!");
			return false;
		}
		else if(_dir == INDIRECT && (DIR0 == INDIRECT || DIR0 == BOTH)) {
			if(DIR1 == DIRECT 	&& (_elt0.getStart().equals(_elt1.getStart())))
				return true;
			if(DIR1 == INDIRECT && (_elt0.getStart().equals(_elt1.getEnd())))
				return true;
			if(DIR1 == BOTH     && (_elt0.getStart().equals(_elt1.getStart()) || _elt0.getStart().equals(_elt1.getEnd())))
				return true;
		}
		if(_dir == INDIRECT && DIR0 == DIRECT) {
			System.err.println("WRONG WAY !!!");
			return false;
		}
		else if(_dir == BOTH) {
			return true;
		}

		return false;
	}

	protected final Set<Road.Element>  			buildInfluenceArea(RoadCoordinate _p, double _r) {
		Road.Element elt = _p.getRoadElement();

		if(elt == null)
			return null;

		Set<Road.Element> iarea = new HashSet<Road.Element>();
		iarea.add(elt);
		buildInfluenceArea(elt, _r, iarea);

		return iarea;
	}
	protected final void            			buildInfluenceArea(Road.Element _elt, double _r, Set<Road.Element> _ids) {
		if(_r < 0)
			return ;

		Set<Road.Element> prev = _elt.getPreviousElements();
		Set<Road.Element> next = _elt.getNextElements();
		
		Set<Road.Element> adjacents = new HashSet<Road.Element>();
		if(prev != null) adjacents.addAll(prev);
		if(next != null) adjacents.addAll(next);

		for(Road.Element a : adjacents) {
			if(!_ids.contains(a)) {
				buildInfluenceArea(a, _r - a.getLength(), _ids);
				_ids.add(a);
			}
		}
	}

	protected final void            			buildOrientedRoadTree(Road.Element _elt, double _r, Gis.Direction _dir, Set<Road.Element> _ids) {
		Set<Road.Element> nexts = _elt.getNextElements();
		if(_r <= 0 || nexts == null)
			return ;

		for(Road.Element next : nexts) {
			Road.Element A = next;

			if(isNextDrivingElement(_elt, A, _dir, THETA_TEST)) {
				_ids.add(A);
				buildOrientedRoadTree(A, _r - A.getLength(), Gis.Direction.areInverse(_dir, A.getDrivingWay()) ? Gis.Direction.getInverse(_dir) : _dir, _ids);
			}
		}
	}

}
