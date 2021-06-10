package fr.gms.sdk.graphics.plugins;

import java.util.List;

import fr.gms.sdk.graphics.drawers.PathDrawer;
import fr.gms.sdk.navigation.waypath.WayPaths;
import fr.gms.sdk.utils.PathBuilder;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.java.maths.algebra.Vectors;
import fr.java.utils.jMath;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.input.ScrollEvent;

public class PvcPluginProjection<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	private CanvasControl<MODEL, COORD>			control;
    private Canvas 								canvas;
    private PathDrawer 							drawer;

	private EventHandler<? super KeyEvent>    	keyPressed,   keyReleased;
	private EventHandler<? super MouseEvent>  	mousePressed, mouseReleased;
	private EventHandler<? super MouseEvent>  	mouseEntered;
	private EventHandler<? super MouseEvent>  	mouseMoved;
	private EventHandler<? super MouseEvent>  	mouseDragging;
	private EventHandler<? super MouseEvent>  	mouseExited;
	private EventHandler<? super ScrollEvent> 	mouseScrolling;

	/** *************************** **\
	 *                               *
	 *         CANVAS MOTION         *
	 *                               *
	\** *************************** **/
	public boolean  							crtlDown, AltDown, ShiftDown;
	private boolean 							dragging;
	public double   							lastX,  lastY;
	public Point2D  							lastPosition;
	public double   							mouseX, mouseY;
	public Point2D  							mouse;

    double 										espilon = 25d;

    ListProperty<Point2D> 						waypointsProperty;
    ObjectProperty<Point2D>     				positionProperty;
    ObjectProperty<Vector2D>    				directionProperty;
    BooleanProperty			    				loopProperty;
    
    ListProperty<Point2D> 						tracepointsProperty;

	private Point2D 							anchor, projection, orthoProjection;

	public PvcPluginProjection() {
		super();
    	waypointsProperty   = new SimpleListProperty<Point2D>    (FXCollections.observableArrayList());
    	positionProperty    = new SimpleObjectProperty<Point2D>  (null);
    	directionProperty   = new SimpleObjectProperty<Vector2D> (null);
    	loopProperty        = new SimpleBooleanProperty			 (false);

        waypointsProperty   . addListener ((ListChangeListener<? super Point2D>) evt -> { compute(); redraw(); });
        positionProperty    . addListener (evt -> { compute(); redraw(); });
        directionProperty   . addListener (evt -> { compute(); redraw(); });
        loopProperty        . addListener (evt -> { redraw(); });

    	tracepointsProperty = new SimpleListProperty<Point2D>   (FXCollections.observableArrayList());
        tracepointsProperty . addListener ((ListChangeListener<? super Point2D>) evt -> { redraw(); });
	}

	void compute() {
		if(waypointsProperty.get() != null && waypointsProperty.getSize() > 1
		&& positionProperty.get() != null
		&& directionProperty.get() != null) {
			boolean loop            = loopProperty.get();
			Point2D orthoProjection = PathBuilder.getOrthoProjection	(WayPaths.newSimplePath(loop, waypointsProperty.get()), 0, -1, positionProperty.get());
			Point2D projection      = PathBuilder.getProjection			(WayPaths.newSimplePath(loop, waypointsProperty.get()), 0, -1, positionProperty.get(), directionProperty.get());

			this.projection      = projection;
			this.orthoProjection = orthoProjection;
		}
	}

	@Override
	public void 							setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		if(_pvpControl instanceof CanvasControl) {
			CanvasControl<MODEL, COORD> canvasControl = (CanvasControl<MODEL, COORD>) _pvpControl;

			control = canvasControl;
			canvas  = canvasControl.canvas();
	        drawer  = new PathDrawer(canvas);

			control . setFocusTraversable ( true );
			control . addEventHandler     (KeyEvent.KEY_PRESSED, 	  keyPressed    = keyPressedHandler4Sizing());
			control . addEventHandler     (KeyEvent.KEY_RELEASED, 	  keyReleased   = keyReleasedHandler4Sizing());
			control . addEventHandler     (MouseEvent.MOUSE_PRESSED,  mousePressed  = mousePressedHandler4Motion());
			control . addEventHandler     (MouseEvent.MOUSE_RELEASED, mouseReleased = mouseReleasedHandler4Motion());
			control . addEventHandler     (MouseEvent.MOUSE_MOVED,    mouseMoved    = mouseMovedHandler4Motion());
			control . addEventHandler     (MouseEvent.MOUSE_DRAGGED,  mouseDragging = mouseDragHandler4Motion());
			control . addEventHandler     (MouseEvent.MOUSE_ENTERED,  mouseEntered  = mouseEnteredHandler4Motion());
			control . addEventHandler     (MouseEvent.MOUSE_EXITED,   mouseExited    = mouseExitedHandler4Motion());
			control . addEventHandler     (ScrollEvent.ANY,   		  mouseScrolling = scrollHandler4Motion());
		} else {
			System.err.println();
		}
	}
	public void 							unsetViewportControl() {
		if(control == null)
			return ;

		control . removeEventHandler (KeyEvent.KEY_PRESSED,      keyPressed);
		control . removeEventHandler (KeyEvent.KEY_RELEASED,     keyReleased);
		control . removeEventHandler (MouseEvent.MOUSE_PRESSED,  mousePressed);
		control . removeEventHandler (MouseEvent.MOUSE_RELEASED, mouseReleased);
		control . removeEventHandler (MouseEvent.MOUSE_MOVED,    mouseMoved);
		control . removeEventHandler (MouseEvent.MOUSE_DRAGGED,  mouseDragging);
		control . removeEventHandler (MouseEvent.MOUSE_ENTERED,  mouseEntered);
		control . removeEventHandler (MouseEvent.MOUSE_EXITED,   mouseExited);
		control . removeEventHandler (ScrollEvent.ANY, 		     mouseScrolling);
		control = null;

		keyPressed     = null;
		keyReleased    = null;
		mousePressed   = null;
		mouseReleased  = null;
		mouseMoved     = null;
		mouseDragging  = null;
		mouseEntered   = null;
		mouseExited    = null;
		mouseScrolling = null;
	}

	@Override
	public ObservableList<Node> 			 getChildren() {
		return FXCollections.emptyObservableList();
	}

	public ReadOnlyBooleanProperty			 loopProperty() {
		return loopProperty;
	}
    public ReadOnlyObjectProperty<Point2D>   spawnProperty() {
		return positionProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 waypointsProperty() {
		return waypointsProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 tracepointsProperty() {
		return tracepointsProperty;
	}

    public void  	 						 setTracepoints(Point2D[] _trace) {
		tracepointsProperty.setAll(_trace);
	}
    public void  	 						 setTracepoints(List<Point2D> _trace) {
		tracepointsProperty.setAll(_trace);
	}

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			switch(e.getCode()) {
    		case C	        : positionProperty.set(null); waypointsProperty.clear(); tracepointsProperty.clear(); break;
    		case L	        : loopProperty.set(!loopProperty.get()); break;
/*
			case DELETE	    :
			case SUBTRACT	: 	control.preferredFpsProperty().set(e.isAltDown() ?  0 : control.preferredFpsProperty().get() - 1); break;
*/
			default			: 	return;
			}

			e.consume();

		};
	}
	public EventHandler<? super KeyEvent>    keyReleasedHandler4Sizing() {
		return e -> {};
	}

	public EventHandler<? super MouseEvent>  mousePressedHandler4Motion() {
		return e -> { 
            double x      = e.getX();
            double y      = e.getY();
            double width  = canvas.getWidth();
            double height = canvas.getHeight();

            switch( e.getButton() ) {
			case PRIMARY:	if(x > 0 && x < width 
							&& y > 0 && y < height 
							&& ( Math.abs(lastX - x) > espilon || Math.abs(lastY - y) > espilon )) {
				            	waypointsProperty.add(Points.of(x, y));
				
				                lastX        = x;
				                lastY        = y;
								lastPosition = Points.of(e.getX(), e.getY());
				            	System.out.println("Adding pt: " + waypointsProperty.size());
				            }
							break;
			case SECONDARY: if(!dragging) anchor = Points.of(x, y);
							break;
			case MIDDLE:	positionProperty.set(Points.of(x, y));
							break;
/*
			case BACK:
							break;
			case FORWARD:
							break;
*/
			case NONE:
			default:
							break;
            }
		};
	}
	private EventHandler<? super MouseEvent> mouseReleasedHandler4Motion() {
		return e -> {
            switch( e.getButton() ) {
			case SECONDARY: dragging = false;
							break;
			default:
							break;
            }};
	}

	public EventHandler<? super MouseEvent>  mouseEnteredHandler4Motion() {
		return e -> control.requestFocus();
	}
	public EventHandler<? super MouseEvent>  mouseExitedHandler4Motion() {
		return e -> {};
	}

	public EventHandler<? super MouseEvent>  mouseMovedHandler4Motion() {
		return e -> {
			mouseX = e.getX();
            mouseY = e.getY();
            mouse  = Points.of(e.getX(), e.getY());
		};
	}
	public EventHandler<? super MouseEvent>  mouseDragHandler4Motion() {
		return e -> {
            double x = jMath.clamp(e.getX(), 3, canvas.getWidth( ) - 3);
            double y = jMath.clamp(e.getY(), 3, canvas.getHeight( ) - 3);

            if(e.isSecondaryButtonDown()) {
            	dragging = true;
                mouseX = x;
                mouseY = y;
                mouse  = Points.of(e.getX(), e.getY());
                
                Vector2D dir = Vectors.delta(mouse, anchor);
                directionProperty.set(dir);
            }

		};
	}
	public EventHandler<? super ScrollEvent> scrollHandler4Motion() {
		return e -> { };
	}

    private void redraw() {
    	drawer.clear();

    	if(waypointsProperty.get() != null)
    		drawer.drawPoints(waypointsProperty.get(), 1d, Colors.RED, true, true, 3d, false, 1d);

    	if(positionProperty.get() != null)
    		drawer.drawPoint(positionProperty.get(), 1d, Colors.ORANGE);

    	if(directionProperty.get() != null)
    		drawer.drawVector(directionProperty.get(), positionProperty.get() != null ? positionProperty.get() : anchor, 1d, Colors.ORANGE);

    	if(tracepointsProperty.get() != null)
    		drawer.drawPoints(tracepointsProperty.get(), 3d, Colors.GREEN.opacity(0.5));

    	if(projection != null)
    		drawer.drawPoint(projection, 5d, Colors.GREEN);

    	if(orthoProjection != null)
    		drawer.drawPoint(orthoProjection, 7d, Colors.DARKGREEN);

    }

}
