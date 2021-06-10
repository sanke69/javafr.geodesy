package fr.gis.api.road.tools;

import java.util.Collection;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Direction;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.engines.MapMatchEngine;
import fr.gis.api.tools.MapToolBox;

public interface RoadToolBox extends MapToolBox {

	public Collection<Road.Element> 		getAllRoadElements			();
	public Collection<Road.TraficSign> 		getAllTrafficSigns			();

	public Collection<Road.Element> 		getSurroundingRoadElements	(GeoCoordinate _p, double _radius);
	public Collection<Road.TraficSign> 		getSurroundingTrafficSigns	(GeoCoordinate _p, double _radius);

	public MapMatchEngine 					getMapMatchEngine();

	public boolean   						isValidDrivingWay			(double _heading, Road.Element _elt, int idSeg);
	public Direction 						getDrivingWay				(RoadCoordinate _p, Gis.Curve _e);

	public Collection<Road.Element> 		getReachableElements		(RoadCoordinate _position, double _length);

	public Collection<Road.Element> 		getOrientedRoadTree			(RoadCoordinate _position, double _radius);
	public Collection<Road.Element> 		getOrientedRoadVector		(RoadCoordinate _position, double _length, Gis.Path _trajectory);

	public Collection<Gis.Dynamics> 		getForwardMobiles			(RoadCoordinate _position, double _length, Collection<Road.Element> _elts);
	public Collection<Road.TraficSign> 		getForwardTraficSigns		(RoadCoordinate _position, double _length, Collection<Road.Element> _elts);

}
