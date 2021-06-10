package fr.gms.planner.utils.hmi;

import java.util.stream.IntStream;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;
import fr.java.maths.interpolation.FunctionInterpolator;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSplineMonotone;
import fr.javafx.JavaFX;
import fr.javafx.io.screen.Screens;
import javafx.application.Application;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;

public class TraceAgentDemo {

    public static void main(String[] args) {
    	JavaFX.launchWithArgs(StartApplication.class, "demoOpts");
    }

	public static class StartApplication extends Application {
		TraceAgentMapDrawer mapStage;
		TraceAgentVisual    xGraphStage, 
							yGraphStage;

	    public void start(Stage stage) {
	    	getParameters();

	    	mapStage    = new TraceAgentMapDrawer("Map Drawer");
	    	mapStage    . show();
			xGraphStage = new TraceAgentVisual("X Viewer");
			xGraphStage . show();
			yGraphStage = new TraceAgentVisual("Y Viewer");
			yGraphStage . show();

            xGraphStage.setCurveNames("WayPoints (X)", "Interpolation");
            yGraphStage.setCurveNames("WayPoints (Y)", "Interpolation");

			initBindings();
            initDesktop();

            mapStage.spawnProperty     . addListener((_obs, _old, _new) -> compute());
            mapStage.waypointsProperty . addListener((_obs, _old, _new) -> compute());
	    }

	    public void initBindings() {
			ListBinding<Data<Number, Number>> xListBinding = new ListBinding<Data<Number, Number>>() {
                { bind( mapStage.waypointsProperty ); }

                @Override
                protected ObservableList<Data<Number, Number>> computeValue() {
                    ObservableList<Data<Number, Number>> result = FXCollections.observableArrayList();

                    int count = 0;
                    for(Point2D point : mapStage.waypointsProperty())
                        result.add( new Data<Number, Number>(count++, point.getX()) );

                    xGraphStage.xAxis().setUpperBound(result.size() < 20 ? 20 : result.size());

                    return result;
                }

            };
			ListBinding<Data<Number, Number>> yListBinding = new ListBinding<Data<Number, Number>>() {
                { bind( mapStage.waypointsProperty ); }

                @Override
                protected ObservableList<Data<Number, Number>> computeValue() {
                    ObservableList<Data<Number, Number>> result = FXCollections.observableArrayList();
                    
                    int count = 0;
                    for(Point2D point : mapStage.waypointsProperty())
                        result.add( new Data<Number, Number>(count++, point.getY()) );

                    yGraphStage.xAxis().setUpperBound(result.size() < 20 ? 20 : result.size());

                    return result;
                }

            };

            xGraphStage.curve(0).dataProperty().bind(xListBinding);
            yGraphStage.curve(0).dataProperty().bind(yListBinding);

	    }
	    public void initDesktop() {
	    	Rectangle2D monitor0 = Screens.getScreenVisualBounds(0);

	    	mapStage    .setPosition (0, 0);
	    	mapStage    .setSize     (monitor0.getWidth()/2d, monitor0.getHeight());
	    	
	    	xGraphStage .setPosition (monitor0.getWidth()/2d, 0);
	    	xGraphStage .setSize     (monitor0.getWidth()/2d, monitor0.getHeight()/2);

	    	yGraphStage .setPosition (monitor0.getWidth()/2d, monitor0.getHeight()/2);
	    	yGraphStage .setSize     (monitor0.getWidth()/2d, monitor0.getHeight()/2);

            xGraphStage .yAxis().setUpperBound(monitor0.getWidth() / 2d);
            yGraphStage .yAxis().setUpperBound(monitor0.getHeight());
	    }

	    private void compute() {
	    	Point2D   _spawn     = mapStage.spawnProperty.get();
	    	Point2D[] _waypoints = mapStage.waypointsProperty.toArray(new Point2D[0]);

//	    	computeV1(_spawn, _waypoints, false);
	    	computeV0(_spawn, _waypoints);
	    }

	    private void computeV0(Point2D _spawn, Point2D[] _waypoints) {
        	if(_spawn == null || _waypoints.length < 3)
        		return ;

        	double[] T = IntStream.range(0, _waypoints.length).boxed()	. mapToDouble(Integer::doubleValue).toArray();
        	double[] X = mapStage.waypointsProperty().stream()			. mapToDouble(Point2D::getX).toArray();
        	double[] Y = mapStage.waypointsProperty().stream()			. mapToDouble(Point2D::getY).toArray();

//			new LinearInterpolator(T, _) - new LagrangeInterpolator(T, _) - new CubicSpline(T, _) - new CubicSplineWithWeight(T, _, 1) - new CubicSplineMonotone(x, y);
			FunctionInterpolator.OneVar FX = new CubicSplineMonotone(T, X);
	    	FunctionInterpolator.OneVar FY = new CubicSplineMonotone(T, Y);

			int      nInterp = 100;
			double[] Ti      = new double[nInterp];
        	double[] Xi      = new double[nInterp];
        	double[] Yi      = new double[nInterp];
			double   step    = (double) ((T[T.length - 1] - T[0])) / (nInterp - 1d);

			for(int i = 0; i < nInterp; i++) {
				Ti[i] = T[0] + i * step;
				Xi[i] = FX.evaluate( Ti[i] );
				Yi[i] = FY.evaluate( Ti[i] );
			}

			Point2D[] trace = IntStream.range(0, nInterp).boxed() . map(i -> Points.of(Xi[i], Yi[i])).toArray(Point2D[]::new);

			mapStage.tracepointsProperty.setAll(trace);
			xGraphStage.setCurveData(1, Ti, Xi);
			yGraphStage.setCurveData(1, Ti, Yi);
	    }

	}

}
