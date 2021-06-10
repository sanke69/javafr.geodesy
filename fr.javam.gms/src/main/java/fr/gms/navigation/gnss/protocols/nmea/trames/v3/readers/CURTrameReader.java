package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.CURTrame;

class CURTrameReader extends NmeaTrameReader implements CURTrame {

	// field indices
	private static final int DATA_STATUS           = 0;
	private static final int DATA_SET              = 1;
	private static final int LAYER                 = 2;
	private static final int CURRENT_DEPTH         = 3; // in meters
	private static final int CURRENT_DIRECTION     = 4; // in degrees
	private static final int DIRECTION_REFERENCE   = 5; // True/Relative T/R
	private static final int CURRENT_SPEED         = 6; // in knots
	private static final int REFERENCE_LAYER_DEPTH = 7; // in meters
	private static final int CURRENT_HEADING       = 8;
	private static final int HEADING_REFERENCE     = 9; // True/Magentic T/M
	private static final int SPEED_REFERENCE       = 10; // Bottom/Water/Positioning system B/W/P

	CURTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.CUR);
	}

	@Override public double getCurrentDirection() 			{ return getDoubleValue(CURRENT_DIRECTION); }
	@Override public String getCurrentDirectionReference() 	{ return getStringValue(DIRECTION_REFERENCE); }
	@Override public String getCurrentHeadingReference() 	{ return getStringValue(HEADING_REFERENCE); }
	@Override public double getCurrentSpeed() 				{ return getDoubleValue(CURRENT_SPEED); }

}
