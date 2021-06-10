package fr.gis.utils.tools.road;

import java.util.Collection;
import java.util.Collections;

import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.api.road.tools.engines.MapMatchEngine;
import fr.gis.api.road.tools.engines.ShortPathEngine;
import fr.gis.utils.tools.road.mapmatching.DefaultMapMatcher;
import fr.gis.utils.tools.road.shortpath.dijkstra.DijkstraSPE;

public class CustomRoadToolBox extends AbstractRoadToolBox implements RoadToolBox {
	Collection<Gis.Building>    buildings;
	Collection<Gis.Dynamics>    mobiles;
	Collection<Road.Element>    road;
	Collection<Road.TraficSign> trafficSigns;

	MapMatchEngine 	    		mmEngine;
	ShortPathEngine 	    	spEngine;

	public CustomRoadToolBox(Collection<Gis.Building> _buildings, Collection<Gis.Dynamics> _mobiles, Collection<Road.Element> _road, Collection<Road.TraficSign> _trafficSigns) {
		super();

		road         = _road         == null ? Collections.emptyList() : _road;
		buildings    = _buildings    == null ? Collections.emptyList() : _buildings;
		trafficSigns = _trafficSigns == null ? Collections.emptyList() : _trafficSigns;
		mobiles      = _mobiles      == null ? Collections.emptyList() : _mobiles;

		mmEngine     = new DefaultMapMatcher( this );
		spEngine     = new DijkstraSPE( null );
	}

	@Override
	public Collection<Road.Element> 		getAllRoadElements() {
		return road;
	}
	@Override
	public Collection<Gis.Building> 		getAllBuildings() {
		return buildings;
	}
	@Override
	public Collection<Road.TraficSign> 		getAllTrafficSigns() {
		return trafficSigns;
	}
	@Override
	public Collection<Gis.Dynamics> 		getAllMobiles() {
		return mobiles;
	}

	@Override
	public MapMatchEngine 					getMapMatchEngine() {
		return mmEngine;
	}
	public ShortPathEngine 					getShortPathEngine() {
		return spEngine;
	}

}
