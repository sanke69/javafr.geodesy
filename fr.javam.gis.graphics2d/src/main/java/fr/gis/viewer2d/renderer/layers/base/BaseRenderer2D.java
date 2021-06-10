package fr.gis.viewer2d.renderer.layers.base;

import fr.drawer.fx.DrawerFx;
import fr.drawer.fx.tiles.TileDrawerFx;
import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.GisServiceRenderer2D.AvailableCanvas;
import fr.gis.viewer2d.api.GisLayerRenderer2D;

public class BaseRenderer2D implements GisLayerRenderer2D {

	@Override
	public void renderLayer(GisServiceRenderer2D _renderer, GisLayer _layer, GisRendererOption _opts) {
		if( ! _layer.getType().hasTileProvider() )
			return ;
		
		new TileDrawerFx((DrawerFx) _renderer.getDrawer(AvailableCanvas.MAPBASE))
				.drawTiles( _renderer.getViewport(),
							_layer.getTileProvider() );
	}

}
