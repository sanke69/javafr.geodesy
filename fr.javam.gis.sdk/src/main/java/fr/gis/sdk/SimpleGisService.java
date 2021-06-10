package fr.gis.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.GisService;
import fr.gis.utils.GisUtils;
import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public class SimpleGisService implements GisService.Registrable {
	protected final Map<String, GisProvider> 		providers;
	protected final Map<GisLayer.Type, GisLayer>   	defaults;

	public SimpleGisService() {
		super();
		providers = new LinkedHashMap<String, GisProvider>();
		defaults  = new HashMap<GisLayer.Type, GisLayer>();

		loadServices();
	}

	@Override
	public GisService.Registrable 		registerProviders(Collection<GisProvider> _providers) {
		for(GisProvider provider : _providers)
			registerProvider(provider);
		return this;
	}
	@Override
	public GisService.Registrable		registerProvider(GisProvider _provider) {
		if(providers.containsKey(_provider.getName())) {
			System.err.println("Contains already a provider named '" + _provider.getName() + "'");

			System.err.println("Trying to add non existing layers in registered '" + _provider.getName() + "' provider.");
			GisProvider registered = providers.get(_provider.getName());
			for(GisLayer layer : _provider.getLayers())
				if(registered.getLayer(layer.getId()) == null)
					registered.getLayers().add(layer);

			return this;
		}

		providers.put(_provider.getName(), _provider);

		return this;
	}

	@Override
	public Collection<GisProvider> 		getProviders() {
		return providers.values();
	}
	public Collection<GisProvider> 		getProviders(boolean _reload) {
		if( providers.isEmpty() )
			loadServices();

		return providers.values();
	}
	@Override
	public GisProvider 					getProvider(String _name) {
		return providers.get(_name);
	}
	@Override
	public GisProvider.Registrable 		getProviderRegistrable(String _name) throws IllegalCastException {
		GisProvider provider = providers.get(_name);
		if(provider instanceof GisProvider.Registrable)
			return (GisProvider.Registrable) provider;
		throw new IllegalCastException();
	}
	@Override
	public GisProvider.Registrable 		getProviderRegistrable(String _name, boolean _createIfNotExist) throws IllegalCastException {
		GisProvider provider = providers.get(_name);
		if(provider == null)
			if(_createIfNotExist) {
				provider = GisUtils.createProvider(_name);
				registerProvider(provider);
			} else
				return null;
			
		if(provider instanceof GisProvider.Registrable)
			return (GisProvider.Registrable) provider;
		throw new IllegalCastException();
	}

	@Override
	public Set<GisLayer.Type> 			getLayerTypes() {
		return providers.values().stream()
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.map(l -> l.getType())
							.distinct()
							.collect(Collectors.toSet());
	}

	@Override
	public Collection<GisLayer> 		getLayers() {
		return providers.values().stream()
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.collect(Collectors.toList());
	}
	@Override
	public Collection<GisLayer> 		getLayers(String _providerName) {
		return providers.values().stream()
							.filter(p -> p.getName().compareToIgnoreCase(_providerName) == 0)
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.collect(Collectors.toList());
	}
	@Override
	public Collection<GisLayer> 		getLayers(String _providerName, GisLayer.Type _layerType) {
		return providers.values().stream()
							.filter(p -> p.getName().compareToIgnoreCase(_providerName) == 0)
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.filter(ml -> ml.getType() == _layerType)
							.collect(Collectors.toList());
	}
	@Override
	public Collection<GisLayer> 		getLayers(GisLayer.Type _layerType) {
		return providers.values().stream()
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.filter(ml -> ml.getType() == _layerType)
							.collect(Collectors.toList());
	}
	@Override
	public GisLayer 					getLayer(String _providerName, String _layerName) {
		return providers.values().stream()
							.filter(p -> p.getName().compareToIgnoreCase(_providerName) == 0)
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.filter(l -> l.getName().compareToIgnoreCase(_layerName) == 0)
							.findFirst().orElse(null);
	}
	@Override
	public GisLayer 					getLayer(String _layerName) {
		return providers.values().stream()
							.map(p -> p.getLayers().stream())
							.flatMap(Function.identity())
							.filter(l -> l.getName().compareToIgnoreCase(_layerName) == 0)
							.findFirst().orElse(null);
	}
	@Override
	public GisLayer 					getLayer(ID _layerId) {
		return providers.values().stream()
				.map(p -> p.getLayers().stream())
				.flatMap(Function.identity())
				.filter(l -> l.getId() == _layerId)
				.findFirst().orElse(null);
	}

	public void 						setDefaultLayer(GisLayer.Type _type, String _providerName, String _layerName) {
		if(!providers.containsKey(_providerName)) 
			throw new NullPointerException();

		GisProvider provider = providers.get(_providerName);

		if(provider.getLayer(_layerName) == null) 
			throw new NullPointerException();

		GisLayer layer =  provider.getLayer(_layerName);

		defaults.put(_type, layer);
	}
	public void 						setDefaultLayer(GisLayer.Type _type, ID _layerId) {
		providers.values().stream()
						  .map(p -> p.getLayers(_type))
						  .flatMap(Collection::stream)
						  .filter(l -> l.getId() == _layerId)
						  .findAny()
						  .ifPresent(l -> defaults.put(_type, l));
	}
	public void 						setDefaultLayer(GisLayer.Type _type, GisLayer _layer) {
		providers.values().stream()
						  .map(p -> p.getLayers(_type))
						  .flatMap(Collection::stream)
						  .filter(l -> l == _layer)
						  .findAny()
						  .ifPresent(l -> defaults.put(_type, l));
	}
	
	@Override
	public Optional<GisLayer> 			getDefault(GisLayer.Type _type) {
		return getDefault(_type, false);
	}
	@Override
	public Optional<GisLayer> 			getDefault(GisLayer.Type _type, boolean _findAny) {
		if( defaults.get(_type) != null )
			return Optional.of( defaults.get(_type) );

		for(GisProvider p :  providers.values()) {
			Optional<GisLayer> oLayer = p.getDefault(_type, false);
			
			if( oLayer.isPresent() )
				return oLayer;
		}
		
		if( ! _findAny )
			return Optional.empty();
		
		for(GisProvider p :  providers.values()) {
			Optional<GisLayer> oLayer = p.getDefault(_type, true);
			
			if( oLayer.isPresent() )
				return oLayer;
		}

		return Optional.empty();
	}

	void 								loadServices() {
		loadServices(false);
	}
	void 								loadServices(boolean _reload) {
		if(_reload)
			providers . clear();

		try {
			ServiceLoader.load(GisProvider.class)
						 .iterator()
						 .forEachRemaining(provider -> registerProvider( provider ));

        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }
	}

}
