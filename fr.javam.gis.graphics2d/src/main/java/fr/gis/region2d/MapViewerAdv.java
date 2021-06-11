package fr.gis.region2d;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.GisServiceController;
import fr.gis.graphics.core.GisControllerFactory;
import fr.gis.graphics.core.controller.DefaultGisServiceController;
import fr.gis.graphics.sdk.control.AbstractGisLayerController;
import fr.gis.viewer2d.renderer.GisRendererFactory2D;
import fr.java.lang.properties.ID;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.Points;
import fr.javafx.scene.control.viewport.planar.utils.info.PlaneViewportInfoControl;
import fr.javafx.scene.control.viewport.planar.utils.minimap.PlaneViewportMinimapControl;
import fr.javafx.scene.control.viewport.utils.statusbar.ViewportStatusBar;
import fr.javafx.utils.FxImageUtils;
import fr.media.image.utils.BufferedImages;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MapViewerAdv extends MapViewer {
	BorderPane					rootPane;
	VBox						debugPane, actionPane;
	ViewportStatusBar			statusBar;

	GisServiceController		rendererControllers;
	PlaneViewportMinimapControl rendererMinimap;
	PlaneViewportInfoControl	rendererInfos;

	GisControllerFactory 		controlFactory;

	BooleanProperty				actionActivated, debugActivated;

	public MapViewerAdv() {
		this(null, new GisRendererFactory2D(), new GisControllerFactory());
	}
	public MapViewerAdv(GisService.Registrable _service) {
		this(_service, new GisRendererFactory2D(), new GisControllerFactory());
	}
	public MapViewerAdv(GisRendererFactory2D _renderFactory, GisControllerFactory _controlFactory) {
		this(null, _renderFactory, _controlFactory);
	}
	public MapViewerAdv(GisService.Registrable _service, GisRendererFactory2D _renderFactory, GisControllerFactory _controlFactory) {
		super(_service, _renderFactory);

		controlFactory = _controlFactory;

		actionActivated = new SimpleBooleanProperty(true);
		debugActivated  = new SimpleBooleanProperty(true);

		rootPane = new BorderPane();
		rootPane.setCenter (renderer);
		rootPane.setLeft   (debugPane  = initDebugPane(true, true, true));
		rootPane.setRight  (actionPane = initUserActionPane());
		rootPane.setBottom (statusBar  = initStatusBar());

		initListeners();
	}

	public Skin<MapViewer> 				createDefaultSkin() {
		return new Skin<MapViewer>() {
			@Override public MapViewer 	getSkinnable() 	{ return MapViewerAdv.this; }
			@Override public Node 		getNode() 		{ return rootPane; }
			@Override public void 		dispose() 		{ }
		};
	}

	public GisServiceController 		getControllers() {
		if(rendererControllers != null)
			return rendererControllers;
		
		rendererControllers = new DefaultGisServiceController(controlFactory);

		rendererControllers . serviceProperty().bind(service);

		renderer . activatedLayersProperty().bindContent( rendererControllers.activeLayersProperty() );

		DefaultGisServiceController
		ctrl = (DefaultGisServiceController) rendererControllers;
		ctrl . setPrefWidth(320);

		VBox.setVgrow(ctrl, Priority.ALWAYS);

		return rendererControllers;
	}
	public PlaneViewportMinimapControl 	getMiniMap() {
		if(rendererMinimap != null)
			return rendererMinimap;

		rendererMinimap = new PlaneViewportMinimapControl(renderer);
		rendererMinimap . setPrefSize(320, 240);

		VBox.setVgrow(rendererMinimap, Priority.NEVER);

		try { 
			final Path  earth = Paths.get( System.getProperty("resources_path") + "projects/VirtualUniverse/resources/universe/milky-way/sun/Earth/Earth.png" );
			final Paint paint = new ImagePattern(FxImageUtils.toFX(BufferedImages.loadImage(earth)));
			rendererMinimap . setModelPaint(paint);
		} catch(IOException e) { ; }

		return rendererMinimap;
	}
	public PlaneViewportInfoControl 	getViewportInfos() {
		if(rendererInfos != null)
			return rendererInfos;

		rendererInfos = new PlaneViewportInfoControl( renderer() );
		rendererInfos . setPrefWidth(320);
		VBox.setVgrow(rendererInfos, Priority.ALWAYS);

		renderer().addEventFilter(MouseEvent.MOUSE_MOVED, (me) -> {
			Point2D mouse = Points.of(me.getX(), me.getY());
			rendererInfos.mouseProperty().set(mouse);
	    });

		return rendererInfos;
	}

	public void 						addAction(Node _node) {
		actionPane.getChildren().add( _node );
	}

	public final void 					registerLayerController(Class<? extends GisLayer> _layerClass,     Class<? extends AbstractGisLayerController> _layerController) {
		getControllers().getFactory().registerLayerController(_layerClass, _layerController);
	}
	public final void 					registerLayerController(GisLayer.Type 	          _layerType,      Class<? extends AbstractGisLayerController> _layerController) {
		getControllers().getFactory().registerLayerController(_layerType, _layerController);
	}
	public final void 					registerLayerController(ID                        _layerId,        Class<? extends AbstractGisLayerController> _layerController) {
		getControllers().getFactory().registerLayerController(_layerId, _layerController);
	}

	private final VBox 					initDebugPane(boolean _useController, boolean _useInfos, boolean _useMinimap) {
		VBox
		leftPane = new VBox();
		leftPane . setPrefWidth(320);

		if(_useController)
			leftPane . getChildren().add( (DefaultGisServiceController) getControllers() );

		if(_useInfos)
			leftPane . getChildren().add( getViewportInfos() );

		if(_useMinimap)
			leftPane . getChildren().add( getMiniMap() );

		return leftPane;
	}
	private final VBox 					initUserActionPane() {
		VBox
		rightPane = new VBox();
		rightPane . setPrefWidth(240);
		rightPane . setAlignment(Pos.TOP_CENTER);

		Label label  = new Label("Actions:");

		try { 
			Font font = Font.loadFont("file:" + System.getProperty("resources_path") + "/common/fonts/iAWriterDuospace-Regular.ttf", 45);
			if(font != null)
				label.setFont(font);
		} catch(Exception e) { ; }

		Pane  space  = new Pane();
		space.setPrefHeight(27d);

		rightPane.getChildren().add( label );
		rightPane.getChildren().add( space );

		return rightPane;
	}
	private final ViewportStatusBar 	initStatusBar() {
		Separator 
		leftSep  = new Separator(Orientation.VERTICAL),
		rightSep = new Separator(Orientation.VERTICAL);
		leftSep  . setPadding(new Insets(0, 5, 0, 5));
		rightSep . setPadding(new Insets(0, 5, 0, 5));

		statusBar = new ViewportStatusBar(renderer);
		statusBar . setText("");
		statusBar . getLeftItems().add(leftSep);
		statusBar . getRightItems().add(0, rightSep);

		return statusBar;
	}

	void initListeners() {
		renderer.addEventFilter(MouseEvent.MOUSE_ENTERED, (me) -> renderer.requestFocus());
		rootPane.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressedHandler4Hmi);

		actionActivated . addListener((_obs, _old, _new) -> {
			if(_new) {
				actionPane.setVisible(true);
				actionPane.setManaged(true);
			} else {
				actionPane.setVisible(false);
				actionPane.setManaged(false);
			}
		});
		debugActivated . addListener((_obs, _old, _new) -> {
			if(_new) {
				debugPane.setVisible(true);
				debugPane.setManaged(true);
			} else {
				debugPane.setVisible(false);
				debugPane.setManaged(false);
			}
		});
	}

	public void    		keyPressedHandler4Hmi(KeyEvent e) {
		if (e.isConsumed())
			return;

		switch(e.getCode()) {
		case A			: 	actionActivated.set(!actionActivated.get());
							break;
		case D			: 	debugActivated.set(!debugActivated.get());
							break;
		default			: 	return;
		}
		e.consume();
	}

}
