package fr.geodesic.api;

import fr.java.lang.properties.Timestamp;
import fr.java.math.geometry.space.Vector3D;
import fr.java.patterns.tileable.TileCoordinate;

public interface GeoDynamics extends GeoCoordinate {

	public interface Cartesian2D extends GeoDynamics, 				GeoCoordinate.Cartesian2D {

		public interface Editable extends GeoDynamics.Cartesian2D, GeoDynamics.Editable, GeoCoordinate.Cartesian2D.Editable {

		}

	}
	public interface Cartesian3D extends GeoDynamics.Cartesian2D, 	GeoCoordinate.Cartesian3D {

		public interface Editable extends GeoDynamics.Cartesian3D, GeoDynamics.Cartesian2D.Editable, GeoCoordinate.Cartesian3D.Editable {

		}

	}
	public interface Spheric2D   extends GeoDynamics, 				GeoCoordinate.Spheric2D {

		public interface Editable extends GeoDynamics.Spheric2D, GeoDynamics.Editable, GeoCoordinate.Spheric2D.Editable {

		}

	}
	public interface Spheric3D   extends GeoDynamics.Spheric2D, 	GeoCoordinate.Spheric3D {

		public interface Editable extends GeoDynamics.Spheric3D, GeoDynamics.Spheric2D.Editable, GeoCoordinate.Spheric3D.Editable {

		}

	}
	public interface Tiled2D     extends GeoDynamics, 				GeoCoordinate.Tiled2D {

		public interface Editable extends GeoDynamics.Tiled2D, GeoDynamics.Editable, TileCoordinate.Editable { }

	}
	public interface Tiled3D     extends GeoDynamics.Tiled2D, 		GeoCoordinate.Tiled3D {

		public interface Editable extends GeoDynamics.Tiled3D, GeoDynamics.Tiled2D.Editable, GeoCoordinate.Tiled3D.Editable {

		}

	}

	public interface Editable extends GeoDynamics {

		public void    	setHeading(double _heading);
		public void    	setVelocity(Vector3D _vel);
		public void    	setAcceleration(Vector3D _acc);

	}

	public Timestamp   	getTimestamp();
	public double   	getHeading();
	public Vector3D 	getVelocity();
	public Vector3D 	getAcceleration();

}
