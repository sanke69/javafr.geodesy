package fr.gis.viewer2d.renderer.layers.road;

import java.util.Collection;

import fr.gis.api.GisLayer;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.api.GisLayerRenderer2D;
import fr.gis.viewer2d.renderer.objects.road.RoadElement2D;

public class RoadTopologyLayer2D implements GisLayerRenderer2D {
	RoadElement2D roadEltRenderer = new RoadElement2D();

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		Collection<Road.Element> elements = _layer.getContent().getAllItems(Road.Element.class);

		for(Road.Element elt : elements)
			roadEltRenderer.renderObject(_renderer, elt, GisRendererOption.of("render", "graph"));
	}

}
