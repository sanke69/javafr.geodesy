package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.VDRTrame;

class VDRTrameReader extends NmeaTrameReader implements VDRTrame {

	private static final int TRUE_DIRECTION = 0;
	private static final int TRUE_INDICATOR = 1;
	private static final int MAGN_DIRECTION = 2;
	private static final int MAGN_INDICATOR = 3;
	private static final int SPEED 			= 4;
	private static final int SPEED_UNITS 	= 5;

	VDRTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.VDR);
	}

	@Override public double getMagneticDirection() { return getDoubleValue(MAGN_DIRECTION); }
	@Override public double getSpeed() { return getDoubleValue(SPEED); }
	@Override public double getTrueDirection() { return getDoubleValue(TRUE_DIRECTION); }

}
