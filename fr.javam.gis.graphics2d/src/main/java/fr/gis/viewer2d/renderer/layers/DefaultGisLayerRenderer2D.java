package fr.gis.viewer2d.renderer.layers;

import java.util.Collection;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.api.GisLayerRenderer2D;

public class DefaultGisLayerRenderer2D implements GisLayerRenderer2D {

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		Collection<Gis.Object> objects = _layer.getContent().getAllItems();
	
		for(Gis.Object object : objects) {
			GisObjectRenderer<GisServiceRenderer2D> objectRenderer = _renderer.getFactory().getObjectRenderer(object.getClass());

			if(objectRenderer != null)
				objectRenderer . renderObject(_renderer, object, _opts);
		}
	}

}
