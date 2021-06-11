package fr.gis.viewer2d.renderer.objects.road;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis.Object;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.BoundingBoxes;

public class RoadElement2D implements GisObjectRenderer2D<Road.Element> {

	@Override
	public boolean   isDrawable(Object _object) {
		return (_object instanceof Road.Element);
	}

	@Override
	public void      drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.Element _object, GisRendererOption _options) {
		switch( (String) _options.get("render") ) {
		case "graph"    : 	drawBounds2D(_drawer, _window, _projector, _object);
							return ;
		case "geometry" : 	
		default         :   drawGeometry2D(_drawer, _window, _projector, _object);
							return ;
		}
	}

	protected void   drawGeometry2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.Element _elt) {
		List<Point2D> pts = _elt.getTops().stream().map(c -> _projector.apply(c.asWGS84())).collect(Collectors.toList());

		if( BoundingBoxes.of( _window ) .intersects( BoundingBoxes.of( pts ) ) ) {
			_drawer.setDrawStyle("-fx-stroke: " + color(_elt) + ";" + "-fx-stroke-width:" + width(_elt));
			_drawer.drawPolyLine(pts);
//			for(int i = 0; i < pts.size() - 1; ++i)
//				_drawer.drawSegment(pts.get(i).getX(), pts.get(i).getY(), pts.get(i+1).getX(), pts.get(i+1).getY());
		}
	}
	protected void   drawBounds2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.Element _elt) {
		Point2D x0 = _projector.apply(_elt.getStart().asWGS84());
		Point2D x1 = _projector.apply(_elt.getEnd().asWGS84());

		_drawer.setDrawStyle("-fx-stroke: " + "blue" + ";" + "-fx-stroke-width:" + 3);
		_drawer.drawSegment(x0.getX(), x0.getY(), x1.getX(), x1.getY());
	}

	protected String color(Road.Element _elt) {
		switch(_elt.getDrivingWay()) {
		case BOTH     : return "green";
		case DIRECT   : return "yellow";
		case INDIRECT : return "blue";
		case NONE     : return "black";
		case UNKNOWN  : return "red";
		default       : return "orange";
		}
	}
	protected int    width(Road.Element _elt) {
		switch(_elt.getCategory()) {
		case Motorway			 : return 4;
		case MajorRoad			 : return 3;
		case OtherMajorRoad		 : return 3;
		case SecondaryRoad     	 : return 2;
		case LocalConnectingRoad : return 1; 
		case LocalRoadLvl1		 : return 1;
		case LocalRoadLvl2		 : return 1;
		case LocalRoadLvl3		 : return 1;
		case OtherRoad			 : return 1;
		case BicybleWay          : return 1; 
		case WalkWay             : return 1; 
		case Unknown			 : return 1;	
		default                  : return 2;
		}
	}

}
