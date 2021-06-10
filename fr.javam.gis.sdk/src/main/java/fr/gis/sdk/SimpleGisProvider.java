package fr.gis.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.tools.MapToolBox;
import fr.gis.utils.tools.DefaultMapToolBox;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;
import fr.java.sdk.log.LogInstance;
import fr.java.util.collections.HashSetWithDefault;

public class SimpleGisProvider implements GisProvider.Registrable {
	private static final LogInstance log = LogInstance.getLogger();

	protected final ID                      							id;
	protected final String      										name, shortname;

//	protected final Set<GisLayer.Type> 									types;
	protected final Map<GisLayer.Type, HashSetWithDefault<GisLayer>>  	layers;
	
	protected MapToolBox												mapTools;

	public SimpleGisProvider(String _name) {
		super();
		id        = IDs.random(32);
		name      = _name;
		shortname = _name.substring(0, 2);
//		types     = new HashSet<GisLayer.Type>();
		
		layers = new HashMap<GisLayer.Type, HashSetWithDefault<GisLayer>>();
	}

	@Override
	public ID 									getId() {
		return id;
	}
	@Override
	public final String 						getName() {
		return name;
	}
	@Override
	public final String 						getShortName() {
		return shortname;
	}

	@Override
	public final Set<? extends GisLayer.Type> 	getAvailableTypes() {
		return layers.keySet();
	}

	@Override
	public final Collection<GisLayer> 			getLayers() {
		return layers.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
	}
	@Override
	public final Collection<GisLayer> 			getLayers(GisLayer.Type _type) {
		return layers.get(_type) != null ? layers.get(_type).stream()
					 									    .collect(Collectors.toSet()) 
					 					   : 
					 					   java.util.Collections.<GisLayer>emptySet();
	}

	@Override
	public GisLayer 							getLayer(ID _id) {
		return layers.values().stream().flatMap(Collection::stream).filter(l -> l.getId() == _id).findFirst().orElse(null);
	}
	@Override
	public GisLayer 							getLayer(String _name) {
		return layers.values().stream()
							  .flatMap(Collection::stream).filter(l -> l.getName().compareToIgnoreCase(_name) == 0)
							  .findFirst().orElse(null);
	}

	public void									setDefault(GisLayer.Type _type, GisLayer _layer) {
		if(! layers.get(_type).contains(_layer) )
			throw new IllegalArgumentException();

		layers.get(_type).setDefault(_layer);
	}

	@Override
	public Optional<GisLayer>					getDefault(GisLayer.Type _type) {
		return getDefault(_type, false);
	}
	@Override
	public Optional<GisLayer>					getDefault(GisLayer.Type _type, boolean _findAny) {
		HashSetWithDefault<GisLayer> typeLayers = layers . get(_type);

		if(typeLayers == null)
			return Optional.<GisLayer>empty();

		return typeLayers.getDefault()
						 .or(() -> _findAny ? getLayers(_type).stream().findFirst() : Optional.<GisLayer>empty());
	}

	public Optional<MapToolBox>					getMapToolbox() {
		if(mapTools == null)
			mapTools = new DefaultMapToolBox(this);
		return Optional.of(mapTools);
	}

	@Override
	public final GisProvider.Registrable 		registerLayers(Collection<GisLayer> _layers) {
		for(GisLayer layer : _layers)
			registerLayer(layer, false);
		return this;
	}
	@Override
	public final GisProvider.Registrable 		registerLayers(GisLayer... _layers) {
		for(GisLayer layer : _layers)
			registerLayer(layer, false);
		return this;
	}
	@Override
	public final GisProvider.Registrable 		registerLayer(GisLayer _layer) {
		return registerLayer(_layer, false);
	}
	@Override
	public final GisProvider.Registrable 		registerLayer(GisLayer _layer, boolean _setAsDefault) {
		HashSetWithDefault<GisLayer> typeLayers = layers . get(_layer.getType());

		if(typeLayers == null) {
			typeLayers = new HashSetWithDefault<GisLayer>();
			layers.put(_layer.getType(), typeLayers);
		}

		if(typeLayers.contains(_layer))
			log.error("A layer named '{}' already exists, we will erase it", _layer.getName());

		typeLayers.add(_layer, _setAsDefault);
		
		return this;
	}

}
