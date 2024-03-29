package fr.gms.sdk.graphics.tests;

import fr.gms.planner.agent.TraceAgent;
import fr.gms.planner.agent.ant.AntAgentOriented;
import fr.gms.sdk.graphics.plugins.PvcPluginPathAndSpawn;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.javafx.JavaFX;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import fr.javafx.stage.StageExt;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DemoTraceAgent extends StageExt {

    public static void main(String[] args) {
    	JavaFX.launchWithArgs(StartApplication.class, "demoOpts");
    }

	public static class StartApplication extends Application {

	    public void start(Stage _stage) {
	    	getParameters();

	    	DemoTraceAgent stage = new DemoTraceAgent();
			stage.show();

			stage.plugin.loopProperty()       . addListener (evt -> { compute(stage); });
			stage.plugin.spawnProperty()      . addListener (evt -> { compute(stage); });
			stage.plugin.waypointsAProperty()  . addListener ((ListChangeListener<? super Point2D>) evt -> { compute(stage); });
	    }

	    public void compute(DemoTraceAgent _stage) {
	    	if(_stage.plugin.spawnProperty().get() == null || _stage.plugin.waypointsAProperty().size() < AntAgentOriented.MIN_PATH_SIZE)
	    		return ;

	    	TraceAgent agent = new AntAgentOriented(250, 10);
	    	Point2D[]  trace = agent.compute(_stage.plugin.spawnProperty().get(), new TraceAgent.Path(_stage.plugin.waypointsAProperty(), _stage.plugin.loopProperty().get()));

	    	if(trace != null)
	    		_stage.plugin.setTracepointsA(trace);

	    }

	}

    private Scene 								scene;
    private Pane 								root;
    private CanvasControl<Void, Point2D> 		canvas;
    private PvcPluginPathAndSpawn<?, Point2D> 	plugin;

    public DemoTraceAgent() {
    	this("DemoTrace", -1, -1);
    }
    public DemoTraceAgent(String _title) {
    	this(_title, -1, -1);
    }
    public DemoTraceAgent(int _width, int _height) {
    	this("DemoTrace", _width, _height);
    }
    public DemoTraceAgent(String _title, int _width, int _height) {
    	super();

    	plugin = new PvcPluginPathAndSpawn<>();
    	canvas = new CanvasControl<Void, Point2D>();
        root   = new BorderPane(canvas);
		scene  = _width > 0 && _height > 0 ? new Scene(root, _width, _height) : new Scene(root);

        canvas . setFocusTraversable ( true );
        canvas . prefWidthProperty()  . bind ( root.widthProperty() );
        canvas . prefHeightProperty() . bind ( root.heightProperty() );
        canvas . getViewport()        . setModel(null, (_null) -> BoundingBoxes.of(0, 0, canvas.getWidth(), canvas.getHeight()));
        canvas . getPlugins()         . add(plugin);

		setScene(scene);
//		setResizable(false);
		setTitle(_title);
    }

}
