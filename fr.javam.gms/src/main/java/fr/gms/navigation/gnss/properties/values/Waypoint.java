package fr.gms.navigation.gnss.properties.values;

import java.util.Date;

import fr.geodesic.api.referential.Datum;

/**
 * Waypoint represents a named geographic location.
 * 
 * @author Kimmo Tuukkanen
 * @see fr.gnss.properties.values.Position
 */
public class Waypoint extends Position {

	private String id;
	private String description = "";
	private final Date timeStamp = new Date();

	/**
	 * Creates a new instance of <code>Waypoint</code> with default WGS84 datum.
	 * 
	 * @param id Waypoint identifier
	 * @param lat Latitude degrees of the waypoint location
	 * @param lon Longitude degrees of waypoint location
	 */
	public Waypoint(String id, double lat, double lon) {
		super(lat, lon);
		this.id = id;
	}

	/**
	 * Creates a new instance of <code>Waypoint</code> with default WGS84 datum.
	 * 
	 * @param id Waypoint identifier
	 * @param lat Latitude degrees of the waypoint location
	 * @param lon Longitude degrees of waypoint location
	 * @param alt Altitude value, in meters above/below mean sea level
	 */
	public Waypoint(String id, double lat, double lon, double alt) {
		super(lat, lon, alt);
		this.id = id;
	}

	/**
	 * Creates a new instance of Waypoint with explicitly specified datum.
	 * 
	 * @param id Waypoint identifier
	 * @param lat Latitude degrees of the waypoint location
	 * @param lon Longitude degrees of waypoint location
	 * @param datum Position datum, i.e. the coordinate system.
	 */
	public Waypoint(String id, double lat, double lon, Datum datum) {
		super(lat, lon, datum);
		this.id = id;
	}

	/**
	 * Creates a new instance of <code>Waypoint</code> with explicitly specified
	 * datum.
	 * 
	 * @param id Waypoint identifier/name
	 * @param lat Latitude degrees of the waypoint location
	 * @param lon Longitude degrees of waypoint location
	 * @param alt Altitude value, in meters above/below mean sea level
	 * @param datum Position datum, i.e. the coordinate system.
	 */
	public Waypoint(String id, double lat, double lon, double alt, Datum datum) {
		super(lat, lon, alt, datum);
		this.id = id;
	}

	/**
	 * Gets the waypoint description/comment.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get id of Waypoint
	 * 
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the time stamp when <code>Waypoint</code> was created.
	 * 
	 * @return Date
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the waypoint description.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the id of Waypoint
	 * 
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
