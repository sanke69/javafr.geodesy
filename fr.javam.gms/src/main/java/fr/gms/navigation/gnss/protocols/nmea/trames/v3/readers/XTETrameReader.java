package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.XTETrame;

class XTETrameReader extends NmeaTrameReader implements XTETrame {

	private final static int SIGNAL_STATUS 		= 0;
	private final static int CYCLE_LOCK_STATUS 	= 1;
	private final static int DISTANCE 			= 2;
	private final static int DIRECTION 			= 3;
	private final static int DISTANCE_UNIT 		= 4;
	private final static int FAA_MODE 			= 5;

	XTETrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.XTE);
	}

	@Override public DataStatus getCycleLockStatus() 	{ return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS)); }
	@Override public double 	getMagnitude() 			{ return getDoubleValue(DISTANCE); }
	@Override public FaaMode 	getMode() 				{ return FaaMode.valueOf(getCharValue(FAA_MODE)); }
	@Override public DataStatus getStatus() 			{ return DataStatus.valueOf(getCharValue(SIGNAL_STATUS)); }
	@Override public Direction 	getSteerTo() 			{ return Direction.valueOf(getCharValue(DIRECTION)); }

}
