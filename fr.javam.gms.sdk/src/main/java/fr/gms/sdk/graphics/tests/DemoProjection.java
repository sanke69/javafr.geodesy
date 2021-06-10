package fr.gms.sdk.graphics.tests;

import fr.gms.sdk.graphics.plugins.PvcPluginProjection;
import fr.java.math.geometry.plane.Point2D;
import fr.javafx.JavaFX;
import fr.javafx.scene.control.viewport.planar.implementations.canvas.CanvasControl;
import fr.javafx.stage.StageExt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DemoProjection extends StageExt {

    public static void main(String[] args) {
    	JavaFX.launchWithArgs(StartApplication.class, "demoOpts");
    }

	public static class StartApplication extends Application {

	    public void start(Stage _stage) {
	    	DemoProjection stage = new DemoProjection();
			stage.show();
	    }

	}

    private Scene 							scene;
    private Pane 							root;
    private CanvasControl<?, Point2D> 		canvas;

    public DemoProjection() {
    	this("DemoProjection", 800, 600);
    }
    public DemoProjection(String _title) {
    	this(_title, -1, -1);
    }
    public DemoProjection(int _width, int _height) {
    	this("DemoProjection", _width, _height);
    }
    public DemoProjection(String _title, int _width, int _height) {
    	super();

    	canvas = new CanvasControl<Object, Point2D>();
        root   = new BorderPane(canvas);
		scene  = _width > 0 && _height > 0 ? new Scene(root, _width, _height) : new Scene(root);

        canvas.setFocusTraversable ( true );
        canvas.prefWidthProperty()  . bind ( root.widthProperty() );
        canvas.prefHeightProperty() . bind ( root.heightProperty() );

        canvas.getPlugins().add(new PvcPluginProjection<>());

		setScene(scene);
//		setResizable(false);
		setTitle(_title);
    }

}