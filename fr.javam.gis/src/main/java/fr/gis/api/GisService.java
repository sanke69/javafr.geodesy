package fr.gis.api;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public interface GisService {

	public static interface Registrable extends GisService {
		public GisProvider.Registrable				getProviderRegistrable(String _providerName, boolean _createIfNotExist) throws IllegalCastException;

		public GisService.Registrable 				registerProviders(Collection<GisProvider> providers);
		public GisService.Registrable				registerProvider(GisProvider _provider);

	}

//	public void										reload();

	// Providers
	public Collection<GisProvider>					getProviders();

	public GisProvider								getProvider(String _providerName);
	public GisProvider.Registrable					getProviderRegistrable(String _providerName) throws IllegalCastException;

	// Layers
	public Set<GisLayer.Type> 						getLayerTypes();

	public Collection<GisLayer> 					getLayers();
	public Collection<GisLayer> 					getLayers(GisLayer.Type _layerType);
	public Collection<GisLayer> 					getLayers(String _providerName);
	public Collection<GisLayer> 					getLayers(String _providerName, GisLayer.Type _layerType);

	public GisLayer 								getLayer(String _providerName, String _layerName);
	public GisLayer 								getLayer(String _layerName);
	public GisLayer 								getLayer(ID _layerId);

	public Optional<GisLayer>						getDefault(GisLayer.Type _type);
	public Optional<GisLayer>						getDefault(GisLayer.Type _type, boolean _findAny);

}
