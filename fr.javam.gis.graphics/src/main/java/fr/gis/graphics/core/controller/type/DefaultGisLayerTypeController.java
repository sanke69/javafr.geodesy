package fr.gis.graphics.core.controller.type;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.gis.graphics.core.GisControllerFactory;
import fr.java.utils.Collections;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Skin;
import javafx.scene.control.TitledPane;

public class DefaultGisLayerTypeController extends TitledPane {
	private final GisService   									service;
	private final GisLayer.Type									type;

	final protected BooleanProperty 	 						activationProperty;
	final protected ListProperty<GisLayer> 						availableLayers;
	final protected ListProperty<GisLayer> 						activeLayers;

	private final ObservableMap<GisLayer, GisLayerController> 	layerControllers;

	private final GisControllerFactory 						   	factory;

	public DefaultGisLayerTypeController(GisLayer.Type _type, GisService _service, GisControllerFactory _factory) {
		super();
		type      		   = _type;
		service 	       = _service;
		factory            = _factory;

		activationProperty = new SimpleBooleanProperty();

		availableLayers    = new SimpleListProperty<GisLayer>(FXCollections.observableArrayList());
		activeLayers       = new SimpleListProperty<GisLayer>(FXCollections.observableArrayList());

		layerControllers   = FXCollections.observableMap(new TreeMap<GisLayer, GisLayerController>  (GisLayer.comparator));

		availableLayersProperty()
			. addListener((ListChangeListener<? super GisLayer>) (c -> updateMapLayerControllers()));
		selectedLayersProperty()
			. addListener((ListChangeListener<? super GisLayer>) (c -> updateMapLayerControllers()));

		setSkin( createDefaultSkin() );
	}

	@Override
	protected Skin<?> 								createDefaultSkin() {
		return new DefaultGisLayerTypeControllerSkin(this, getType() != GisLayer.BASE);
	}

	public GisLayer.Type 							getType() {
		return type;
	}
	public GisService 								getService() {
		return service;
	}

	public void 									setActivation(boolean _enable) {
		activationProperty.set(_enable);
	}
	public boolean 									getActivation() {
		return activationProperty.get();
	}
	public BooleanProperty 							activationProperty() {
		return activationProperty;
	}

	public ListProperty<GisLayer>   				availableLayersProperty() {
		return availableLayers;
	}

	public void 									setSelectedLayer(GisLayer _layer) {
		activeLayers.add(_layer);
	}
	public void 									setSelectedLayers(GisLayer... _layers) {
		activeLayers.addAll(_layers);
	}
	public void 									setSelectedLayers(Collection<GisLayer> _layers) {
		activeLayers.addAll(_layers);
	}
	public void 									setSelectedLayers(ObservableList<GisLayer> _layers) {
		activeLayers.addAll(_layers);
	}
	public List<GisLayer> 							getSelectedLayers() {
		return activeLayers.get();
	}
	public ListProperty<GisLayer> 					selectedLayersProperty() {
		return activeLayers;
	}

	ObservableMap<GisLayer, GisLayerController>		layerControllersProperty() {
		return layerControllers;
	}

	public GisLayerController						getController(GisLayer _layer) {
		return layerControllers.get(_layer);
	}
	public GisControllerFactory						getFactory() {
		return factory;
	}

	private void									updateMapLayerControllers() {
		Set<GisLayer> existingLayers  = layerControllers.keySet().stream().collect(Collectors.toSet());
		Set<GisLayer> remainingLayers = new TreeSet<GisLayer>(GisLayer.comparator);

		for(GisLayer layer : activeLayers) {
			remainingLayers.add(layer);

			if(layerControllers.get(layer) == null && getFactory() != null) {
				GisLayerController ctrl = getFactory().newInstance(layer);
				if(ctrl != null)
					layerControllers . put(layer, ctrl);
			}
		}

		Set<GisLayer> suppressedTypes = Collections.asymDiff(existingLayers, remainingLayers);

		for(GisLayer layer : suppressedTypes) {
			GisLayerController ctrl = layerControllers.get(layer);
			if(ctrl != null)
				layerControllers . remove(layer);
		}
	}

}
