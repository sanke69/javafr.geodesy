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

public class GisArea2D implements GisObjectRenderer2D<Gis.Area> {

	@Override
	public boolean isDrawable(Object _object) {
		return (_object instanceof Gis.Area);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Area _area, GisRendererOption _options) {
		List<Point2D> pts = _area.getOutline().stream().map(c -> _projector.apply(c.asWGS84())).collect(Collectors.toList());

		// TODO:: Performance issue, multiple instanciation
		if( BoundingBoxes.of( _window ).intersects( BoundingBoxes.of( pts ) ) )
			for(int i = 0; i < pts.size() - 1; ++i) {
				Point2D x0 = pts.get(i);
				Point2D x1 = pts.get(i+1);
	
				_drawer.drawSegment(x0.getX(), x0.getY(), x1.getX(), x1.getY());
			}
	}

}
