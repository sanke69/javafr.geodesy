package fr.gms.sdk.graphics.plugins;

import java.util.List;
import java.util.stream.Collectors;

import fr.drawer.fx.DrawerFx;
import fr.drawer.fx.ViewportDrawerFx;
import fr.gms.navigation.waypath.WayPath;
import fr.gms.sdk.graphics.drawers.PathDrawer;
import fr.gms.sdk.navigation.waypath.WayPathDouble;
import fr.gms.sdk.navigation.waypath.WayPaths;
import fr.gms.sdk.utils.PathBuilder;
import fr.gms.sdk.utils.PathSwitcher;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.javafx.event.RefreshEvent;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PvcPluginTrajectoryDrawer<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	private PlaneViewportControl<MODEL, COORD>	control;
	private Viewport.TwoDims<MODEL, COORD>  	viewport;
	private Canvas								canvas;
	private DrawerFx							modelDrawer;
	private PathDrawer							windowDrawer;

	private EventHandler<? super KeyEvent>    	keyPressed,  keyReleased;
	private EventHandler<? super MouseEvent>  	mousePressed, mouseReleased;

	private ObjectProperty<COORD> 				poiProperty, positionProperty;
	private ListProperty<COORD> 				leftTrackProperty,             	rightTrackProperty;
	private ListProperty<Point2D> 				interpolatedLeftTrackProperty, 	interpolatedRightTrackProperty;
	private ListProperty<Point2D> 				switchTrackProperty;
	private ObjectProperty<Color> 				leftColorProperty,             	rightColorProperty, 
												interpolatedLeftColorProperty, 	interpolatedRightColorProperty,
												switchTrackColorProperty;
    private DoubleProperty			    		stepProperty;
    private BooleanProperty			    		referenceProperty, 				traceProperty;

	public PvcPluginTrajectoryDrawer() {
		super();
		stepProperty   		           = new SimpleDoubleProperty		 (0.33);
		referenceProperty              = new SimpleBooleanProperty		 (true);
    	traceProperty                  = new SimpleBooleanProperty		 (false);

		poiProperty                    = new SimpleObjectProperty<COORD> ();
		positionProperty               = new SimpleObjectProperty<COORD> ();
		leftColorProperty              = new SimpleObjectProperty<Color> (Colors.ORANGE);
    	leftTrackProperty              = new SimpleListProperty<COORD>   (FXCollections.observableArrayList());
		rightColorProperty             = new SimpleObjectProperty<Color> (Colors.RED);
        rightTrackProperty             = new SimpleListProperty<COORD>   (FXCollections.observableArrayList());

    	interpolatedLeftColorProperty  = new SimpleObjectProperty<Color> (Colors.GREEN);
		interpolatedLeftTrackProperty  = new SimpleListProperty<Point2D> (FXCollections.observableArrayList());
    	interpolatedRightColorProperty = new SimpleObjectProperty<Color> (Colors.BLUE);
        interpolatedRightTrackProperty = new SimpleListProperty<Point2D> (FXCollections.observableArrayList());
        switchTrackColorProperty       = new SimpleObjectProperty<Color> (Colors.MAGENTA);
        switchTrackProperty            = new SimpleListProperty<Point2D> (FXCollections.observableArrayList());

    	poiProperty        . addListener (evt -> { computePosition(); redraw(); });
    	stepProperty       . addListener (evt -> { computeLanes(); redraw(); });
    	referenceProperty  . addListener (evt -> { redraw(); });
    	traceProperty      . addListener (evt -> { redraw(); });
        leftTrackProperty  . addListener ((ListChangeListener<? super COORD>) evt -> { computeLanes(); redraw(); });
    	rightTrackProperty . addListener ((ListChangeListener<? super COORD>) evt -> { computeLanes(); redraw(); });
	}

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			switch(e.getCode()) {
    		case O	        : referenceProperty.set(!referenceProperty.get()); break;
    		case T	        : traceProperty.set(!traceProperty.get()); break;
    		case P          : stepProperty.set(stepProperty.get()+1); break;
    		case M          : stepProperty.set(stepProperty.get()-1); break;
    		case C          : computeLanes(); redraw(); break;
			default			: 	return;
			}

			if(stepProperty.get() < 0.005)
				stepProperty.set(0.1);

			e.consume();

		};
	}
	public EventHandler<? super KeyEvent>    keyReleasedHandler4Sizing() {
		return e -> {};
	}

	public EventHandler<? super MouseEvent>  mousePressedHandler4Motion() {
		return e -> { 
            double x = e.getX();
            double y = e.getY();

            if(e.isControlDown()/* || e.isSecondaryButtonDown() || e.getButton() == MouseButton.SECONDARY*/) {
                poiProperty.set(viewport.windowInModel(Points.of(x, y)));
                redraw();
            }

		};
	}
	public EventHandler<? super MouseEvent>  mouseReleasedHandler4Motion() {
		return e -> {};
	}

	public ObjectProperty<COORD> 			 poiProperty() {
		return poiProperty;
	}
	public ListProperty<COORD> 				 leftTrackProperty() {
		return leftTrackProperty;
	}
	public ListProperty<COORD> 				 rightTrackProperty() {
		return rightTrackProperty;
	}
	public ListProperty<Point2D> 			 interpolatedTrackProperty() {
		return interpolatedLeftTrackProperty;
	}

	@Override
	public void 							 setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		if(_pvpControl instanceof CanvasControl) {
			CanvasControl canvasControl = (CanvasControl) _pvpControl;

			control  = canvasControl;
			viewport = canvasControl.getViewport();
			canvas   = canvasControl.canvas();

			modelDrawer  = new ViewportDrawerFx(canvas, viewport);
			windowDrawer = new PathDrawer(canvas);

			control . setFocusTraversable ( true );
			control . addEventHandler   (KeyEvent.KEY_PRESSED, 	    keyPressed    = keyPressedHandler4Sizing());
			control . addEventHandler   (KeyEvent.KEY_RELEASED,     keyReleased   = keyReleasedHandler4Sizing());
			control . addEventHandler   (MouseEvent.MOUSE_PRESSED,  mousePressed  = mousePressedHandler4Motion());
			control . addEventHandler   (MouseEvent.MOUSE_RELEASED, mouseReleased = mouseReleasedHandler4Motion());
			control . addEventHandler	(RefreshEvent.REFRESHED,    e -> redraw());
			
		}
	}
	public void 							 unsetViewportControl() {
		if(control == null)
			return ;

		control . removeEventHandler (KeyEvent.KEY_PRESSED,      keyPressed);
		control . removeEventHandler (KeyEvent.KEY_RELEASED,     keyReleased);
		control . removeEventHandler (MouseEvent.MOUSE_PRESSED,  mousePressed);
		control . removeEventHandler (MouseEvent.MOUSE_RELEASED, mouseReleased);

		control  = null;
		viewport = null;
		canvas   = null;

		modelDrawer  = null;
		windowDrawer = null;
	}

	@Override
	public ObservableList<Node> 			 getChildren() {
		return FXCollections.emptyObservableList();
	}

	WayPath	computed;
	private void 							 computeLanes() {
		System.out.println("Start computing...");

		List<Point2D> left = null, right = null;

		if(!rightTrackProperty.isEmpty())
			right = rightTrackProperty.get().stream().map(Points::of).collect(Collectors.toList());
		if(!leftTrackProperty.isEmpty())
			left = leftTrackProperty.get().stream().map(Points::of).collect(Collectors.toList());

		if(right != null && left != null)
			computed = new PathBuilder().buildDualTrack  (WayPaths.newSimpleTrip(right), WayPaths.newSimpleLoop(left), stepProperty.get());
		else if(right != null)
			computed = new PathBuilder().buildMonoTrack  (WayPaths.newSimpleTrip(right), stepProperty.get());
		else if(left != null)
			computed = new PathBuilder().buildMonoTrack  (WayPaths.newSimpleTrip(left), stepProperty.get());

		if(computed != null) {
			if(computed instanceof WayPathDouble) {
				WayPathDouble dual = (WayPathDouble) computed;

				interpolatedRightTrackProperty . setAll(dual.getWayPoints(WayPathDouble.RIGHT));
				interpolatedLeftTrackProperty  . setAll(dual.getWayPoints(WayPathDouble.LEFT));

			} else if(computed instanceof WayPath.Single) {
				WayPath.Single single = (WayPath.Single) computed;

				interpolatedRightTrackProperty . setAll(single.getWayPoints());

			}
		}

		System.out.println("Computing done!!");
	}
	private void 						 	 computePosition() {
		System.out.println("Start computing position...");

		if(computed == null || poiProperty.get() == null) {
			System.err.println("Computing failed!!");
			return ;
		}

		Point2D proj = null;
		if(computed instanceof WayPath.Single) 
			proj = PathBuilder.getOrthoProjection((WayPath.Single) computed, 0, -1, Points.of( poiProperty.get() ));
		else if(computed instanceof WayPathDouble)
			proj = PathBuilder.getOrthoProjection(((WayPathDouble) computed).getLane(0), 0, -1, Points.of( poiProperty.get() ));

		if(proj != null)
			positionProperty.set((COORD) proj);

		if(computed instanceof WayPathDouble) {
			double step       = stepProperty.get();
			double prevDist   = 50;
			double initDist   = 50;
			double switchDist = 150;
			double nextDist   = 150;

			WayPath.Single path = new PathSwitcher().buildSwitchTrajectory((WayPathDouble) computed, proj, true, 
																			(int) (prevDist   / step), 
																			(int) (initDist   / step), 
																			(int) (switchDist / step), 
																			(int) (nextDist   / step));

			if(proj != null)
				positionProperty.set((COORD) proj);

			if(path.getWayPoints() != null)
				switchTrackProperty.setAll(path.getWayPoints());

		} else {
			System.err.println("Not on WayPathDouble !!!");

			if(computed instanceof WayPath.Single) {
				proj = PathBuilder.getOrthoProjection((WayPath.Single) computed, 0, -1, Points.of( poiProperty.get() ));

				if(proj != null)
					positionProperty.set((COORD) proj);
			}
		}

		System.out.println("Computing position done!!");
	}
	public void 							 redraw() {
    	clear();

    	double ptSizeRef    = viewport.getViewScale() < 30 ? 3d : 7d;
    	double ptSizeInterp = viewport.getViewScale() < 30 ? 3d : 7d;

    	if(poiProperty.get() != null) {
        	Point2D poi = Points.of( viewport.modelInWindow( (COORD) poiProperty.get() ) );
	    	windowDrawer.drawPoint(poi, ptSizeRef, rightColorProperty.get(), 5d);
    	}
    	if(positionProperty.get() != null) {
        	Point2D position = Points.of( viewport.modelInWindow( (COORD) positionProperty.get() ) );
	    	windowDrawer.drawPoint(position, ptSizeRef, Colors.GREEN, 2d);
    	}
    	if(referenceProperty.get()) {
        	if(rightTrackProperty.size() > 0) {
	        	List<Point2D> ref_right = rightTrackProperty . get().stream().map(viewport::modelInWindow).map(Points::of).collect(Collectors.toList());
		    	windowDrawer.drawPoints(ref_right, ptSizeRef, rightColorProperty.get(), false, false, 1d, false, 1d);
        	}
        	if(rightTrackProperty.size() > 0) {
	        	List<Point2D> ref_left  = leftTrackProperty  . get().stream().map(viewport::modelInWindow).map(Points::of).collect(Collectors.toList());
		    	windowDrawer.drawPoints(ref_left,  ptSizeRef, leftColorProperty.get(),  false, false, 1d, false, 1d);
        	}
    	}

    	if(traceProperty.get()) {
        	if(interpolatedRightTrackProperty.size() > 0) {
            	List<Point2D> trk_right = interpolatedRightTrackProperty .get().stream().map(pt -> viewport.modelInWindow((COORD) pt)).map(Points::of).collect(Collectors.toList());
    	    	windowDrawer.drawPoints(trk_right, ptSizeInterp, interpolatedRightColorProperty.get(), false, false, 1d, viewport.getViewScale() > 30, 50d);
        	}
	    	if(interpolatedLeftTrackProperty.size() > 0) {
	        	List<Point2D> trk_left  = interpolatedLeftTrackProperty  .get().stream().map(pt -> viewport.modelInWindow((COORD) pt)).map(Points::of).collect(Collectors.toList());
	        	windowDrawer.drawPoints(trk_left, ptSizeInterp, interpolatedLeftColorProperty.get(),  false, false, 1d, viewport.getViewScale() > 30, 50d);
	    	}
    	}

    	if(switchTrackProperty.size() > 0) {
        	List<Point2D> ref_left  = switchTrackProperty  . get().stream().map(pt -> viewport.modelInWindow((COORD) pt)).map(Points::of).collect(Collectors.toList());
	    	windowDrawer.drawPoints(ref_left,  ptSizeRef, switchTrackColorProperty.get(),  false, false, 1d, false, 1d);
    	}
    }

    private void 							 clear() {
	    int width  = (int) canvas.getWidth();
	    int height = (int) canvas.getHeight();

//	    modelDrawer  . drawRectangle(0, 0, width, height, 0d, Colors.RED.opacity(.5), 			Colors.RED.opacity(.5));
//	    windowDrawer . drawRectangle(0, 0, width, height, 3d, Colors.DARKBLUE.opacity(0.75),   	Colors.WHITE.opacity(.5));
	    windowDrawer . drawRectangle(0, 0, width, height, 3d, Colors.DARKBLUE,   	Colors.WHITE);
    }

}
