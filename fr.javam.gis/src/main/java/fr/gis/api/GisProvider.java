package fr.gis.api;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import fr.java.lang.properties.ID;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.capabilities.Nameable;

public interface GisProvider extends Identifiable, Nameable {

	public static interface Registrable extends GisProvider {
		public GisProvider.Registrable 	registerLayers (Collection<GisLayer> _layers);
		public GisProvider.Registrable 	registerLayers (GisLayer...          _layers);
		public GisProvider.Registrable	registerLayer  (GisLayer             _layer);
		public GisProvider.Registrable	registerLayer  (GisLayer             _layer, boolean _setAsDefault);
	}

	public String 							getShortName();

	public Set<? extends GisLayer.Type> 	getAvailableTypes();

	public Collection<GisLayer> 			getLayers		();
	public Collection<GisLayer> 			getLayers		(GisLayer.Type _type);

	public GisLayer 						getLayer		(String _name);
	public GisLayer 						getLayer		(ID _id);

	public Optional<GisLayer> 				getDefault		(GisLayer.Type _type);
	public Optional<GisLayer> 				getDefault		(GisLayer.Type _type, boolean _findAny);

}
