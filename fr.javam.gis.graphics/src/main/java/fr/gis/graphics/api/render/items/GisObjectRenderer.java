package fr.gis.graphics.api.render.items;

import fr.gis.api.Gis;
import fr.gis.graphics.api.render.GisServiceRenderer;
import fr.gis.graphics.api.render.options.GisRendererOption;

public interface GisObjectRenderer<MR extends GisServiceRenderer<MR>> {

	public default 	void renderObject(MR _renderer, Gis.Object _object) 						{ renderObject(_renderer, _object, null); }
	public 			void renderObject(MR _renderer, Gis.Object _object, GisRendererOption _opts);

}
