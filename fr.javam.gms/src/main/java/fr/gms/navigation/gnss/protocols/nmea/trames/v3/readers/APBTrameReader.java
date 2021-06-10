package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.APBTrame;

class APBTrameReader extends NmeaTrameReader implements APBTrame {

	APBTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.APB);
	}

	@Override public double 	getBearingPositionToDestination() 		{ return getDoubleValue(BEARING_POS_DEST); }
	@Override public double 	getBearingOriginToDestination() 		{ return getDoubleValue(BEARING_ORIGIN_DEST); }
	@Override public double 	getCrossTrackError() 					{ return getDoubleValue(XTE_DISTANCE); }
	@Override public char 		getCrossTrackUnits() 					{ return getCharValue(XTE_UNITS); }
	@Override public DataStatus getCycleLockStatus() 					{ return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS)); }
	@Override public String 	getDestionationWaypointId() 			{ return getStringValue(DEST_WAYPOINT_ID); }
	@Override public double 	getHeadingToDestionation() 				{ return getDoubleValue(HEADING_TO_DEST); }
	@Override public DataStatus getStatus() 							{ return DataStatus.valueOf(getCharValue(SIGNAL_STATUS)); }
	@Override public Direction 	getSteerTo() 							{ return Direction.valueOf(getCharValue(XTE_STEER_TO)); }
	@Override public boolean 	isArrivalCircleEntered() 				{ return getCharValue(CIRCLE_STATUS) == 'A'; }
	@Override public boolean 	isBearingOriginToDestionationTrue() 	{ return getCharValue(BEARING_ORIGIN_DEST_TYPE) == 'T'; }
	@Override public boolean 	isBearingPositionToDestinationTrue() 	{ return getCharValue(BEARING_POS_DEST_TYPE) == 'T'; }
	@Override public boolean 	isHeadingToDestinationTrue() 			{ return getCharValue(HEADING_TO_DEST_TYPE) == 'T'; }
	@Override public boolean 	isPerpendicularPassed() 				{ return getCharValue(PERPENDICULAR_STATUS) == 'A'; }

}
