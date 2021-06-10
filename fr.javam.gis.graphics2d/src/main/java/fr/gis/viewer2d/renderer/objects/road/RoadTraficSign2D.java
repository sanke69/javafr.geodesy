package fr.gis.viewer2d.renderer.objects.road;

import java.util.function.Function;

import fr.drawer.fx.DrawerFx;
import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis.Object;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.graphics.utils.MapSprites;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;

public class RoadTraficSign2D implements GisObjectRenderer2D<Road.TraficSign> {

	@Override
	public boolean   isDrawable(Object _object) {
		return (_object instanceof Road.TraficSign);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.TraficSign _elt, GisRendererOption _options) {
		switch(_elt.getType()) {
		case MANDATORY_SPEED_LIMIT : drawMSL(_drawer, _window, _projector, _elt); break;
		case CROSS_WALK            :
		case STOP                  :
		case TRAFIC_LIGHT          :
		case YIELD                 : drawSign(_drawer, _window, _projector, _elt); break;
		default                    : break;
		}	
	}

	void drawSign(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.TraficSign _elt) {
		int w = 32;
		Point2D pt = _projector.apply(_elt.getPosition().asWGS84());

		// draw Exact Position
		_drawer.setDrawStyle("-fx-stroke: " + "blue" + "; -fx-width: " + 2 + "; -fx-stroke-width:" + 2);

		_drawer.drawCircle(pt.getX()-1, pt.getY()-1, 2);
		_drawer.drawCircle(pt.getX()-5, pt.getY()-5, 10);
		// draw Link to Symbol
		_drawer.drawSegment(pt.getX(), pt.getY()-5, pt.getX(), pt.getY()-10);
		// draw Symbol
		if(_drawer instanceof DrawerFx)
			((DrawerFx) _drawer).drawImage(MapSprites.get(_elt.getType(), 0), null, BoundingBoxes.of(pt.getX()-w/2, pt.getY()-10-w, w, w));
	}
	void drawMSL(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Road.TraficSign _msl) {
		Point2D pt = _projector.apply(_msl.getPosition().asWGS84());

		int    w = 32;
		double x = pt.getX();
		double y = pt.getY();

		_drawer.setDrawStyle("-fx-stroke: " + "blue" + "; -fx-width: " + 2 + "; -fx-stroke-width:" + 2);

		// draw Exact Position
		_drawer.drawCircle(x-1, y-1,  2);
		_drawer.drawCircle(x-5, y-5, 10);
		// draw Link to Symbol
		_drawer.drawSegment(  x, y-5,  x, y-10);
		// draw Symbol
		if(_drawer instanceof DrawerFx)
			((DrawerFx) _drawer).drawImage(MapSprites.get(Road.TraficSign.Type.MANDATORY_SPEED_LIMIT, (int) _msl.getProperty("MandatorySpeedLimit")), null, BoundingBoxes.of(x-w/2, y-w-10, w, w));
	}

}
