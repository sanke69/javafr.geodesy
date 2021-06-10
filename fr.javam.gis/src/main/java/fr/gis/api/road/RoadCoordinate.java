package fr.gis.api.road;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.java.lang.properties.ID;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileCoordinate;

public interface RoadCoordinate extends GeoCoordinate {

	public interface Cartesian2D extends RoadCoordinate, 				GeoCoordinate.Cartesian2D {

		public interface Editable extends RoadCoordinate.Cartesian2D, RoadCoordinate.Editable, GeoCoordinate.Cartesian2D.Editable {

		}

	}
	public interface Cartesian3D extends RoadCoordinate.Cartesian2D, 	GeoCoordinate.Cartesian3D {

		public interface Editable extends RoadCoordinate.Cartesian3D, RoadCoordinate.Cartesian2D.Editable, GeoCoordinate.Cartesian3D.Editable {

		}

	}
	public interface Spheric2D   extends RoadCoordinate, 				GeoCoordinate.Spheric2D {

		public interface Editable extends RoadCoordinate.Spheric2D, RoadCoordinate.Editable, GeoCoordinate.Spheric2D.Editable {

		}

	}
	public interface Spheric3D   extends RoadCoordinate.Spheric2D, 	GeoCoordinate.Spheric3D {

		public interface Editable extends RoadCoordinate.Spheric3D, RoadCoordinate.Spheric2D.Editable, GeoCoordinate.Spheric3D.Editable {

		}

	}
	public interface Tiled2D     extends RoadCoordinate, 				GeoCoordinate.Tiled2D {

		public interface Editable extends RoadCoordinate.Tiled2D, RoadCoordinate.Editable, TileCoordinate.Editable { }

	}
	public interface Tiled3D     extends RoadCoordinate.Tiled2D, 		GeoCoordinate.Tiled3D {

		public interface Editable extends RoadCoordinate.Tiled3D, RoadCoordinate.Tiled2D.Editable, GeoCoordinate.Tiled3D.Editable {

		}

	}

	public interface Editable extends RoadCoordinate {

		public void	 		setRoadElement(Road.Element _roadElt);
		public void			setRoadElementDirection(Gis.Direction _direction);
		public void			setRoadElementSegmentId(int _id);
		public void      	setRoadElementLaneId(int _n);

		public default void	setCurvilinearAbscissa(double _s) 						{ setCurvilinearAbscissa(_s, false); }
		public void			setCurvilinearAbscissa(double _s, boolean _fromEnd);

		public void		   	setHeading(double _heading);

		public void			setProjection(Coordinate _proj);
		public void      	setProjectionError(double _e);

	}

	public Road.Element	 	getRoadElement();
	public default ID	 	getRoadElementId() { return getRoadElement().getId(); }
	public int       	 	getRoadElementSegmentId();
	public int       	 	getRoadElementLaneId();
	public Gis.Direction 	getRoadElementDirection();

	public default double	getCurvilinearAbscissa() 								{ return getCurvilinearAbscissa(false); }
	public double    	 	getCurvilinearAbscissa(boolean _fromEnd);

	public double 		   	getHeading();

	public Coordinate    	getProjection();
	public double    	 	getProjectionError();

}
