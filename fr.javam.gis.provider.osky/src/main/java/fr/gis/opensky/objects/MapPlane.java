package fr.gis.opensky.objects;

import java.util.HashSet;
import java.util.Set;

import org.opensky.model.StateVector;

import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisDynamics;
import fr.java.jvm.properties.id.IDs;
import fr.java.sdk.patterns.timeable.Timestamps;

public class MapPlane extends GisDynamics implements MapPlaneInfo {

	// Identification
	private boolean 		spi;
	private String 			icao24;
	private String 			callsign;
	private String 			squawk;
	private String 			originCountry;

	// Location
	private boolean 		onGround;
//	private GeoCoordinate 	coordinate;
	private Double 			geoAltitude;
	private Double 			baroAltitude;

	// Dynamics
//	private Double 			heading;
	private Double 			velocity;
	private Double 			verticalRate;

	// Update Status
	private Double 			lastContact;
	private Double 			lastPositionUpdate;
	private PositionSource 	positionSource;

	private Set<Integer> 	serials;

	public MapPlane(String _icao24) {
		super(IDs.newUTF8(_icao24), Gis.User.Plane, Timestamps.now(), GeoCoordinates.newWGS84(0,0));

		this.icao24 = _icao24;
		this.serials = null;
	}
	public MapPlane(MapPlaneInfo nfo) {
		super(IDs.newUTF8(nfo.getIcao24()), Gis.User.Plane, null);

		setCallsign				( nfo.getCallsign() );
		setSquawk				( nfo.getSquawk() );

		setOriginCountry		( nfo.getOriginCountry() );

		setOnGround				( nfo.isOnGround() );
		setPosition				( GeoCoordinates.newWGS84(nfo.getLongitude(), nfo.getLatitude()) );
		setGeoAltitude			( nfo.getGeoAltitude() );
		setBaroAltitude			( nfo.getBaroAltitude() );

		setHeading				( nfo.getHeading() );
//		setVelocity				( nfo.getVelocity() );
		setVerticalRate			( nfo.getVerticalRate() );

		setLastContact			( nfo.getLastContact() * 1000L  );
		setLastPositionUpdate	( nfo.getLastPositionUpdate() * 1000L );
	}

	/**
	 * @return whether flight status indicates special purpose indicator.
	 */
	public void 			setSpi(boolean _spi) {
		spi = _spi;
	}
	public boolean 			isSpi() {
		return spi;
	}

	public void 			setIcao24(String _icao24) {
		icao24 = _icao24;
	}
	public String 			getIcao24() {
		return icao24;
	}

	public void 			setCallsign(String _callsign) {
		callsign = _callsign;
	}
	public String 			getCallsign() {
		return callsign;
	}

	public void 			setSquawk(String _squawk) {
		squawk = _squawk;
	}
	public String 			getSquawk() {
		return squawk;
	}

	public String 			getOriginCountry() {
		return originCountry;
	}
	public void 			setOriginCountry(String _originCountry) {
		originCountry = _originCountry;
	}

	/**
	 * @return origin of this state's position
	 */
	public PositionSource 	getPositionSource() {
		return positionSource;
	}
	public void 			setPositionSource(PositionSource positionSource) {
		this.positionSource = positionSource;
	}


	public void 			setOnGround(boolean _onGround) {
		onGround = _onGround;
	}
	public boolean 			isOnGround() {
		return onGround;
	}

	public void 			setGeoAltitude(double _geoAltitude) {
		geoAltitude = _geoAltitude;
	}
	public double 			getGeoAltitude() {
		return geoAltitude;
	}

	public void 			setBaroAltitude(double _baroAltitude) {
		baroAltitude = _baroAltitude;
	}
	public double 			getBaroAltitude() {
		return baroAltitude;
	}

	public void 			setVelocity(double _velocity) {
		velocity = _velocity;
	}

	public void 			setVerticalRate(Double _verticalRate) {
		verticalRate = _verticalRate;
	}
	public double 			getVerticalRate() {
		return verticalRate;
	}



	public void 			setLastContact(double _lastContact) {
		lastContact = _lastContact;
	}
	public double 			getLastContact() {
		return lastContact;
	}

	public void 			setLastPositionUpdate(double _lastPositionUpdate) {
		lastPositionUpdate = _lastPositionUpdate;
	}
	public double 			getLastPositionUpdate() {
		return lastPositionUpdate;
	}

	public void 			addSerial(int _serial) {
		if (serials == null) {
			serials = new HashSet<>();
		}
		serials.add(_serial);
	}
	public Set<Integer> 	getSerials() {
		return serials;
	}

	@Override
	public String toString() {
		return "MapPlane{" +
				"geoAltitude=" + getGeoAltitude() +
				", longitude=" + getLongitude() +
				", latitude=" + getLatitude() +
				", velocity=" + velocity +
				", heading=" + getHeading() +
				", verticalRate=" + verticalRate +
				", icao24='" + icao24 + '\'' +
				", callsign='" + callsign + '\'' +
				", onGround=" + onGround +
				", lastContact=" + lastContact +
				", lastPositionUpdate=" + lastPositionUpdate +
				", originCountry='" + originCountry + '\'' +
				", squawk='" + squawk + '\'' +
				", spi=" + spi +
				", baroAltitude=" + baroAltitude +
				", positionSource=" + positionSource +
				", serials=" + serials +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof StateVector)) return false;

		MapPlane that = (MapPlane) o;

		if (onGround != that.onGround) return false;
		if (spi != that.spi) return false;
		if (geoAltitude != null ? !geoAltitude.equals(that.geoAltitude) : that.geoAltitude != null) return false;
//		if (longitude != Double.NaN ? !longitude.equals(that.longitude) : that.longitude != null) return false;
//		if (latitude != Double.NaN ? !latitude.equals(that.latitude) : that.latitude != null) return false;
		if (velocity != null ? !velocity.equals(that.velocity) : that.velocity != null) return false;
//		if (heading != null ? !heading.equals(that.heading) : that.heading != null) return false;
		if (verticalRate != null ? !verticalRate.equals(that.verticalRate) : that.verticalRate != null) return false;
		if (!icao24.equals(that.icao24)) return false;
		if (callsign != null ? !callsign.equals(that.callsign) : that.callsign != null) return false;
		if (lastContact != null ? !lastContact.equals(that.lastContact) : that.lastContact != null) return false;
		if (lastPositionUpdate != null ? !lastPositionUpdate.equals(that.lastPositionUpdate) : that.lastPositionUpdate != null)
			return false;
		if (originCountry != null ? !originCountry.equals(that.originCountry) : that.originCountry != null)
			return false;
		if (squawk != null ? !squawk.equals(that.squawk) : that.squawk != null) return false;
		if (baroAltitude != null ? !baroAltitude.equals(that.baroAltitude) : that.baroAltitude != null) return false;
		if (positionSource != that.positionSource) return false;
		return serials != null ? serials.equals(that.serials) : that.serials == null;
	}

	@Override
	public int hashCode() {
		int result = geoAltitude != null ? geoAltitude.hashCode() : 0;
//		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
//		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (velocity != null ? velocity.hashCode() : 0);
//		result = 31 * result + (heading != null ? heading.hashCode() : 0);
		result = 31 * result + (verticalRate != null ? verticalRate.hashCode() : 0);
		result = 31 * result + icao24.hashCode();
		result = 31 * result + (callsign != null ? callsign.hashCode() : 0);
		result = 31 * result + (onGround ? 1 : 0);
		result = 31 * result + (lastContact != null ? lastContact.hashCode() : 0);
		result = 31 * result + (lastPositionUpdate != null ? lastPositionUpdate.hashCode() : 0);
		result = 31 * result + (originCountry != null ? originCountry.hashCode() : 0);
		result = 31 * result + (squawk != null ? squawk.hashCode() : 0);
		result = 31 * result + (spi ? 1 : 0);
		result = 31 * result + (baroAltitude != null ? baroAltitude.hashCode() : 0);
		result = 31 * result + (positionSource != null ? positionSource.hashCode() : 0);
		result = 31 * result + (serials != null ? serials.hashCode() : 0);
		return result;
	}

	public enum PositionSource {
		ADS_B,
		ASTERIX,
		MLAT,
		FLARM,
		UNKNOWN
	}

}
