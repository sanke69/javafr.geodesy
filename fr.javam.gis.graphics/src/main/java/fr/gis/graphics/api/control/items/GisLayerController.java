package fr.gis.graphics.api.control.items;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;

public interface GisLayerController {

	public GisLayer 			getLayer();
	public GisRendererOption	getRenderOptions();

}
