package fr.gms.navigation.gnss.properties.values;

import java.text.DecimalFormat;

import fr.geodesic.api.referential.Datum;
import fr.gms.navigation.gnss.properties.constants.CompassPoint;

public class Position { // extends ...

	private double 	latitude;				// latitude degrees
	private double 	longitude;				// longitude degrees
	private double 	altitude = 0.0;			// altitude
	private Datum 	datum = Datum.WGS84;	// datum/coordinate system

	public Position(double lat, double lon) {
		setLatitude(lat);
		setLongitude(lon);
	}
	public Position(double lat, double lon, double alt) {
		this(lat, lon);
		this.altitude = alt;
	}
	public Position(double lat, double lon, Datum datum) {
		this(lat, lon);
		this.datum = datum;
	}
	public Position(double lat, double lon, double alt, Datum datum) {
		this(lat, lon, alt);
		this.datum = datum;
	}

	public double distanceTo(Position pos) {
		return haversine(getLatitude(), getLongitude(), pos.getLatitude(), pos.getLongitude());
	}

	public double getAltitude() {
		return altitude;
	}

	public Datum getDatum() {
		return datum;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public CompassPoint getLatitudeHemisphere() {
		return isLatitudeNorth() ? CompassPoint.NORTH : CompassPoint.SOUTH;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public CompassPoint getLongitudeHemisphere() {
		return isLongitudeEast() ? CompassPoint.EAST : CompassPoint.WEST;
	}

	public boolean isLatitudeNorth() {
		return getLatitude() >= 0.0;
	}

	public boolean isLongitudeEast() {
		return getLongitude() >= 0.0;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public void setLatitude(double latitude) {
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException(
				"Latitude out of bounds -90..90 degrees");
		}
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException(
				"Longitude out of bounds -180..180 degrees");
		}
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("00.0000000");
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(df.format(Math.abs(getLatitude())));
		sb.append(" ");
		sb.append(getLatitudeHemisphere().toChar());
		sb.append(", ");
		df.applyPattern("000.0000000");
		sb.append(df.format(Math.abs(getLongitude())));
		sb.append(" ");
		sb.append(getLongitudeHemisphere().toChar());
		sb.append(", ");
		sb.append(getAltitude());
		sb.append(" m]");
		return sb.toString();
	}

	public Waypoint toWaypoint(String id) {
		return new Waypoint(id, getLatitude(), getLongitude());
	}

	/**
	 * Haversine formulae, implementation based on example at <a href=
	 * "http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe"
	 * >codecodex</a>.
	 * 
	 * @param lat1 Origin latitude
	 * @param lon1 Origin longitude
	 * @param lat2 Destination latitude
	 * @param lon2 Destination longitude
	 * @return Distance in meters
	 */
	private double haversine(double lat1, double lon1, double lat2, double lon2) {

		// Mean earth radius (IUGG) = 6371.009
		// Meridional earth radius = 6367.4491
		// Earth radius by assumption that 1 degree equals exactly 60 NM:
		// 1.852 * 60 * 360 / (2 * Pi) = 6366.7 km

		final double earthRadius = 6366.70702;

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (earthRadius * c * 1000);
	}
}
