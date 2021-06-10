package fr.gis.utils.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.tools.MapToolBox;

public class DefaultMapToolBox extends AbstractMapToolBox implements MapToolBox {
	final GisProvider		provider;

	public DefaultMapToolBox(GisProvider _provider) {
		super();
		provider = _provider;
	}

	@Override
	public Collection<Gis.Dynamics> 		getAllMobiles() {
		return null;
	}
	@Override
	public Collection<Gis.Building> 		getAllBuildings() {
		if(provider == null
		|| provider.getLayers(GisLayer.BUILDINGS).isEmpty())
			return new ArrayList<Gis.Building>(); 

		return (Collection<Gis.Building>) 	provider.getLayers(GisLayer.BUILDINGS)
															   .stream()
															   .map(l -> l.getContent().getAllItems(Gis.Building.class).stream())
															   .flatMap(Function.identity())
															   .collect(Collectors.toSet());
	}

}
