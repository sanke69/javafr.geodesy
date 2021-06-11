package fr.gms.api.ego;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gms.api.sensor.EHorizon;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.Coordinates;

public interface EgoVehicle extends Gis.Dynamics {

	public EHorizon.Oriented					getEHorizon();
	public double 								getHeading();

	public default Coordinate.Cartesian2D		getLocalCoordinate(GeoCoordinate _global) {
		GeoCoordinate current = getPosition();
		double 	      heading =  ( getHeading() ) * Math.PI / 180d;

		double X   = _global.asUTM().getX() - current.asUTM().getX();
		double Y   = _global.asUTM().getY() - current.asUTM().getY();
		double R00 = Math.cos(heading), R01 = - Math.sin(heading),
			   R10 = Math.sin(heading), R11 =   Math.cos(heading);

		double x = R00 * X + R01 * Y;
		double y = R10 * X + R11 * Y;

		return Coordinates.newCartesian(y, - x);
	}
	public default GeoCoordinate				getWorldCoordinate(Coordinate.Cartesian2D _local) {
		/*
		GeoCoordinate current = getPosition();
		double 	      heading = - getHeading() * Math.PI / 180d;

		double X   = _global.asUTM().getX() - current.asUTM().getX();
		double Y   = _global.asUTM().getY() - current.asUTM().getY();
		double R00 = Math.cos(heading), R01 = - Math.sin(heading),
			   R10 = Math.sin(heading), R11 =   Math.cos(heading);

		double x = R00 * X + R01 * Y;
		double y = R10 * X + R11 * Y;

		return Coordinates.newCartesian(x, y);
		*/
		return null;
	}

}
