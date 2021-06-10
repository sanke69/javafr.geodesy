package fr.gis.graphics.api.render;

import java.util.Optional;

import fr.gis.graphics.api.control.GisServiceController;
import fr.gis.graphics.core.GisRendererFactory;

public interface GisServiceRenderer<MR extends GisServiceRenderer<MR>> {

	public Optional<GisServiceController> 	getController();
	
	public GisRendererFactory<MR> 			getFactory();

}
