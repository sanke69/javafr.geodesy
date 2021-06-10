package fr.gis.viewer2d.renderer.objects.gis;

import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.geodesic.api.GeoDynamics;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.lang.collections.RingList;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.Angles;

public class GisDynamics2D implements GisObjectRenderer2D<Gis.Dynamics> {

	@Override
	public boolean isDrawable(Object _object) {
		return (_object instanceof Gis.Dynamics);
	}

	@Override
	public void drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, Gis.Dynamics _elt, GisRendererOption _opts) {
		if(_elt.getPosition() == null)
			return ;

		drawHistory  (_drawer, _window, _projector, _elt.getHistory(), null);
		drawPosition (_drawer, _window, _projector, _elt.getPosition(), null);
//		drawHeading  (_drawer, _window, _projector, _elt.getPosition(), _elt.getHeading(), null);
//		drawVelocity (_drawer, _window, _projector, _elt.getPosition(), _elt.getHeading(), _elt.getVelocity(), null);
	}

	protected void drawPosition (Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, GeoCoordinate _position, GisRendererOption _opts) {
		Point2D P = _projector.apply(_position.asWGS84());

		_drawer.setDrawStyle("-fx-stroke: yellow;-fx-width: 2;");
		_drawer.drawEllipse(P.getX(), P.getY(), 2, 2);
	}
	protected void drawHeading  (Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, GeoCoordinate _position, double _heading, GisRendererOption _opts) {
		Point2D P       = _projector.apply(_position.asWGS84());
		double  headRad = Angles.Degree2Radian( 90 - _heading );
		double  dx      = 30.0 * Math.cos(headRad),
			    dy      = 30.0 * Math.sin(headRad);
		_drawer.drawLine(P.getX(), P.getY(), P.getX() + dx, P.getY() - dy);
	}
	protected void drawVelocity (Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, GeoCoordinate _position, double _heading, Vector3D _velocity, GisRendererOption _opts) {
		Point2D P       = _projector.apply(_position.asWGS84());
		double K = 0.15;
		double vx      = K * (_velocity.getX() * Math.cos(_heading) + _velocity.getY() * Math.sin(_heading)),
			   vy      = K * (_velocity.getX() * Math.sin(_heading) + _velocity.getY() * Math.cos(_heading));

		_drawer.setDrawStyle("-fx-stroke: blue;");
		_drawer.setLineWidth(2.0);
		_drawer.drawLine(P.getX(), P.getY(), P.getX() - vx, P.getY() + vy);
	}
	protected void drawHistory  (Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, RingList<GeoDynamics> _history, GisRendererOption _opts) {
		Color.Interpolable c = Colors.BLACK;

		for(int i = _history.size() - 1; i > 0; --i) {
			GeoCoordinate raw = _history.get(i);

			Point2D  p = _projector.apply(raw.asWGS84());

			// History Position
			_drawer.setFill(c.interpolate(Colors.YELLOW, 1.0 - ((double) i / (_history.size() - 1.0))));
			_drawer.setLineWidth(2.0);
			_drawer.drawEllipse(p.getX()-5, p.getY()-5, 10, 10);

			_drawer.setLineWidth(1.0);
			_drawer.drawEllipse(p.getX()-1, p.getY()-1, 2, 2);
		}
	}

}
