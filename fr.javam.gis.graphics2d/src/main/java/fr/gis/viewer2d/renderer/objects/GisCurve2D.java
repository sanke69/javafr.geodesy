package fr.gis.viewer2d.renderer.objects;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;

public class GisCurve2D implements GisObjectRenderer2D<Gis.Curve> {

	@Override
	public boolean isDrawable(Object _object) {
		return (_object instanceof Gis.Curve);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Curve _curve, GisRendererOption _opts) {
		List<Point2D> pts   = _curve.getTops().stream().map(c -> _projector.apply( c.asWGS84() )) .collect(Collectors.toList());
		String 		  color = _opts.containsKey("color") ? (String) _opts.get("color") : "red";

		// TODO:: Performance issue, multiple instanciation
		if( BoundingBoxes.of( _window ).intersects( BoundingBoxes.of( pts ) ) ) {
			_drawer.setDrawStyle("-fx-stroke: " + color + ";" + "-fx-stroke-width:" + 3);
			_drawer.drawPolyLine(pts);
		}
	}

}
