package fr.gms.sdk.graphics.plugins;

import java.util.List;

import fr.gms.sdk.graphics.drawers.PathDrawer;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.java.utils.jMath;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PvcPluginPathAndSpawn<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	private CanvasControl<MODEL, COORD>			control;

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
	public boolean 								crtlDown, AltDown, ShiftDown;
	public double  								lastX,  lastY;
	public Point2D 								lastPosition;
	public double  								mouseX, mouseY;
	public Point2D 								mouse;

	private double 								espilon = 25d;

    private Canvas 								canvas;
    private PathDrawer 							drawer;

    private ObjectProperty<Point2D>     		spawnProperty;
    private ListProperty<Point2D> 				waypointsAProperty, waypointsBProperty;
    private BooleanProperty			    		loopProperty, feedAProperty, useBProperty;

    private BooleanProperty			    		traceProperty;
    private ListProperty<Point2D> 				tracepointsAProperty, tracepointsBProperty;

    private DoubleProperty			    		stepProperty;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PvcPluginPathAndSpawn() {
		super();

		stepProperty         = new SimpleDoubleProperty				(0.33d);
    	loopProperty         = new SimpleBooleanProperty			(false);
    	traceProperty        = new SimpleBooleanProperty			(true);
    	spawnProperty        = new SimpleObjectProperty<Point2D>	(null);
    	waypointsAProperty   = new SimpleListProperty<Point2D>		(FXCollections.observableArrayList());
    	waypointsBProperty   = new SimpleListProperty<Point2D>		(FXCollections.observableArrayList());
    	tracepointsAProperty = new SimpleListProperty<Point2D>		(FXCollections.observableArrayList());
    	tracepointsBProperty = new SimpleListProperty<Point2D>		(FXCollections.observableArrayList());
		feedAProperty        = new SimpleBooleanProperty			(true);
		useBProperty         = new SimpleBooleanProperty			(false);

        stepProperty         . addListener (this::redraw);
        loopProperty         . addListener (this::redraw);
        traceProperty        . addListener (this::redraw);
        spawnProperty        . addListener (this::redraw);
        waypointsAProperty   . addListener ((ListChangeListener)this::redraw);
        waypointsBProperty   . addListener ((ListChangeListener)this::redraw);
        tracepointsAProperty . addListener ((ListChangeListener)this::redraw);
        tracepointsBProperty . addListener ((ListChangeListener)this::redraw);
	}

	@Override
	public void 				setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		if(_pvpControl instanceof CanvasControl) {
			CanvasControl<MODEL, COORD> canvasControl = (CanvasControl<MODEL, COORD>) _pvpControl;

			canvas = canvasControl.canvas();
	        drawer = new PathDrawer(canvas);

			control = canvasControl;
			control . setFocusTraversable ( true );
			control . addEventHandler    (KeyEvent.KEY_PRESSED, 	 keyPressed    = keyPressedHandler4Sizing());
			control . addEventHandler    (KeyEvent.KEY_RELEASED, 	 keyReleased   = keyReleasedHandler4Sizing());
			control . addEventHandler    (MouseEvent.MOUSE_PRESSED,  mousePressed  = mousePressedHandler4Motion());
			control . addEventHandler    (MouseEvent.MOUSE_RELEASED, mouseReleased = mouseReleasedHandler4Motion());
			control . addEventHandler    (MouseEvent.MOUSE_MOVED,    mouseMoved    = mouseMovedHandler4Motion());
			control . addEventHandler    (MouseEvent.MOUSE_DRAGGED,  mouseDragging = mouseDragHandler4Motion());
			control . addEventHandler    (MouseEvent.MOUSE_ENTERED,  mouseEntered  = mouseEnteredHandler4Motion());
			control . addEventHandler    (MouseEvent.MOUSE_EXITED,   mouseExited   = mouseExitedHandler4Motion());
			control . addEventHandler    (ScrollEvent.ANY,   		 mouseScrolling     = scrollHandler4Motion());
		} else {
			System.err.println();
		}
	}
	public void 				unsetViewportControl() {
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
	public ObservableList<Node> getChildren() {
		return FXCollections.emptyObservableList();
	}

	public ReadOnlyDoubleProperty  			 stepProperty() {
		return stepProperty;
	}
	public ReadOnlyBooleanProperty			 loopProperty() {
		return loopProperty;
	}
	public ReadOnlyBooleanProperty			 feedAProperty() {
		return feedAProperty;
	}
	public ReadOnlyBooleanProperty			 useBProperty() {
		return useBProperty;
	}
    public ReadOnlyObjectProperty<Point2D>   spawnProperty() {
		return spawnProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 waypointsAProperty() {
		return waypointsAProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 waypointsBProperty() {
		return waypointsBProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 tracepointsAProperty() {
		return tracepointsAProperty;
	}
    public ReadOnlyListProperty<Point2D> 	 tracepointsBProperty() {
		return tracepointsBProperty;
	}

    public void  	 						 setTracepointsA(Point2D[] _trace) {
    	if(_trace == null)
    		tracepointsAProperty.clear();
    	else
    		tracepointsAProperty.setAll(_trace);
	}
    public void  	 						 setTracepointsA(List<Point2D> _trace) {
    	if(_trace == null)
    		tracepointsAProperty.clear();
    	else
    		tracepointsAProperty.setAll(_trace);
	}
    public void  	 						 setTracepointsB(Point2D[] _trace) {
    	if(_trace == null)
    		tracepointsBProperty.clear();
    	else
    		tracepointsBProperty.setAll(_trace);
	}
    public void  	 						 setTracepointsB(List<Point2D> _trace) {
    	if(_trace == null)
    		tracepointsBProperty.clear();
    	else
    		tracepointsBProperty.setAll(_trace);
	}

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			switch(e.getCode()) {
    		case C	        : spawnProperty.set(null); waypointsAProperty.clear(); waypointsBProperty.clear(); tracepointsAProperty.clear(); tracepointsBProperty.clear(); break;
    		case L	        : loopProperty.set(!loopProperty.get()); break;
    		case T	        : traceProperty.set(!traceProperty.get()); break;

    		case P	        : stepProperty.set(stepProperty.get()+0.5d); break;
    		case M	        : stepProperty.set(stepProperty.get()-0.5d); break;

    		case D          : useBProperty.set(!useBProperty.get()); break;
    		case SPACE      : feedAProperty.set(!feedAProperty.get()); break;
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

            if(e.isControlDown() || e.isSecondaryButtonDown() || e.getButton() == MouseButton.SECONDARY)
                spawnProperty.set(Points.of(x, y));
        	else if(e.isPrimaryButtonDown())
	            if (x > 0 && x < width && y > 0 && y < height && (Math.abs(lastX - x) > espilon || Math.abs(lastY - y) > espilon)) {
	            	if(feedAProperty.get())
	            		waypointsAProperty.add(Points.of(x, y));
	            	else
	            		waypointsBProperty.add(Points.of(x, y));
	
	                lastX        = x;
	                lastY        = y;
					lastPosition = Points.of(e.getX(), e.getY());
	            	System.out.println("Adding pt: " + waypointsAProperty.size());
	            }

		};
	}
	private EventHandler<? super MouseEvent> mouseReleasedHandler4Motion() {
		return e -> {};
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

            if(e.isMiddleButtonDown())
            	drawer.drawLine(mouseX, mouseY, x, y, 1d, Colors.GREEN);

            mouseX = x;
            mouseY = y;
            mouse  = Points.of(e.getX(), e.getY());
		};
	}
	public EventHandler<? super ScrollEvent> scrollHandler4Motion() {
		return e -> {
			if(e.isControlDown()) {
				//isZoomingProperty.set(true);
				Point2D oldAP  = Points.of(control.getViewport().getViewAnchor()); // TODO:: Change to Coordinate
				control.zoom(e.getDeltaY() < 0, e.getX(), e.getY());
				if (lastPosition != null) {
					Point2D newAP = Points.of(control.getViewport().getViewAnchor());
					lastPosition  = Points.of( lastPosition.getX() - oldAP.getX() + newAP.getX(), 
							 					lastPosition.getY() - oldAP.getY() + newAP.getY());
				}
				e.consume();
			} else ;
				//isZoomingProperty.set(false);
		};
	}

	public void redraw() {
    	drawer.clear();

    	if(waypointsAProperty.get() != null)
    		drawer.drawPoints(waypointsAProperty.get(), 1d, Colors.RED, true, true, 3d, false, 1d);

    	if(waypointsBProperty.get() != null)
    		drawer.drawPoints(waypointsBProperty.get(), 1d, Colors.ORANGE, true, true, 3d, false, 1d);

    	if(spawnProperty.get() != null)
    		drawer.drawPoint(spawnProperty.get(), 1d, Colors.BLUE);

    	if(traceProperty.get() && tracepointsAProperty.get() != null)
    		drawer.drawPoints(tracepointsAProperty.get(), 3d, Colors.GREEN.opacity(0.5));

    	if(traceProperty.get() && tracepointsBProperty.get() != null)
    		drawer.drawPoints(tracepointsBProperty.get(), 3d, Colors.DARKGREEN.opacity(0.5));

    }
    public void redraw(Observable observable) {
    	redraw();
    }
    public void redraw(ListChangeListener.Change<? extends Point2D> c) {
    	redraw();
    }
    
}
 