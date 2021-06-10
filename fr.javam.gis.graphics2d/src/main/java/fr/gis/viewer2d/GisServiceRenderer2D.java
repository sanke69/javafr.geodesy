package fr.gis.viewer2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.drawer.fx.DrawerFx;
import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.mercator.Mercator;
import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.GisServiceController;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.gis.graphics.api.render.GisServiceRenderer;
import fr.gis.graphics.api.render.items.GisLayerRenderer;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.graphics.core.GisRendererFactory;
import fr.gis.mercator.MercatorViewport;
import fr.gis.viewer2d.renderer.GisRendererFactory2D;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Colors;
import fr.java.lang.properties.ID;
import fr.java.math.geometry.BoundingBox;
import fr.java.maths.window.SimpleEdges2D;
import fr.javafx.scene.canvas.ResizableCanvas;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.StackPane;

public class GisServiceRenderer2D extends PlaneViewportControl<BoundingBox.TwoDims, GeoCoordinate.Spheric2D> implements GisServiceRenderer<GisServiceRenderer2D> {
	private ObjectProperty<GisService> 					serviceProperty;

	private GisServiceController 						controller;
	private GisRendererFactory<GisServiceRenderer2D>	factory;

	private ListProperty<GisLayer> 						activeLayersProperty;

	public static enum AvailableCanvas {
		MAPBASE, MAPDATA, OVERLAY; 
	}

	public GisServiceRenderer2D() {
		this(null, new GisRendererFactory2D());
	}
	public GisServiceRenderer2D(GisService _service) {
		this(_service, new GisRendererFactory2D());
	}
	public GisServiceRenderer2D(GisRendererFactory2D _factory) {
		this(null, _factory);
	}
	public GisServiceRenderer2D(GisService _service, GisRendererFactory2D _factory) {
		super(60, new MercatorViewport());

		serviceProperty      = new SimpleObjectProperty<GisService>(_service);
		factory        		 = _factory;

		activeLayersProperty = new SimpleListProperty<GisLayer>(FXCollections.observableArrayList());

		getViewport() . setEdges			(new SimpleEdges2D());
//		getViewport() . setWindow			(Boundables.of(this));

		updateViewScaleRange(15, 0.01, 5);
	}

	public Skin<GisServiceRenderer2D> 				createDefaultSkin() {
		return new GisServiceRenderer2DSkin();
	}

	public Drawer 									getDrawer(AvailableCanvas _canvas) {
		GisServiceRenderer2DSkin skin = (GisServiceRenderer2DSkin) getSkin();

		switch(_canvas) {
		case MAPBASE : return skin.mapbaseDrawer;
		case MAPDATA : return skin.mapdataDrawer;
		case OVERLAY : 
		default      : return skin.overlayDrawer;
		}
	}

	@Override
	public Mercator.Viewport 						getViewport() {
		return (Mercator.Viewport) super.getViewport();
	}

	public ObjectProperty<GisService> 				serviceProperty() {
		return serviceProperty;
	}
	public void	 									setService(GisService _mapService) {
		serviceProperty.set(_mapService);
	}
	public GisService 								getService() {
		return serviceProperty.get();
	}

	public void 									setController(GisServiceController _controller) {
		controller = _controller;
	}
	@Override
	public Optional<GisServiceController> 			getController() {
		return Optional.ofNullable(controller);
	}

	@Override
	public GisRendererFactory<GisServiceRenderer2D>	getFactory() {
		return factory;
	}

	@Override
	public void 									refresh() {
		Skin<?> skin = getSkin();
		if( skin instanceof GisServiceRenderer2DSkin )
			((GisServiceRenderer2DSkin) skin).refresh();
	}

	public final void 								registerObjectRenderer(Class<? extends Gis.Object> _mapObjectClass, Class<? extends GisObjectRenderer<GisServiceRenderer2D>> _mapObjectDrawerClass) {
		factory.registerObjectRenderer(_mapObjectClass, _mapObjectDrawerClass);
	}
	public final void 								registerLayerRenderer(Class<? extends GisLayer>   _mapLayerClass,  Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		factory.registerLayerRenderer(_mapLayerClass, _mapLayerDrawerClass);
	}
	public final void 								registerLayerRenderer(GisLayer.Type               _mapLayerType,   Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		factory.registerLayerRenderer(_mapLayerType, _mapLayerDrawerClass);
	}
	public final void 								registerLayerRenderer(ID                          _mapLayerId,     Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		factory.registerLayerRenderer(_mapLayerId, _mapLayerDrawerClass);
	}

	public ListProperty<GisLayer> 					activatedLayersProperty() {
		return activeLayersProperty;
	}

	protected void 									updateViewScaleRange(int _prefLevel, double _min, double _max) {
		if(getViewport() != null && getViewport().getTileSystem() != null) {
			getViewport().setPreferedMapLevel	(_prefLevel);
			getViewport().setMinScale 			(_min * Math.pow(0.5, getViewport().getPreferedMapLevel()));
			getViewport().setMaxScale 			(_max * Math.pow(0.5, getViewport().getPreferedMapLevel() - getViewport().getTileSystem().maxLevel()));
		}
	}

	class GisServiceRenderer2DSkin  extends StackPane implements Skin<GisServiceRenderer2D> {
		private final ResizableCanvas 	mapbaseCanvas, mapdataCanvas, overlayCanvas;
		private final DrawerFx 			mapbaseDrawer, mapdataDrawer, overlayDrawer;

		public GisServiceRenderer2DSkin() {
			super();
			mapbaseCanvas = new ResizableCanvas();
			mapbaseCanvas . widthProperty()  . bind(GisServiceRenderer2D.this.widthProperty());
			mapbaseCanvas . heightProperty() . bind(GisServiceRenderer2D.this.heightProperty());
			mapbaseDrawer = new DrawerFx(mapbaseCanvas);

			mapdataCanvas = new ResizableCanvas();
			mapdataCanvas . widthProperty()  . bind(GisServiceRenderer2D.this.widthProperty());
			mapdataCanvas . heightProperty() . bind(GisServiceRenderer2D.this.heightProperty());
			mapdataDrawer = new DrawerFx(mapdataCanvas);

			overlayCanvas = new ResizableCanvas();
			overlayCanvas . widthProperty()  . bind(GisServiceRenderer2D.this.widthProperty());
			overlayCanvas . heightProperty() . bind(GisServiceRenderer2D.this.heightProperty());
			overlayDrawer = new DrawerFx(overlayCanvas);


			
			setStyle("-fx-background-color: red;");
			getChildren().addAll(mapbaseCanvas, mapdataCanvas, overlayCanvas);
		}
	
		@Override
		public GisServiceRenderer2D getSkinnable() {
			return GisServiceRenderer2D.this;
		}
	
		@Override
		public Node 					getNode() {
			return this;
		}

		public void 					refresh() {
			mapbaseDrawer.clear(Colors.TURQUOISE);
			mapdataDrawer.clear(Colors.TRANSPARENT);
			overlayDrawer.clear(Colors.TRANSPARENT);

			if(getFactory() != null)
				repaint(activatedLayersProperty(), (Gis.Object[]) null);
		}

		@Override
		public void dispose() {
			;
		}

		public final void 								repaint(List<GisLayer> _layers, Gis.Object... _objects) {
			GisRendererFactory<GisServiceRenderer2D> renderers = getFactory();
			
			Collection<Gis.Object> remainingObjects = new ArrayList<Gis.Object>();
			if(_objects != null && _objects.length != 0)
				remainingObjects.addAll( Arrays.asList( _objects ) );

			if(_layers != null)
				for(GisLayer layer : _layers) {
					GisLayerController              		lcontroller = getController().map(c -> c.getLayerController(layer)).orElse(null);
					GisLayerRenderer<GisServiceRenderer2D> 	lrenderer   = renderers.getLayerRenderer(layer);

					if( lrenderer != null) {
						lrenderer.renderLayer(getSkinnable(), layer, lcontroller != null ? lcontroller.getRenderOptions() : null);

					} else {
						remainingObjects.addAll( layer.getContent().getAllItems() );

					}
				}

			if( ! remainingObjects.isEmpty() )
				for(Gis.Object elt : remainingObjects) {
					GisObjectRenderer<GisServiceRenderer2D> renderer = renderers.getObjectRenderer(elt.getClass());

					if(renderer != null)
						renderer.renderObject(getSkinnable(), (Gis.Object) elt);
				}

		}

	}

}
