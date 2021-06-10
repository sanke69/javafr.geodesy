package fr.gis.graphics.api.render.items;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.GisServiceRenderer;
import fr.gis.graphics.api.render.options.GisRendererOption;

public interface GisLayerRenderer<MR extends GisServiceRenderer<MR>> {

	public default void renderLayer(MR _renderer, GisLayer _layer) 						{ renderLayer(_renderer, _layer, null); }
	public 		   void renderLayer(MR _renderer, GisLayer _layer, GisRendererOption _opts);

}
