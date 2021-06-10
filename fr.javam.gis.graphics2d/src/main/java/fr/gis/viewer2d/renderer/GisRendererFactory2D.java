package fr.gis.viewer2d.renderer;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.graphics.core.GisRendererFactory;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.renderer.layers.base.BaseRenderer2D;
import fr.gis.viewer2d.renderer.layers.gis.GisBuildingLayer2D;
import fr.gis.viewer2d.renderer.layers.road.RoadGeometryLayer2D;
import fr.gis.viewer2d.renderer.layers.road.RoadTopologyLayer2D;
import fr.gis.viewer2d.renderer.layers.road.RoadTrafficSignLayer2D;
import fr.gis.viewer2d.renderer.objects.GisArea2D;
import fr.gis.viewer2d.renderer.objects.GisCurve2D;
import fr.gis.viewer2d.renderer.objects.GisNode2D;
import fr.gis.viewer2d.renderer.objects.gis.GisDynamics2D;
import fr.gis.viewer2d.renderer.objects.gis.GisPath2D;

public class GisRendererFactory2D extends GisRendererFactory<GisServiceRenderer2D> {

	public GisRendererFactory2D() {
		super();

		// GIS OBJECT
		// ----------
		registerObjectRenderer			(Gis.Node.class, 			GisNode2D.class);
		registerObjectRenderer			(Gis.Curve.class, 			GisCurve2D.class);
		registerObjectRenderer			(Gis.Area.class, 			GisArea2D.class);

		registerObjectRenderer			(Gis.Dynamics.class, 		GisDynamics2D.class);
		registerObjectRenderer			(Gis.Path.class, 			GisPath2D.class);

		// GIS LAYER
		// ---------
		registerLayerRenderer			(GisLayer.BASE, 			BaseRenderer2D.class);
		registerLayerRenderer			(GisLayer.BUILDINGS, 		GisBuildingLayer2D.class);

		// ROAD LAYER
		// ---------
		registerLayerRenderer			(GisLayer.ROAD_GEOMETRY, 	RoadGeometryLayer2D.class);
		registerLayerRenderer			(GisLayer.ROAD_TOPOLOGY, 	RoadTopologyLayer2D.class);
		registerLayerRenderer			(GisLayer.ROAD_TRAFICSIGNS, RoadTrafficSignLayer2D.class);

	}

}
