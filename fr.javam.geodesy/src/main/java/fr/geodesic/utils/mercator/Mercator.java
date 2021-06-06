package fr.geodesic.utils.mercator;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Projector;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileSystem;
import fr.java.sdk.math.Angles;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Coordinates;
import fr.java.sdk.patterns.tileable.TileSystemAdapter;
import fr.java.sdk.patterns.tileable.TileViewportAdapter;

public class Mercator {
	public static final BoundingBox.TwoDims	GeoSphere        = BoundingBoxes.fromBounds(-180.0, -85.051129, 180.0, 85.051129);
	public static final TileSystem			DefaultMapSystem = new TileSystemAdapter(256, 0, 19);

	public static class GudermannProjector implements Projector.TwoDims.Levelable<GeoCoordinate.Spheric2D, Coordinate.TwoDims> {
		private static final Function<Double, Double> gudermann = lat -> 2.0 * Math.atan(Math.exp(lat)) - Math.PI / 2.0;

		Mercator.Viewport 		viewport;

		Supplier<TileSystem> 	tileSystem;
		Supplier<Integer> 		level;

		protected GudermannProjector() {
			super();
		}
		public    GudermannProjector(Supplier<TileSystem> _tileSystem, Supplier<Integer> _level) {
			super();
			tileSystem = _tileSystem;
			level      = _level;
		}

		@Override
		public BoundingBox.TwoDims 			getKernel() {
			return GeoSphere;
		}
		@Override
		public BoundingBox.TwoDims 			getImage() { 
			return getImage( getLevel() );
		}
		public final BoundingBox.TwoDims 	getImage(int _level) { 
			long mapSize = getTileSystem().mapSize(_level); 
			return BoundingBoxes.of(0, 0, mapSize, mapSize);
		}

		@Override
		public GeoCoordinate.Spheric2D		toKernel(Coordinate.TwoDims _img) { 
			return toKernel(_img, getLevel());
		}
		@Override
		public GeoCoordinate.Spheric2D		toKernel(Coordinate.TwoDims _map_coords, int _map_level) {
			double MSZ = getTileSystem().mapSize(_map_level);
			double px  = _map_coords.getFirst() + getImage(_map_level).getMinX();
			double py  = _map_coords.getSecond() + getImage(_map_level).getMinY();

			double dlon = (px - MSZ/2d) / (MSZ / 360.0);
			double tlat = (py - MSZ/2d) / ( - MSZ / (2.0 * Math.PI));
			double dlat = Angles.Radian2Degree( gudermann.apply(tlat) );

			return GeoCoordinates.newGeoSpheric2D(dlon, dlat);
		}

		@Override
		public Coordinate.TwoDims 			toImage(GeoCoordinate.Spheric2D _ker) { 
			return toImage(_ker, getLevel());
		}
		@Override
		public Coordinate.TwoDims 			toImage(GeoCoordinate.Spheric2D _wgs84, int _atLevel) {
			long   MSZ = getTileSystem().mapSize(_atLevel);
			double LG  = _wgs84.getFirst(),
				   LT  = _wgs84.getSecond();

			double e = Math.sin(LT * (Math.PI / 180.0));
			e = e > 0.9999 ? 0.9999 : e < -0.9999 ? -0.9999 : e;

			double x = MSZ / 2.0 + LG * MSZ / 360.0;
			double y = MSZ / 2.0  - 0.5 * Math.log((1 + e) / (1 - e)) * MSZ / (2.0 * Math.PI);

			return Coordinates.of(x - getImage(_atLevel).getMinX(), y - getImage(_atLevel).getMinY());
		}

		int 								getLevel() {
			if( viewport != null )
				return viewport.getMapLevel();
			return level.get();
		}
		TileSystem 							getTileSystem() {
			return viewport.getTileSystem();
		}

	}

	public static class Viewport extends TileViewportAdapter<BoundingBox.TwoDims, GeoCoordinate.Spheric2D> {

		public Viewport() {
			this(null);
		}
		public Viewport(Dimension.TwoDims _window) {
			super(GeoSphere, x -> GeoSphere, new GudermannProjector(), DefaultMapSystem, _window);

			getGudermannProjector().viewport = this;
		}
		public Viewport(TileSystem _tileSystem, Dimension.TwoDims _window) {
			super(GeoSphere, x -> GeoSphere, new GudermannProjector(), _tileSystem, _window);

			getGudermannProjector().viewport = this;
		}
		
		GudermannProjector getGudermannProjector() {
			return ((GudermannProjector) getModelProjector());
		}

	}

}
