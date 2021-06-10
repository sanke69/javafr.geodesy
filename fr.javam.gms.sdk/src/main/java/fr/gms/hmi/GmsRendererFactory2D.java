package fr.gms.hmi;

import fr.gis.viewer2d.renderer.GisRendererFactory2D;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.sensor.EHorizon;
import fr.gms.hmi.ego.EgoObjectRenderer2D;
import fr.gms.hmi.ehorizon.EHorizonObjectRenderer2D;

public class GmsRendererFactory2D extends GisRendererFactory2D {

	public GmsRendererFactory2D() {
		super();

		registerObjectRenderer(EHorizon.class, 			EHorizonObjectRenderer2D.class);
		registerObjectRenderer(EHorizon.Oriented.class, EHorizonObjectRenderer2D.class);
		registerObjectRenderer(EgoVehicle.class, 		EgoObjectRenderer2D.class);

	}

}
