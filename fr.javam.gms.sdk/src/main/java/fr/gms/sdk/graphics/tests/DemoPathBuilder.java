package fr.gms.sdk.graphics.tests;

import fr.gms.navigation.waypath.WayPath;
import fr.gms.planner.agent.TraceAgent;
import fr.gms.planner.agent.ant.AntAgentOriented;
import fr.gms.sdk.graphics.plugins.PvcPluginPathAndSpawn;
import fr.gms.sdk.navigation.waypath.WayPaths;
import fr.gms.sdk.utils.PathBuilder;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;
import fr.javafx.JavaFX;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import fr.javafx.stage.StageExt;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DemoPathBuilder extends StageExt {

    public static void main(String[] args) {
    	JavaFX.launchWithArgs(StartApplication.class, "demoOpts");
    }

	public static class StartApplication extends Application {

	    public void start(Stage _stage) {
	    	getParameters();

	    	DemoPathBuilder stage = new DemoPathBuilder();
			stage.show();

			stage.plugin.stepProperty()       . addListener (evt -> { compute(stage); });
			stage.plugin.loopProperty()       . addListener (evt -> { compute(stage); });
			stage.plugin.spawnProperty()      . addListener (evt -> { compute(stage); });
			stage.plugin.waypointsAProperty() . addListener ((ListChangeListener<? super Point2D>) evt -> { compute(stage); });
			stage.plugin.waypointsBProperty() . addListener ((ListChangeListener<? super Point2D>) evt -> { compute(stage); });
			stage.plugin.useBProperty()       . addListener (evt -> { compute(stage); });
	    }

	    public void compute(DemoPathBuilder _stage) {
	    	if(_stage.plugin.useBProperty().get())
	    		computeWithPathBuilderMulti(_stage);
	    	else
	    		computeWithPathBuilderMono(_stage);
	    }

	    public void computeWithPathBuilderMono(DemoPathBuilder _stage) { System.out.println("compute mono");
	    	if(_stage.plugin.waypointsAProperty().size() < 2)
	    		return ;

			WayPath.Single wps = new PathBuilder().buildMonoTrack(WayPaths.newSimpleTrip(_stage.plugin.waypointsAProperty()), _stage.plugin.stepProperty().get());

			if(wps != null)
	    		_stage.plugin.setTracepointsA(wps.getWayPoints());
			else
				 System.err.println("no result");
	    }
	    public void computeWithPathBuilderMulti(DemoPathBuilder _stage) { System.out.println("compute multi");
	    	PvcPluginPathAndSpawn<?, Point2D> 	plugin = _stage.plugin;
    		plugin.setTracepointsA((Point2D[])null);
    		plugin.setTracepointsB((Point2D[])null);

	    	if(plugin.waypointsAProperty().size() < 2)
	    		return ;

	    	if(plugin.waypointsBProperty().size() < 2)
	    		return ;

			WayPath.Single[] wps = new PathBuilder().buildMultiTrack(	WayPaths.newSimpleTrip(plugin.waypointsAProperty()), 
																		plugin.stepProperty().get(), 
																		WayPaths.newSimpleTrip(plugin.waypointsBProperty())  );

			 System.err.println(wps.length);
			if(wps != null && wps.length >= 2) {
				 System.err.println("bingo");
	    		_stage.plugin.setTracepointsA(wps[0].getWayPoints());
	    		_stage.plugin.setTracepointsB(wps[1].getWayPoints());
			} else
	    		_stage.plugin.setTracepointsA(wps[0].getWayPoints());
	    }

	    public void computeWithAntAgent(DemoPathBuilder _stage) {
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

    public DemoPathBuilder() {
    	this("DemoPathBuilder", -1, -1);
    }
    public DemoPathBuilder(String _title) {
    	this(_title, -1, -1);
    }
    public DemoPathBuilder(int _width, int _height) {
    	this("DemoPathBuilder", _width, _height);
    }
    public DemoPathBuilder(String _title, int _width, int _height) {
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
