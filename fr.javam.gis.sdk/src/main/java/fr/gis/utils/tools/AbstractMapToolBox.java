package fr.gis.utils.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.api.tools.MapToolBox;
import fr.java.maths.geometry.plane.shapes.SimpleEllipse2D;

public abstract class AbstractMapToolBox implements MapToolBox {

	protected AbstractMapToolBox() {
		super();
	}

	@Override
	public final Collection<Gis.Dynamics> 		getSurroundingMobiles		(GeoCoordinate _p, double _radius) {
		return null;
	}
	@Override
	public final Collection<Gis.Building> 		getSurroundingBuildings		(GeoCoordinate _p, double _radius) {
		Set<Gis.Building> 	buildings 			= new HashSet<Gis.Building>();
		SimpleEllipse2D 	influenceArea 		= new SimpleEllipse2D(_p.asUTM().as2D(), _radius, _radius);
		SimpleEllipse2D 	influenceAreaTwiced = new SimpleEllipse2D(_p.asUTM().as2D(), 2 * _radius, 2 * _radius);

		for(Gis.Building building : getAllBuildings()) {
			if( influenceAreaTwiced.contains(building.getOutline().get(0).asUTM().as2D()) )
				for(GeoCoordinate pt : building.getOutline())
					if(	influenceArea.contains(pt.asUTM().as2D()) )
						buildings.add(building);
		}

		return buildings;
	}

}
