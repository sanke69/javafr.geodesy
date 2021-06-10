package fr.gis.viewer2d.renderer.layers.gis;

import java.util.Collection;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.api.GisLayerRenderer2D;
import fr.gis.viewer2d.renderer.objects.gis.GisDynamics2D;

public class GisDynamicsLayer2D implements GisLayerRenderer2D {
	GisDynamics2D dynamicsRenderer = new GisDynamics2D();

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		Collection<Gis.Dynamics> dynamics = _layer.getContent().getAllItems(Gis.Dynamics.class);

		for(Gis.Dynamics d : dynamics)
			dynamicsRenderer.renderObject(_renderer, d, null);
	}

}
