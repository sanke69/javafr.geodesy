package fr.gis.viewer2d.renderer.layers.gis;

import java.util.Collection;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.api.GisLayerRenderer2D;
import fr.gis.viewer2d.renderer.objects.gis.GisBuilding2D;

public class GisBuildingLayer2D implements GisLayerRenderer2D {
	GisBuilding2D buildingRenderer = new GisBuilding2D();

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		Collection<Gis.Building> buildings = _layer.getContent().getAllItems(Gis.Building.class);

		for(Gis.Building b : buildings)
			buildingRenderer.renderObject(_renderer, b, null);
	}

}
