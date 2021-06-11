package fr.gms.planner.utils.hmi;

import fr.drawer.fx.DrawerFx;
import fr.gms.planner.agent.TraceAgent;
import fr.gms.planner.agent.ant.AntAgentOriented;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.Points;
import fr.javafx.JavaFX;
import fr.javafx.scene.canvas.ResizableCanvas;
import fr.javafx.stage.StageExt;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TraceAgentMapDrawer extends StageExt {

    public static void main(String[] args) {
    	JavaFX.launchWithArgs(StartApplication.class, "demoOpts");
    }

	public static class StartApplication extends Application {

	    public void start(Stage _stage) {
	    	getParameters();

	    	TraceAgentMapDrawer stage = new TraceAgentMapDrawer();
			stage.show();
			

			stage.loopProperty       . addListener (evt -> { compute(stage); });
			stage.spawnProperty      . addListener (evt -> { compute(stage); });
	        stage.waypointsProperty  . addListener ((ListChangeListener<? super Point2D>) evt -> { compute(stage); });
	    }

	    public void compute(TraceAgentMapDrawer _stage) {
	    	if(_stage.spawnProperty.get() == null || _stage.waypointsProperty.size() < AntAgentOriented.MIN_PATH_SIZE)
	    		return ;

	    	TraceAgent agent = new AntAgentOriented(250, 10);

	    	Point2D[]  trace = agent.compute(_stage.spawnProperty.get(), new TraceAgent.Path(_stage.waypointsProperty, _stage.loopProperty.get()));

	    	if(trace == null) {
	        	System.err.println("'trace' is empty");
	        	_stage.tracepointsProperty.clear();
	    		return ;
	    	}

	    	_stage.tracepointsProperty.setAll(trace);
	    }

	}

    private Scene 				scene;
    private Pane 				root;
    private Canvas 				canvas;

    private AppDrawer 			drawer;
    private InputController		inputs;

    BooleanProperty			    loopProperty;
    ObjectProperty<Point2D>     spawnProperty;
    ListProperty<Point2D> 		waypointsProperty;
    ListProperty<Point2D> 		tracepointsProperty;

    public TraceAgentMapDrawer() {
    	this("µPaintFx", -1, -1);
    }
    public TraceAgentMapDrawer(String _title) {
    	this(_title, -1, -1);
    }
    public TraceAgentMapDrawer(int _width, int _height) {
    	this("µPaintFx", _width, _height);
    }
    public TraceAgentMapDrawer(String _title, int _width, int _height) {
    	super();
    	loopProperty        = new SimpleBooleanProperty			(false);
    	spawnProperty       = new SimpleObjectProperty<Point2D> (null);
    	waypointsProperty   = new SimpleListProperty<Point2D>   (FXCollections.observableArrayList());
    	tracepointsProperty = new SimpleListProperty<Point2D>   (FXCollections.observableArrayList());

        canvas = new ResizableCanvas();
        root   = new BorderPane(canvas);
		scene  = _width > 0 && _height > 0 ? new Scene(root, _width, _height) : new Scene(root);

        drawer = new AppDrawer(canvas);
    	inputs = new InputController();

        canvas.setFocusTraversable ( true );
        canvas.setOnMouseMoved         ( inputs::mouseMoved);
        canvas.setOnMousePressed       ( inputs::mousePressed );
        canvas.setOnMouseDragged       ( inputs::mouseDragged );
        canvas.setOnMouseReleased      ( inputs::mouseReleased );
        canvas.setOnKeyPressed	       ( inputs::keyPressed );
        canvas.widthProperty()  . bind ( root.widthProperty() );
        canvas.heightProperty() . bind ( root.heightProperty() );

        loopProperty       . addListener (evt -> { drawer.redraw(); });
        spawnProperty      . addListener (evt -> { drawer.redraw(); });
        waypointsProperty  . addListener ((ListChangeListener<? super Point2D>) evt -> { drawer.redraw(); });
        tracepointsProperty. addListener ((ListChangeListener<? super Point2D>) evt -> { drawer.redraw(); });

		setScene(scene);
//		setResizable(false);
		setTitle(_title);
    }

    public ObservableValue<Boolean> loopProperty() {
    	return loopProperty;
    }
    public ObservableValue<Point2D> spawnProperty() {
    	return spawnProperty;
    }
    public ObservableList<Point2D>  waypointsProperty() {
    	return waypointsProperty;
    }
    public ObservableList<Point2D>  tracepointsProperty() {
    	return tracepointsProperty;
    }

    private class InputController {
        private double lastX,  lastY;
        private double mouseX, mouseY;

        public void keyPressed(KeyEvent _ke) {
        	switch(_ke.getCode()) {
    		case C	: spawnProperty.set(null); waypointsProperty.clear(); tracepointsProperty.clear(); break;
    		case L	: loopProperty.set(!loopProperty.get()); break;
    		case R	: break;
    		default	: break;
        	}
        }
        public void mousePressed(MouseEvent evt) { double espilon = 25d;
            double x      = evt.getX();
            double y      = evt.getY();
            double width  = canvas.getWidth();
            double height = canvas.getHeight();

            if(evt.isControlDown() || evt.isSecondaryButtonDown())
                spawnProperty.set(Points.of(x, y));
        	else if(evt.isPrimaryButtonDown())
	            if (x > 0 && x < width && y > 0 && y < height && (Math.abs(lastX - x) > espilon || Math.abs(lastY - y) > espilon)) {
	            	waypointsProperty.add(Points.of(x, y));
	
	                lastX = x;
	                lastY = y;
	            }
        }
        public void mouseReleased(MouseEvent evt) {
            ;
        }

        public void mouseMoved(MouseEvent evt) {
            mouseX = evt.getX();
            mouseY = evt.getY();
        }

        public void mouseDragged(MouseEvent evt) {
            double x = evt.getX();
            double y = evt.getY();

            if (x < 3)
                x = 3;
            if (x > canvas.getWidth() - 57)
                x = (int)canvas.getWidth() - 57;

            if (y < 3)
                y = 3;
            if (y > canvas.getHeight() - 4)
                y = canvas.getHeight() - 4;

            if(evt.isMiddleButtonDown())
            	drawer.drawLine(mouseX, mouseY, x, y, 1d, Colors.GREEN);

            mouseX = x;
            mouseY = y;
        }

    }

    private class AppDrawer extends DrawerFx {

        AppDrawer(Canvas _canvas) {
        	super(_canvas);
        }

        private void redraw() {
        	clear();
        	drawSpawnPoint();
        	drawWayPoints();
        	drawTracepoints();
        }
        
        private void clear() {
    	    int width  = (int)canvas.getWidth();
    	    int height = (int)canvas.getHeight();
    	
    	    drawRectangle(0, 0, width, height, 0d, Colors.WHITE, Colors.WHITE);
        }
        private void drawSpawnPoint() { int l = 3;
	        if(spawnProperty.get() == null)
	        	return ;

        	Point2D pt = spawnProperty.get();

            drawLine(pt.getX() - l, pt.getY() - l, pt.getX() + l, pt.getY() + l, 1d, Colors.ORANGE);
            drawLine(pt.getX() - l, pt.getY() + l, pt.getX() + l, pt.getY() - l, 1d, Colors.ORANGE);
	    }
        private void drawWayPoints() { int l = 3;
	        if(waypointsProperty.size() == 0)
	        	return ;

	        for(int i = 0; i < waypointsProperty.size(); ++i) {
	        	Point2D pt = waypointsProperty.get(i);

	            drawString("" + i, pt.getX() + 5, pt.getY() - 5);
	            drawLine(pt.getX() - l, pt.getY() - l, pt.getX() + l, pt.getY() + l, 1d, Colors.RED);
	            drawLine(pt.getX() - l, pt.getY() + l, pt.getX() + l, pt.getY() - l, 1d, Colors.RED);
	        }
	    }
        private void drawTracepoints() { int l = 3;
	        if(tracepointsProperty.size() == 0)
	        	return ;

	        for(int i = 0; i < tracepointsProperty.size(); ++i) {
	        	Point2D pt = tracepointsProperty.get(i);

	        	if(pt != null) {
		            drawLine(pt.getX() - l, pt.getY() - l, pt.getX() + l, pt.getY() + l, 1d, Colors.ORANGE);
		            drawLine(pt.getX() - l, pt.getY() + l, pt.getX() + l, pt.getY() - l, 1d, Colors.ORANGE);
	        	}
	        }
	    }

    }

}
