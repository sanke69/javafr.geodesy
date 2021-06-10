package fr.gms.planner.utils.hmi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.maths.interpolation.FunctionInterpolator;
import fr.java.maths.interpolation.functions.LagrangeInterpolator;
import fr.java.maths.interpolation.functions.LinearInterpolator;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSpline;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSplineMonotone;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSplineWithWeight;
import fr.javafx.JavaFX;
import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.XYChartPane;
import fr.javafx.scene.chart.axis.NumericAxis;
import fr.javafx.scene.chart.plugins.behavior.AbstractBehaviorPlugin;
import fr.javafx.scene.chart.plugins.behavior.ChartClicker;
import fr.javafx.scene.chart.plugins.behavior.ChartPanner;
import fr.javafx.scene.chart.plugins.behavior.ChartSelecter;
import fr.javafx.scene.chart.plugins.behavior.ChartZoomer;
import fr.javafx.scene.control.editor.BooleanEditor;
import fr.javafx.scene.control.editor.NumberEditor;
import fr.javafx.scene.control.editor.NumberEditor.Style;
import fr.javafx.stage.StageExt;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TraceAgentVisual extends StageExt {

    public static void main(String[] args) {
    	JavaFX.launch(StartApplication.class);
    }

	public static class StartApplication extends Application {

		@Override
	    public void start(Stage _stage) {
			TraceAgentVisual 
			stage = new TraceAgentVisual();
			stage . show();

			stage.setCurveNames("Control Points", "Interpolation1", "Interpolation2", "Interpolation3", "Interpolation4", "Interpolation5");

			// Install ClickPlugin
			Predicate<InputEvent> clickerFilter = evt -> { if(evt instanceof MouseEvent)  return ((MouseEvent) evt).isControlDown(); return false; };
			ChartClicker 		  clickerPlugin = new ChartClicker(clickerFilter);

			clickerPlugin.clickedPointProperty().addListener( (_obs, _old, _new) -> {
				if(_new != null) {
					stage.curve(0).getData().add(new Data<Number, Number>(_new.getX(), _new.getY()));

					double[][] interps = compute( stage.curve(0).getData() );

					if(interps != null)
						for(int i = 1; i < interps.length; ++i)
							stage.setCurveData(i, interps[0], interps[i]);
/*
					stage.setCurveStyles (stage, new XY.Series.Style(Color.BLUE,   0d, null, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT),
												 new XY.Series.Style(Color.GREEN,  1d, null, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT),
												 new XY.Series.Style(Color.ORANGE, 1d, null, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT),
												 new XY.Series.Style(Color.YELLOW, 1d, null, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT),
												 new XY.Series.Style(Color.RED,    1d, null, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT) );
*/
				}
			} );

			stage.chart().getPlugins().addAll( Arrays.asList( clickerPlugin ) );

			// Add some options
//			stage.addOptionNumber("Test1", 1, 0, 999);
			stage.optionProperty("Test1").ifPresent(p -> p.addListener((_obs, _old, _new) -> System.out.println("Test1 is " + _new)));

			//
	    }

		public double[][] 	compute(ObservableList<Data<Number, Number>> _waypoints) {
			int nPoints = _waypoints.size();
			int nInterp = 123;

			if(nPoints <= 2)
				return null;

			double[] xs = _waypoints.stream().mapToDouble(pt -> pt.getXValue().doubleValue()).toArray();
			double[] ys = _waypoints.stream().mapToDouble(pt -> pt.getYValue().doubleValue()).toArray();

			FunctionInterpolator.OneVar F = new LinearInterpolator(xs, ys);
			FunctionInterpolator.OneVar G = new LagrangeInterpolator(xs, ys);
			FunctionInterpolator.OneVar H = new CubicSpline(xs, ys);
			FunctionInterpolator.OneVar I = new CubicSplineWithWeight(xs, ys, 1);
			FunctionInterpolator.OneVar J = new CubicSplineMonotone(xs, ys);

			double[] x  = new double[nInterp], 
					 yf = new double[nInterp],
					 yg = new double[nInterp],
					 yh = new double[nInterp],
					 yi = new double[nInterp],
					 yj = new double[nInterp];

			double step = (double) ((xs[xs.length - 1] - xs[0])) / (nInterp - 1);

			for(int i = 0; i < nInterp; i++) {
				x[i]  = xs[0] + i * step;
				yf[i] = F.evaluate( x[i] );
				yg[i] = G.evaluate( x[i] );
				yh[i] = H.evaluate( x[i] );
				yi[i] = I.evaluate( x[i] );
				yj[i] = J.evaluate( x[i] );
			}

			return new double[][] { x, yf, yg, yh, yi, yj };
		}
		public double[][] 	computeDemo() {
			int nPoints = 100;
			int nInterp = 1000;
	
			if(nPoints <= 2)
				return null;

			double[] x1 = new double[nPoints], 
					 y1 = new double[nPoints],
					 x2 = new double[nInterp], 
					 ya = new double[nInterp],
					 yb = new double[nInterp],
					 yc = new double[nInterp];

			for(int i = 0; i < nPoints; ++i) {
				x1[i] = i * 32;
				y1[i] = (int) (i / 100d);
			}
			for(int i = 0; i < 1000; ++i) {
				x2[i] = i;
				ya[i] = 1e2 * Math.cos( 2d * Math.PI * (i / 100d) );
				yb[i] = 1e2 * Math.sin( 2d * Math.PI * (i / 100d) );
				yc[i] = 1e2 * ((int) (i / 100d)) * Math.sin( 2d * Math.PI * (i / 10d) );
			}
			
			return new double[][] { x1, y1, x2, ya, yb, yc };
		}

	}

	private final Scene     									scene;
	private final BorderPane     								root;

	private final XYChartPane<Number, Number>					chart;
	private final NumericAxis									xAxis, yAxis;

	private final int							  				curveCapacity = 10;
	private final ObservableList<XYChart.Series<Number,Number>> curves;

	private final ObservableMap<String, Node>					options;
	private final GridPane										optionPane;

	public TraceAgentVisual() {
		this("XYChartFx", -1, -1);
	}
	public TraceAgentVisual(String _title) {
		this(_title, -1, -1);
	}
    public TraceAgentVisual(int _width, int _height) {
    	this("XYChartFx", _width, _height);
    }
    public TraceAgentVisual(String _title, int _width, int _height) {
    	super();

    	// DATA
    	ObservableList<XYChart.Series<Number,Number>> tmpCurves = FXCollections.observableArrayList();
    	for(int i = 0; i < curveCapacity; ++i)
    		tmpCurves.add(new XYChart.Series<Number,Number>());
    	curves = FXCollections.unmodifiableObservableList(tmpCurves);

		// HMI
		xAxis        = xAxis();
		yAxis        = yAxis();
		chart        = XYChartPane.of(chartSkin(), xAxis(), yAxis());
        root         = new BorderPane(chart());
		scene        = _width > 0 && _height > 0 ? new Scene(root, _width, _height) : new Scene(root);

		// OPTIONS
		options      = FXCollections.observableHashMap();
    	optionPane   = leftPane().get();

		// Configure XYChartPane
    	initializeBindings();
		initializePlugIns();
		initializeSeries();

		leftPane()   . ifPresent(root::setLeft);
		rightPane()  . ifPresent(root::setRight);
		topPane()    . ifPresent(root::setTop);
		bottomPane() . ifPresent(root::setBottom);

		setScene(scene);
//		setResizable(false);
		setTitle(_title);

        options().addListener((MapChangeListener<? super String, ? super Node>) c -> {
    		String name = c.getKey();
    		Node   node = null;

        	if((node = c.getValueAdded()) != null) {
        		Label 
        		label = new Label(name);

        		optionPane.add(label, 0, 0);
        		optionPane.add(node,  1, 0);

        	} else if((node = c.getValueRemoved()) != null) {
        		
        	}

        	if(c.getKey() != null) {
        		
        	}
        });
        optionPane.getChildren().addListener((ListChangeListener<? super Node>) c -> {
        	if(c.getList().size() > 0) {
        		optionPane.setVisible(true);
                optionPane.setManaged(true);
        	} else {
        		optionPane.setVisible(false);
                optionPane.setManaged(false);
        	}
        });
    }

	public NumericAxis									  	xAxis() {
		if(xAxis != null)
			return xAxis;

		NumericAxis 
		xAxis = new NumericAxis();
		xAxis . setAnimated(false);
		xAxis . setSide(Side.BOTTOM);
		xAxis . setAutoRanging(false);
		xAxis . setLowerBound(0);
		xAxis . setUpperBound(20);
//		xAxis . setTickUnit(25);

		return xAxis;
	}
	public NumericAxis									  	yAxis() {
		if(yAxis != null)
			return yAxis;
		
		NumericAxis
		yAxis = new NumericAxis();
		yAxis . setSide(Side.LEFT);
		yAxis . setAutoRanging(false);
		yAxis . setLowerBound(0);
		yAxis . setUpperBound(1000);
	
		return yAxis;
	}
	public XYChartPane<Number, Number> 					  	chart() {
		if(chart != null)
			return chart;

		XYChartPane<Number, Number> chart = XYChartPane.of(chartSkin(), xAxis(), xAxis());
		return chart;
	}
	public XY.Type 										  	chartSkin() {
		return XY.Type.Area;
	}

	public ObservableList<XYChart.Series<Number,Number>>  	curves() {
		return curves;
	}
	public XYChart.Series<Number,Number>    			  	curve(int _id) {
		if(_id < 0 || _id > curveCapacity - 1)
			return null;

		return curves.get(_id);
	}

	public void 											setCurveNames(String... _names) {
		for(int i = 0; i < _names.length && i < curveCapacity; ++i)
			setCurveName(i, _names[i]);
	}
	public void 											setCurveStyles(XY.Series.Style... _names) {
		for(int i = 0; i < _names.length && i < curveCapacity; ++i)
			setCurveStyle(i, _names[i]);
	}

	public void    										  	setCurve(int _id, String name, double[] _xs, double[] _ys) {
		if(_id < 0 || _id > curveCapacity - 1)
			return ;

		List<Data<Number, Number>> datas = IntStream.range(0, _xs.length)
											.boxed()
											.map(i -> new Data<Number, Number>(_xs[i], _ys[i]))
											.collect(Collectors.toList());

		curves.get(_id).setName(name);
		curves.get(_id).getData().setAll(datas);
	}
	public void    										  	setCurve(int _id, String name, double[] _xs, double[] _ys, XY.Series.Style _style) {
		if(_id < 0 || _id > curveCapacity - 1)
			return ;

		List<Data<Number, Number>> datas = IntStream.range(0, _xs.length)
											.boxed()
											.map(i -> new Data<Number, Number>(_xs[i], _ys[i]))
											.collect(Collectors.toList());

		curves.get(_id).setName(name);
		curves.get(_id).getData().setAll(datas);
		chart().setStyle(curves.get(_id), _style);
	}
	public void    										  	setCurveName(int _id, String name) {
		if(_id < 0 || _id > curveCapacity - 1)
			return ;

		curves.get(_id).setName(name);
	}
	public void 										  	setCurveData(int _id, double[] _xs, double[] _ys) {
		if(_id < 0 || _id > curveCapacity - 1)
			return ;

		List<Data<Number, Number>> datas = IntStream.range(0, _xs.length)
											.boxed()
											.map(i -> new Data<Number, Number>(_xs[i], _ys[i]))
											.collect(Collectors.toList());
		curves.get(_id).getData().setAll(datas);
	}
	public void 										  	setCurveStyle(int _id, XY.Series.Style _style) {
		if(_id < 0 || _id > curveCapacity - 1)
			return ;

		chart().setStyle(curves.get(_id), _style);
	}

	public ObservableMap<String, Node>					  	options() {
		return options;
	}
	public Node											  	optionNode(String _name) {
		return options.get(_name);
	}
	public Optional<Property<?>>						  	optionProperty(String _name) {
		Node node = options.get(_name);

		if(node instanceof BooleanEditor) {
			BooleanProperty property = ((BooleanEditor) node).valueProperty();
			return Optional.of(property);
		}
		if(node instanceof NumberEditor) {
			ObjectProperty<Number> property = ((NumberEditor) node).valueProperty();
			return Optional.of(property);
		}

		return Optional.empty();
	}
	public void											  	addOption(String _name, Node _node) {
		if( options.put(_name, _node) != null )
			System.err.println("WARNING:: A previous option with same name already exists...");
	}
	public void											  	addOptionBoolean(String _name, boolean _value) {
		BooleanEditor
		node = new BooleanEditor(BooleanEditor.Style.CheckBox);
		node . setPrefWidth(127d);
		node . setValue (_value);

		addOption(_name, node);
	}
	public void											  	addOptionNumber(String _name, int _value, int _min, int _max) {
		NumberEditor
		node = new NumberEditor(Integer.class, Style.Simple);
		node . setPrefWidth(127d);
		node . setValue (_value);
		node . setMin   (_min);
		node . setMax   (_max);
		node . setStep  (1);

		addOption(_name, node);
	}
	public void											  	addOptionNumber(String _name, double _value, double _min, double _max) {
		NumberEditor
		node = new NumberEditor(Double.class, Style.Simple);
		node . setPrefWidth(127d);
		node . setValue (_value);
		node . setMin   (_min);
		node . setMax   (_max);
		node . setStep  (1);

		addOption(_name, node);
	}
	public void											  	removeOption(String _name) {
		if( options.remove(_name) == null )
			System.err.println("WARNING:: No option named '" + _name + "' in options...");
	}

	protected Optional<GridPane> 						  	leftPane()   {
		if(optionPane != null)
			return Optional.of(optionPane);
			
		GridPane optionPane = new GridPane();

        optionPane.setVisible(false);
        optionPane.setManaged(false);

		return Optional.of(optionPane);
	}
	protected Optional<Node> 							  	rightPane()  { return Optional.empty(); }
	protected Optional<Node> 							  	topPane()    { return Optional.empty(); }
	protected Optional<Node> 							  	bottomPane() { return Optional.empty(); }

	protected void 										  	initializeBindings() {
		for(int i = 0; i < curveCapacity; ++i) {

			final XYChart.Series<Number,Number> curve = curve(i);

			curve.dataProperty().addListener((_obs, _old, _new) -> {
				if(_new.size() == 0)
					chart().getData().remove(curve);
				else if( !chart().getData().contains(curve) )
					chart().getData().add(curve);
			});
			curve.getData().addListener((ListChangeListener<? super Data<Number, Number>>) c -> {
				if(c.getList().size() == 0)
					chart().getData().remove(curve);
				else if( !chart().getData().contains(curve) )
					chart().getData().add(curve);
			});

		}
	}
	protected void 										  	initializePlugIns() {
		Predicate<InputEvent> 		pannerFilter    = evt -> { if(evt instanceof MouseEvent)  return ((MouseEvent)  evt).isSecondaryButtonDown(); return false; };
		Predicate<InputEvent> 		zoomFilter      = evt -> { if(evt instanceof ScrollEvent) return ((ScrollEvent) evt).isControlDown(); return false; };
		Predicate<InputEvent> 		selectionFilter = evt -> { if(evt instanceof MouseEvent)  return ((MouseEvent)  evt).isShiftDown();   return false; };

		ChartPanner   				pannerPlugin    = new ChartPanner(pannerFilter);
		ChartZoomer<Number, Number> zoomPlugin      = new ChartZoomer<Number, Number>(zoomFilter);
		ChartSelecter 				selectionPlugin = new ChartSelecter(selectionFilter);
		InputController 			optionPlugin    = new InputController(curve(0).getData());

		selectionPlugin.selectionProperty().addListener( (_obs, _old, _new) -> {
			_new = chart.toPlotArea(_new);

			double xmin  = xAxis().getRange().lowerBound();
			double xmax  = xAxis().getRange().upperBound();
			double ymin  = yAxis().getRange().lowerBound();
			double ymax  = yAxis().getRange().upperBound();

			Point2D pt = chart().toPlotArea(new Point2D(_new.getMinX(), _new.getMinY()));
			Point2D PT = chart().toPlotArea(new Point2D(_new.getMaxX(), _new.getMaxY()));
			double x     = pt.getX(), X = PT.getX();
			double y     = pt.getY(), Y = PT.getY();
			double w     = chart.getPlotAreaBounds().getWidth();
			double h     = chart.getPlotAreaBounds().getHeight();

			double rxmin = xmin + x / w * (xmax - xmin);
			double rymin = ymax - Y / h * (ymax - ymin);
			double rxmax = xmin + X / w * (xmax - xmin);
			double rymax = ymax - y / h * (ymax - ymin);

			Rectangle2D zoom = new Rectangle2D(rxmin, rymin, rxmax - rxmin, rymax - rymin);

			ChartZoomer.performZoom(chart, zoom, XY.Constraint.BOTH); 
			} );

		chart().getPlugins().addAll( Arrays.asList( pannerPlugin, zoomPlugin, optionPlugin, selectionPlugin ) );

	}
	protected ObservableList<XYChart.Series<Number,Number>>	initializeSeries() {
    	ObservableList<XYChart.Series<Number,Number>> tmpCurves = FXCollections.observableArrayList();
    	for(int i = 0; i < curveCapacity; ++i)
    		tmpCurves.add(new XYChart.Series<Number,Number>("series"+i, FXCollections.observableArrayList()));
    	return tmpCurves;
	}

	public static class InputController extends AbstractBehaviorPlugin<Number, Number> {
		private ObservableList<Data<Number,Number>> waypoints;

        public InputController(ObservableList<Data<Number,Number>> _waypoints) {
        	super();
        	waypoints = _waypoints;
            registerKeyEventHandler(KeyEvent.KEY_RELEASED,  this::keyPressed);
		}

        public void keyPressed(KeyEvent _ke) {
        	switch(_ke.getCode()) {
    		case C	: waypoints.clear(); break;
    		case L	: break;
    		case R	: break;
    		default	: break;
        	}
        }

    }

}
