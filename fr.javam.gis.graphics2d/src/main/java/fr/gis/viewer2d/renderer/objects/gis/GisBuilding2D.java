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

public class GisBuilding2D implements GisObjectRenderer2D<Gis.Building> {

	@Override
	public boolean isDrawable(Object _object) {
		return (_object instanceof Gis.Building);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Building _building, GisRendererOption _opts) {
		List<Point2D> pts = _building.getOutline().stream().map(c -> _projector.apply(c.asWGS84())).collect(Collectors.toList());

		// TODO:: Performance issue, multiple instanciation
		if( BoundingBoxes.of( _window ).intersects( BoundingBoxes.of( pts ) ) )
			_drawer.drawPolygon(pts, 
								strokeWidth(_building), strokePaint(_building), fillPaint(_building));
	}

	double strokeWidth(Gis.Building _building) {
		return 1d;
	}
	Color strokePaint(Gis.Building _building) {
		return Colors.BLACK;
	}
	Color fillPaint(Gis.Building _building) {
		return Colors.GRAY;
	}

}
