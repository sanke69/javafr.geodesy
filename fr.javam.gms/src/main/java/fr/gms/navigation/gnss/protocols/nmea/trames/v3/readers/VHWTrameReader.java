package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.VHWTrame;

class VHWTrameReader extends NmeaTrameReader implements VHWTrame {

	private static final int TRUE_HEADING 		= 0;
	private static final int TRUE_INDICATOR 	= 1;
	private static final int MAGNETIC_HEADING 	= 2;
	private static final int MAGNETIC_INDICATOR = 3;
	private static final int SPEED_KNOTS 		= 4;
	private static final int KNOTS_INDICATOR 	= 5;
	private static final int SPEED_KMH 			= 6;
	private static final int KMH_INDICATOR 		= 7;

	VHWTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.VHW);
	}

	@Override public double  getHeading() 			{ return getDoubleValue(TRUE_HEADING); }
	@Override public double  getMagneticHeading() 	{ return getDoubleValue(MAGNETIC_HEADING); }
	@Override public double  getSpeedKmh() 			{ return getDoubleValue(SPEED_KMH); }
	@Override public double  getSpeedKnots() 		{ return getDoubleValue(SPEED_KNOTS); }
	@Override public boolean isTrue() 				{ return true; }

}
