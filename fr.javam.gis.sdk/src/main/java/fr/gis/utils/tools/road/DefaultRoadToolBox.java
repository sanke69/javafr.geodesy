package fr.gis.utils.tools.road;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.road.Road;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.api.road.tools.engines.MapMatchEngine;
import fr.gis.api.road.tools.engines.ShortPathEngine;
import fr.gis.utils.tools.road.mapmatching.DefaultMapMatcher;
import fr.gis.utils.tools.road.shortpath.dijkstra.DijkstraSPE;

public class DefaultRoadToolBox extends AbstractRoadToolBox implements RoadToolBox {
	final GisProvider	provider;

	MapMatchEngine 		mmEngine;
	ShortPathEngine 	spEngine;

	public DefaultRoadToolBox(GisProvider _provider) {
		super();
		provider = _provider;
		mmEngine = new DefaultMapMatcher( this );
		spEngine = new DijkstraSPE(null);
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
	@Override
	public Collection<Road.Element> 		getAllRoadElements() {
		if(provider == null
		|| provider.getLayers(GisLayer.ROAD_GEOMETRY).isEmpty())
			return new ArrayList<Road.Element>(); 

		return (Collection<Road.Element>) 	provider.getLayers(GisLayer.ROAD_GEOMETRY)
														   .stream()
														   .map(l -> l.getContent().getAllItems(Road.Element.class).stream())
														   .flatMap(Function.identity())
														   .collect(Collectors.toSet());
	}
	@Override
	public Collection<Road.TraficSign> 		getAllTrafficSigns() {
		if(provider == null
		|| provider.getLayers(GisLayer.ROAD_TRAFICSIGNS).isEmpty())
			return new ArrayList<Road.TraficSign>(); 

		return (Collection<Road.TraficSign>) 	provider.getLayers(GisLayer.ROAD_TRAFICSIGNS)
															   .stream()
															   .map(l -> l.getContent().getAllItems(Road.TraficSign.class).stream())
															   .flatMap(Function.identity())
															   .collect(Collectors.toSet());
	}

	public MapMatchEngine 					getMapMatchEngine() {
		return mmEngine;
	}
	public ShortPathEngine 					getShortPathEngine() {
		return spEngine;
	}

}
