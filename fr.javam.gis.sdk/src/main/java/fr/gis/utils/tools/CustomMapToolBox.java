package fr.gis.utils.tools;

import java.util.Collection;
import java.util.Collections;

import fr.gis.api.Gis;
import fr.gis.api.tools.MapToolBox;

public class CustomMapToolBox extends AbstractMapToolBox implements MapToolBox {
	Collection<Gis.Building>    buildings;
	Collection<Gis.Dynamics>    mobiles;

	public CustomMapToolBox(Collection<Gis.Building> _buildings, Collection<Gis.Dynamics> _mobiles) {
		super();

		buildings = _buildings == null ? Collections.emptyList() : _buildings;
		mobiles   = _mobiles   == null ? Collections.emptyList() : _mobiles;
	}

	@Override
	public final Collection<Gis.Building> 	getAllBuildings() {
		return buildings;
	}
	@Override
	public final Collection<Gis.Dynamics> 	getAllMobiles() {
		return mobiles;
	}

}
