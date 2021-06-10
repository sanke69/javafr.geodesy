package fr.gis.viewer2d.renderer.objects.gis;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;

public class GisPath2D implements GisObjectRenderer2D<Gis.Path> {

	@Override
	public boolean   isDrawable(Object _object) {
		return (_object instanceof Gis.Path);
	}

	@Override
	public void      drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Path _object, GisRendererOption _options) {
		if(_options == null || _options.get("stroke-color") == null) {
			drawGeometry2D(_drawer, _window, _projector, _object);
			drawWayPoints2D(_drawer, _window, _projector, _object);

			return ;
		}

		String strokeColor = (String) _options.get("stroke-color");

		final double TOLERANCE = 250d;
		for(int i = 0; i < _object.getGeometry().size() - 1; ++i) {
			Point2D x0 = _projector.apply(_object.getGeometry(i).asWGS84());
			Point2D x1 = _projector.apply(_object.getGeometry(i+1).asWGS84());

			boolean x0_x_test = x0.getX() > - TOLERANCE && x0.getX() < _window.getWidth()  + TOLERANCE;
			boolean x0_y_test = x0.getY() > - TOLERANCE && x0.getY() < _window.getHeight() + TOLERANCE;
			boolean x1_x_test = x1.getX() > - TOLERANCE && x1.getX() < _window.getWidth()  + TOLERANCE;
			boolean x1_y_test = x1.getY() > - TOLERANCE && x1.getY() < _window.getHeight() + TOLERANCE;
			
			if(x0_x_test && x0_y_test && x1_x_test && x1_y_test)
				_drawer.drawSegment(x0.getX(), x0.getY(), x1.getX(), x1.getY(), 
									3d, Colors.of(strokeColor));

		}

	}

	protected void   drawGeometry2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Path _elt) {
		List<Point2D> pts = _elt.getGeometry().stream().map(c -> _projector.apply(c.asWGS84())).collect(Collectors.toList());

		if( BoundingBoxes.of( _window ) .intersects( BoundingBoxes.of( pts ) ) )
			_drawer.drawPolyLine(pts, width(_elt), color(_elt));	
	}
	protected void   drawWayPoints2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Path _elt) {
		List<Point2D> pts = _elt.getGeometry().stream().map(c -> _projector.apply(c.asWGS84())).collect(Collectors.toList());

		if( BoundingBoxes.of( _window ) .intersects( BoundingBoxes.of( pts ) ) )
			_drawer.drawPoints(pts, 6d, Colors.RED);
	}


	protected Color  color(Gis.Path _elt) {
		switch(_elt.getType()) {
		case PedestrianWay  : return Colors.of("orange");
		case CycleWay       : return Colors.of("yellow");
		case RoadWay        : return Colors.of("darkgrey");
		case RailWay        : return Colors.of("black");
		case WaterWay       : return Colors.of("blue");
		case GazWay         : return Colors.of("brown");
		case OilWay         : return Colors.of("black");
		case ElectricityWay : return Colors.of("yellow");
		case Unknown        : return Colors.of("cyan");
		default             : return Colors.of("magenta");
		}
	}
	protected int    width(Gis.Path _elt) {
		switch(_elt.getType()) {
		case PedestrianWay  : return 2;
		case CycleWay       : return 3;
		case RoadWay        : return 5;
		case RailWay        : return 4;
		case WaterWay       : return 3;
		case GazWay         : return 2;
		case OilWay         : return 2;
		case ElectricityWay : return 1;
		case Unknown        : return 1;
		default             : return 1;
		}
	}

}
