package fr.gis.opensky.objects;

import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.java.lang.properties.ID;

public interface MapPlaneInfo {

	public ID 				getId();

	/**
	 * @return ICAO24 address of the transmitter in hex string representation.
	 */
	public String 			getIcao24();
	/**
	 * @return callsign of the vehicle. Can be {@code null} if no callsign has been received.
	 */
	public String 			getCallsign();
	/**
	 * @return transponder code aka squawk. Can be {@code null}
	 */
	public String 			getSquawk();

	public String 			getOriginCountry();
	/**
	 * @return true if aircraft is on ground (sends ADS-B surface position reports).
	 */
	public boolean 			isOnGround();

	public GeoCoordinate 	getPosition();
	
	/**
	 * @return longitude in ellipsoidal coordinates (WGS-84) and degrees.
	 */
	public default double 	getLongitude() { return getPosition().asWGS84().getLongitude(); }
	/**
	 * @return latitude in ellipsoidal coordinates (WGS-84) and degrees.
	 */
	public default double 	getLatitude() { return getPosition().asWGS84().getLatitude(); }
	/**
	 * @return geometric altitude in meters.
	 */
	public double 			getGeoAltitude();
	/**
	 * @return barometric altitude in meters.
	 */
	public double 			getBaroAltitude();

	/**
	 * @return in decimal degrees (0 is north). Can be {@code null} if information not present
	 */
	public double 			getHeading();

	/**
	 * @return over ground in m/s.
	 */
//	public double 			getVelocity();
	/**
	 * @return in m/s, incline is positive, decline negative. Can be {@code null} if information not present.
	 */
	public double 			getVerticalRate();

	/**
	 * @return  seconds since epoch of last message overall received by this transponder.
	 */
	public double 			getLastContact();
	/**
	 * @return seconds since epoch of last position report. Can be {@code null} if there was no position report received by OpenSky within 15s before.
	 */
	public double 			getLastPositionUpdate();

	/**
	 * @return serial numbers of sensors which received messages from the vehicle within the validity period of this state vector. {@code null} if information is not present, i.e., there was no filter for a sensor in the request
	 */
	public Set<Integer> 	getSerials();

}
