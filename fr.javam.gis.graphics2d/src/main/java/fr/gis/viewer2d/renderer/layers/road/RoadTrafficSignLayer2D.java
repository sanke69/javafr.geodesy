package fr.gis.viewer2d.renderer.layers.road;

import java.util.Collection;

import fr.gis.api.GisLayer;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.api.GisLayerRenderer2D;
import fr.gis.viewer2d.renderer.objects.road.RoadTraficSign2D;

public class RoadTrafficSignLayer2D implements GisLayerRenderer2D {
	RoadTraficSign2D roadSignRenderer = new RoadTraficSign2D();

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		Collection<Road.TraficSign> signs = _layer.getContent().getAllItems(Road.TraficSign.class);

		for(Road.TraficSign sign : signs)
			roadSignRenderer.renderObject(_renderer, sign, _opts);
	}

}
