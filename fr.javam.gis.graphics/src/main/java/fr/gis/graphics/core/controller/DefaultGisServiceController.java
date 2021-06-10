package fr.gis.graphics.core.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.GisServiceController;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.graphics.core.GisControllerFactory;
import fr.gis.graphics.core.controller.type.DefaultGisLayerTypeController;
import fr.java.utils.Collections;
import javafx.beans.Observable;
import javafx.beans.binding.ListBinding;
import javafx.beans.binding.MapBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class DefaultGisServiceController extends Control implements GisServiceController {

	public class ActiveLayersBinding extends ListBinding<GisLayer> {
		{ bind( activeProviders ); }

		@Override
		protected ObservableList<GisLayer> 	computeValue() {
			Collection<GisLayer> layers = 
					typeControllers  . values()
									   . stream()
									   . filter(ctrl -> ctrl.getActivation())
									   . flatMap(nfo -> nfo.selectedLayersProperty().stream())
									   . collect(Collectors.toCollection(() -> new TreeSet<GisLayer>(GisLayer.comparator)));

			return FXCollections.observableArrayList(layers);
		}

		public void 						bindTo(Observable... dependencies) {
			bind(dependencies);
		}
		public void 						unbindFrom(Observable... dependencies) {
			unbind(dependencies);
		}

	}
	public class ActiveLayersBinding2 extends MapBinding<GisLayer, GisRendererOption> {
		{ bind( activeProviders ); }

		@Override
		protected ObservableMap<GisLayer, GisRendererOption> 	computeValue() {
			Map<GisLayer, GisRendererOption> layers_with_options = null;
			
					typeControllers  . values()
									   . stream()
									   . filter(ctrl -> ctrl.getActivation())
									   . flatMap(nfo -> nfo.selectedLayersProperty().stream())
									   . collect(Collectors.toCollection(() -> new TreeSet<GisLayer>(GisLayer.comparator)));

			return FXCollections.observableMap(layers_with_options);
		}

		public void 						bindTo(Observable... dependencies) {
			bind(dependencies);
		}
		public void 						unbindFrom(Observable... dependencies) {
			unbind(dependencies);
		}

	}
	
	private final ObjectProperty<GisService>   				   					service;

	private final ListBinding<GisProvider> 				       					availableProviders;
	private final ListProperty<GisProvider>					   					activeProviders;

	private final ActiveLayersBinding						   					activeLayers;
	private final ActiveLayersBinding2											activeLayersWithOptions;

	private final ObservableMap<GisLayer.Type, DefaultGisLayerTypeController> 	typeControllers;

	private final GisControllerFactory 						   					factory;

	public DefaultGisServiceController() {
		this(null, null);
	}
	public DefaultGisServiceController(GisService _service) {
		this(_service, null);
	}
	public DefaultGisServiceController(GisControllerFactory _factory) {
		this(null, _factory);
	}
	public DefaultGisServiceController(GisService _service, GisControllerFactory _factory) {
		super();

		service    	       = new SimpleObjectProperty<GisService>();
		factory            = _factory;

		availableProviders = new ListBinding<GisProvider>() {
			{ bind( service ); }

			@Override
			protected ObservableList<GisProvider> computeValue() {
				if(service.get() == null)
					return FXCollections.observableArrayList();

				Collection<GisProvider> providers = service.get().getProviders();
				
				return FXCollections.observableArrayList(providers);
			}

		};
		activeProviders    = new SimpleListProperty<GisProvider>(FXCollections.observableArrayList());
		activeLayers       = new ActiveLayersBinding();
		activeLayersWithOptions = new ActiveLayersBinding2();

		typeControllers    = FXCollections.observableMap(new TreeMap<GisLayer.Type, DefaultGisLayerTypeController>  (GisLayer.Type.comparator));

		serviceProperty()
			. addListener((ChangeListener<? super GisService>)      ((ob, ol, srv) -> {activeProviders.setAll(srv.getProviders()); updateMapLayerTypeControllers();}));
		activeProvidersProperty()
			. addListener((ListChangeListener<? super GisProvider>) (      c      -> updateMapLayerTypeControllers()));

		if(_service != null)
			service.set(_service);
	}

	public GisControllerFactory									getFactory() {
		return factory;
	}

	public GisLayerController   								getLayerController(GisLayer _layer) {
		return getController(_layer.getType()).getController(_layer);
	}

	@Override
	protected Skin<?> 											createDefaultSkin() {
		return new DefaultGisServiceControllerSkin(this);
	}

	public final ObjectProperty<GisService>						serviceProperty() {
		return service;
	}
	public final void 											setService(GisService _service) {
		service.set(_service);
	}
	public final GisService 									getService() {
		return service.get();
	}

	public ListBinding<GisProvider>								availableProvidersProperty() {
		return availableProviders;
	}
	public ListProperty<GisProvider> 							activeProvidersProperty() {
		return activeProviders;
	}

	public ListBinding<GisLayer>								availableLayersProperty(GisLayer.Type _type) {
		return new ListBinding<GisLayer>() {
			{ bind( availableProviders, activeProviders ); }

			@Override
			protected ObservableList<GisLayer> computeValue() {
				Collection<GisLayer> layers = 
						activeProviders . stream()
										. flatMap(p -> p.getLayers(_type).stream())
										. distinct()
										. collect(Collectors.toCollection(() -> new TreeSet<GisLayer>(GisLayer.comparator)));
				return FXCollections.observableList(new ArrayList<GisLayer>(layers));
			}
			
		};
	}
	public ActiveLayersBinding									activeLayersProperty() {
		return activeLayers;
	}

	ObservableMap<GisLayer.Type, DefaultGisLayerTypeController>	typeControllersProperty() {
		return typeControllers;
	}

	DefaultGisLayerTypeController								getController(GisLayer.Type _type) {
		return typeControllers.get(_type);
	}

	private void												updateMapLayerTypeControllers() {
		Set<GisLayer.Type> existingTypes  = typeControllers.keySet().stream().collect(Collectors.toSet());
		Set<GisLayer.Type> activeTypes    = activeProviders.stream().flatMap(p -> p.getAvailableTypes().stream()).collect(Collectors.toSet());
		Set<GisLayer.Type> remainingTypes = new TreeSet<GisLayer.Type>(GisLayer.Type.comparator);

		for(GisLayer.Type type : activeTypes) {
			remainingTypes.add(type);

			if(typeControllers.get(type) == null) {
				DefaultGisLayerTypeController ctrl = new DefaultGisLayerTypeController(type, getService(), getFactory());

				ctrl.availableLayersProperty().bind( availableLayersProperty(type) );

				typeControllers . put(type, ctrl);
				activeLayers    . bindTo(ctrl.activationProperty());
				activeLayers    . bindTo(ctrl.selectedLayersProperty());

				getService().getDefault(type).ifPresent(dftLayer -> {
					ctrl.setSelectedLayer(dftLayer);	
					ctrl.setActivation(true);
				});
			}
		}

		Set<GisLayer.Type> suppressedTypes = Collections.asymDiff(existingTypes, remainingTypes);

		for(GisLayer.Type type : suppressedTypes) {
			DefaultGisLayerTypeController ctrl = typeControllers.get(type);

			typeControllers . remove(type);
			activeLayers    . unbindFrom(ctrl.activationProperty());
			activeLayers    . unbindFrom(ctrl.selectedLayersProperty());
		}
	}

}
