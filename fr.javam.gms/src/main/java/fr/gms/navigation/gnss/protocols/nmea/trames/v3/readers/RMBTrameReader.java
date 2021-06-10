package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.Waypoint;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.RMBTrame;

class RMBTrameReader extends NmeaTrameReader implements RMBTrame {

	// field indexes
	private static final int STATUS 			= 0;
	private static final int CROSS_TRACK_ERROR 	= 1;
	private static final int STEER_TO 			= 2;
	private static final int ORIGIN_WPT 		= 3;
	private static final int DEST_WPT 			= 4;
	private static final int DEST_LAT 			= 5;
	private static final int DEST_LAT_HEM 		= 6;
	private static final int DEST_LON 			= 7;
	private static final int DEST_LON_HEM 		= 8;
	private static final int RANGE_TO_DEST 		= 9;
	private static final int BEARING_TO_DEST 	= 10;
	private static final int VELOCITY 			= 11;
	private static final int ARRIVAL_STATUS 	= 12;

	RMBTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.RMB);
	}

	@Override public DataStatus getArrivalStatus() 		{ return DataStatus.valueOf(getCharValue(ARRIVAL_STATUS)); }
	@Override public double 	getBearing() 			{ return getDoubleValue(BEARING_TO_DEST); }
	@Override public double 	getCrossTrackError() 	{ return getDoubleValue(CROSS_TRACK_ERROR); }
	@Override public Waypoint 	getDestination() {
		String id = getStringValue(DEST_WPT);
		Position p = parsePosition(
			DEST_LAT, DEST_LAT_HEM, DEST_LON, DEST_LON_HEM);
		return p.toWaypoint(id);
	}
	@Override public String 	getOriginId() 			{ return getStringValue(ORIGIN_WPT); }
	@Override public double 	getRange() 				{ return getDoubleValue(RANGE_TO_DEST); }
	@Override public DataStatus getStatus() 			{ return DataStatus.valueOf(getCharValue(STATUS)); }
	@Override public Direction 	getSteerTo() 			{ return Direction.valueOf(getCharValue(STEER_TO)); }
	@Override public double 	getVelocity() 			{ return getDoubleValue(VELOCITY); }
	@Override public boolean 	hasArrived() 			{ return DataStatus.ACTIVE.equals(getArrivalStatus()); }

}
