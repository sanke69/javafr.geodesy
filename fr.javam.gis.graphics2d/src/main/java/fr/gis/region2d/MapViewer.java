package fr.gis.region2d;

import java.util.Set;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.api.GisService.Registrable;
import fr.gis.graphics.api.render.items.GisLayerRenderer;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.renderer.GisRendererFactory2D;
import fr.java.lang.properties.ID;
import fr.javafx.scene.control.viewport.plugins.PlaneViewportBehavior;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

public class MapViewer extends Control { 
	ObjectProperty<GisService.Registrable>	service;
	GisServiceRenderer2D					renderer;

	GisRendererFactory2D					renderFactory;

	public MapViewer() {
		this(null, new GisRendererFactory2D());
	}
	public MapViewer(GisService.Registrable _service) {
		this(_service, new GisRendererFactory2D());
	}
	public MapViewer(GisRendererFactory2D _renderFactory) {
		this(null, _renderFactory);
	}
	public MapViewer(GisService.Registrable _service, GisRendererFactory2D _renderFactory) {
		super();

		service  = new SimpleObjectProperty<GisService.Registrable>(_service);

		renderFactory = _renderFactory;

		renderer = new GisServiceRenderer2D(_renderFactory);
		renderer . addEventFilter(MouseEvent.MOUSE_ENTERED, (me) -> renderer.requestFocus());
		renderer . getPlugins().add(new PlaneViewportBehavior<>());

		renderer . serviceProperty().bind(service);
	}

	public Skin<MapViewer> 							createDefaultSkin() {
		return new Skin<MapViewer>() {
			@Override public MapViewer 	getSkinnable() 	{ return MapViewer.this; }
			@Override public Node 		getNode() 		{ return renderer; }
			@Override public void 		dispose() 		{ }
		};
	}

	public void 									setService(GisService.Registrable _service) {
		service.set(_service);
	}

	public void 									setActivatedLayers(GisLayer... _layers) {
		renderer().activatedLayersProperty().setAll	( _layers );
	}
	public void 									addActivatedLayers(GisLayer... _layers) {
		renderer().activatedLayersProperty().addAll	( _layers );
	}

	public void 									setOptimizedFor(Set<Spheric2D> _waypoints) {
		renderer().getViewport().setOptimizedFor(_waypoints);
	}

	public ObjectProperty<GisService.Registrable> 	serviceProperty() {
		return service;
	}
	@Deprecated
	public ObjectProperty<GisService.Registrable> 	service() {
		return service;
	}
	public GisServiceRenderer2D 					renderer() {
		return renderer;
	}

	public final void 								registerObjectRenderer(Class<? extends Gis.Object> _mapObjectClass, Class<? extends GisObjectRenderer<GisServiceRenderer2D>> _mapObjectDrawerClass) {
		renderer.getFactory().registerObjectRenderer(_mapObjectClass, _mapObjectDrawerClass);
	}

	public final void 								registerLayerRenderer(Class<? extends GisLayer>   _mapLayerClass,  Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		renderer.getFactory().registerLayerRenderer(_mapLayerClass, _mapLayerDrawerClass);
	}
	public final void 								registerLayerRenderer(GisLayer.Type               _mapLayerType,   Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		renderer.getFactory().registerLayerRenderer(_mapLayerType, _mapLayerDrawerClass);
	}
	public final void 								registerLayerRenderer(ID                          _mapLayerId,     Class<? extends GisLayerRenderer<GisServiceRenderer2D>> _mapLayerDrawerClass) {
		renderer.getFactory().registerLayerRenderer(_mapLayerId, _mapLayerDrawerClass);
	}
	
	

}
