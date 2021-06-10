package fr.gis.graphics.api.control;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.gis.graphics.core.GisControllerFactory;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface GisServiceController {

	public GisControllerFactory 		getFactory();

	public GisLayerController   		getLayerController(GisLayer _layer);

	public ObjectProperty<GisService>	serviceProperty();
	public ListBinding<GisProvider>		availableProvidersProperty();

	public ListProperty<GisProvider> 	activeProvidersProperty();
	public ListBinding<GisLayer>		activeLayersProperty();

}
