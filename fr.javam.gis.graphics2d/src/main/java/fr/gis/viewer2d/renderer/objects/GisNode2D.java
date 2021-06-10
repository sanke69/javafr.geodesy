package fr.gis.viewer2d.renderer.objects;

import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;

public class GisNode2D implements GisObjectRenderer2D<Gis.Node> {

	@Override
	public boolean isDrawable(Object _object) {
		return (_object instanceof Gis.Node);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Node _node, GisRendererOption _options) {
		Point2D pt = _projector.apply( _node.getPosition().asWGS84() );

		// TODO:: Performance issue, multiple instanciation
		if( BoundingBoxes.of( _window ).contains( pt ) ) {
//			_drawer.drawPoint( pt );

//			_drawer.setDrawStyle("-fx-stroke: yellow;-fx-width: 2;");
			_drawer.drawSegment(pt.getX() - 1, pt.getY() - 1, pt.getX() + 1, pt.getY() + 1, 5, Colors.BLACK);
			_drawer.drawSegment(pt.getX() - 1, pt.getY() + 1, pt.getX() + 1, pt.getY() - 1, 5, Colors.BLACK);
		}
	}

}
