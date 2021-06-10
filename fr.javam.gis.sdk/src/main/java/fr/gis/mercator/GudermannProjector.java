package fr.gis.mercator;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.mercator.Mercator;
import fr.geodesic.utils.GeoCoordinates;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Angles;
import fr.java.maths.BoundingBoxes;
import fr.java.maths.Coordinates;
import fr.java.patterns.tileable.TileSystem;

public class GudermannProjector implements Mercator.Projector {
	private static final Function<Double, Double> gudermann = lat -> 2.0 * Math.atan(Math.exp(lat)) - Math.PI / 2.0;

	Mercator.Viewport 	viewport;
	TileSystem 			tileSystem;
	Supplier<Integer> 	level;

	public GudermannProjector() {
		super();
	}
	public GudermannProjector(TileSystem _tileSystem, Supplier<Integer> _level) {
		super();
		tileSystem = _tileSystem;
		level      = _level;
	}

	@Override
	public BoundingBox.TwoDims 			getKernel() {
		return Mercator.GeoSphere;
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
		if(tileSystem != null)
			return tileSystem;

		if(viewport == null)
			throw new NullPointerException();

		return viewport.getTileSystem();
	}

	void 								setViewport(Mercator.Viewport _viewport) {
		viewport = _viewport;
	}

}
