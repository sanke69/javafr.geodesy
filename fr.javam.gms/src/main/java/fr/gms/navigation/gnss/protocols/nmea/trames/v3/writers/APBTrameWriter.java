package fr.gms.navigation.gnss.protocols.nmea.trames.v3.writers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameEmitter;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameType;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameWriter;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.APBTrame;

class APBTrameWriter extends NmeaTrameWriter implements APBTrame.Editable {

	protected APBTrameWriter(char begin, TrameEmitter tid, TrameType sid, int size) {
		super(begin, tid, sid, size);
	}

	public void setArrivalCircleEntered(boolean isEntered) {
		DataStatus s = isEntered ? DataStatus.ACTIVE : DataStatus.VOID;
		setCharValue(APBTrame.CIRCLE_STATUS, s.toChar());
	}

	public void setBearingOriginToDestination(double bearing) {
		setDegreesValue(APBTrame.BEARING_ORIGIN_DEST, bearing);
	}

	@Override
	public void setBearingOriginToDestionationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(APBTrame.BEARING_ORIGIN_DEST_TYPE, c);
	}

	@Override
	public void setBearingPositionToDestination(double bearing) {
		setDegreesValue(APBTrame.BEARING_POS_DEST, bearing);
	}

	@Override
	public void setBearingPositionToDestinationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(APBTrame.BEARING_POS_DEST_TYPE, c);
	}

	@Override
	public void setCrossTrackError(double distance) {
		setDoubleValue(APBTrame.XTE_DISTANCE, distance, 1, 1);
	}

	@Override
	public void setCrossTrackUnits(char unit) {
		if (unit != APBTrame.KM && unit != APBTrame.NM) {
			throw new IllegalAccessError(
				"Invalid distance unit char, expected 'K' or 'N'");
		}
		setCharValue(APBTrame.XTE_UNITS, unit);
	}

	@Override
	public void setCycleLockStatus(DataStatus status) {
		setCharValue(APBTrame.CYCLE_LOCK_STATUS, status.toChar());
	}

	@Override
	public void setDestinationWaypointId(String id) {
		setStringValue(APBTrame.DEST_WAYPOINT_ID, id);
	}

	@Override
	public void setHeadingToDestination(double heading) {
		setDoubleValue(APBTrame.HEADING_TO_DEST, heading);
	}

	@Override
	public void setHeadingToDestinationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(APBTrame.HEADING_TO_DEST_TYPE, c);
	}

	@Override
	public void setPerpendicularPassed(boolean isPassed) {
		DataStatus s = isPassed ? DataStatus.ACTIVE : DataStatus.VOID;
		setCharValue(APBTrame.PERPENDICULAR_STATUS, s.toChar());
	}

	@Override
	public void setStatus(DataStatus status) {
		setCharValue(APBTrame.SIGNAL_STATUS, status.toChar());
	}

	@Override
	public void setSteerTo(Direction direction) {
		setCharValue(APBTrame.XTE_STEER_TO, direction.toChar());
	}

}