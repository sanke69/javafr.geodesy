package fr.gms.sdk.graphics.drawers;

import java.util.List;

import fr.drawer.fx.DrawerFx;
import fr.gms.navigation.waypath.Segment;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import javafx.scene.canvas.Canvas;

public class PathDrawer extends DrawerFx {

    public PathDrawer(Canvas _canvas) {
    	super(_canvas);
    }

    public void clear() {    	
	    drawRectangle(0, 0, (int) canvas().getWidth(), (int) canvas().getHeight(), 0d, Colors.WHITE, Colors.WHITE);
    }

    public void drawPoint(Point2D _pt, double _thikness, Color _color, double _crossLength) {
        if(_pt == null)
        	return ;

        double x_l = _pt.getX() - _crossLength - _thikness/2d;
        double x_r = _pt.getX() + _crossLength + _thikness;
        double y_t = _pt.getY() - _crossLength - _thikness/2d;
        double y_b = _pt.getY() + _crossLength + _thikness;
    	drawLine(x_l, y_t, x_r, y_b, _thikness, _color);
        drawLine(x_l, y_b, x_r, y_t, _thikness, _color);
    }
    public void drawPoints(List<Point2D> _points, double _thickness, Color _color, boolean _useLabel, boolean _useCross, double _crossLength, boolean _useNormal, double _normalLength) {
        if(_points.size() == 0)
        	return ;

        for(int i = 0; i < _points.size(); ++i) {
        	Point2D pt = _points.get(i);

        	if(_useLabel)
        		drawString("" + i, pt.getX() + 5, pt.getY() - 5);
        	if(_useCross) {
        		drawLine(pt.getX() - _crossLength, pt.getY() - _crossLength, pt.getX() + _crossLength, pt.getY() + _crossLength, _thickness, _color);
        		drawLine(pt.getX() - _crossLength, pt.getY() + _crossLength, pt.getX() + _crossLength, pt.getY() - _crossLength, _thickness, _color);
        	} else
        		drawPoint(pt.getX(), pt.getY(), _thickness, _color);

        	if(_useNormal && _points.size() > i+1) {
            	Point2D pt2 = _points.get(i+1);
            	if(pt2 != null) {
            		Vector2D dir = new Segment(pt2, pt).normal(true);
            		drawVector(dir.normalize(_normalLength), pt.plus(_thickness/2d), 1d, _color);
            	}
        	}
        }
    }

}
