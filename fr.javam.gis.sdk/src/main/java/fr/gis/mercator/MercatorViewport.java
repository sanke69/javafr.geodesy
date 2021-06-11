package fr.gis.mercator;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.mercator.Mercator;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.patterns.tiled.TileSystem;
import fr.java.sdk.patterns.tileable.TileViewportAdapter;

public class MercatorViewport extends TileViewportAdapter<BoundingBox.TwoDims, GeoCoordinate.Spheric2D> implements Mercator.Viewport {

	public MercatorViewport() {
		this(null);
	}
	public MercatorViewport(Dimension.TwoDims _window) {
		super(Mercator.GeoSphere, x -> Mercator.GeoSphere, new GudermannProjector(), new MercatorTileSystem(), _window);

		getGudermannProjector().setViewport(this);
	}
	public MercatorViewport(TileSystem _tileSystem, Dimension.TwoDims _window) {
		super(Mercator.GeoSphere, x -> Mercator.GeoSphere, new GudermannProjector(), _tileSystem, _window);

		getGudermannProjector().setViewport(this);
	}

	GudermannProjector getGudermannProjector() {
		return ((GudermannProjector) getModelProjector());
	}

}