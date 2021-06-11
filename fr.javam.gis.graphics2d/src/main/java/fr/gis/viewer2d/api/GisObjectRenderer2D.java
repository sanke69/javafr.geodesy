package fr.gis.viewer2d.api;

import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.gis.api.Gis;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.GisServiceRenderer2D.AvailableCanvas;
import fr.java.draw.Drawer;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.Points;

public interface GisObjectRenderer2D<GIS_OBJECT extends Gis.Object> extends GisObjectRenderer<GisServiceRenderer2D> {

	@SuppressWarnings("unchecked")
	@Override
	public default void renderObject(GisServiceRenderer2D _renderer, Gis.Object _object, GisRendererOption _opts) {
		if( !isDrawable(_object) )
			return ;

		drawObject2D(_renderer.getDrawer(AvailableCanvas.MAPDATA), 
				 	 _renderer.getViewport().getWindow(),
				 	 p -> Points.of(_renderer.getViewport().modelInWindow(p)), 
				 	 (GIS_OBJECT) _object, 
				 	 _opts);
	}

	boolean isDrawable(Gis.Object _object);

	void    drawObject2D(Drawer                       _drawer, 
						 Dimension.TwoDims            _window, 
						 Function<Spheric2D, Point2D> _projector, 
						 GIS_OBJECT                   _object, 
						 GisRendererOption            _options);

}
