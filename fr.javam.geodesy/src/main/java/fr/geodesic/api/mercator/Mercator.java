package fr.geodesic.api.mercator;

import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileViewport;
import fr.utils.geodesic.BoundingBoxes;

public interface Mercator {
	public static final BoundingBox.TwoDims	GeoSphere = BoundingBoxes.fromBounds(-180.0, -85.051129, 180.0, 85.051129);

	public static interface Projector extends fr.java.math.geometry.Projector.TwoDims.Levelable<GeoCoordinate.Spheric2D, Coordinate.TwoDims> {
		public static final Function<Double, Double> gudermann = lat -> 2.0 * Math.atan(Math.exp(lat)) - Math.PI / 2.0;
	}

	public interface Viewport extends TileViewport.Editable<BoundingBox.TwoDims, GeoCoordinate.Spheric2D> {
	}

}
