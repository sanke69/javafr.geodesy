package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.CompassPoint;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.HDGTrame;

class HDGTrameReader extends NmeaTrameReader implements HDGTrame {

	private static final int HEADING = 0;
	private static final int DEVIATION = 1;
	private static final int DEV_DIRECTION = 2;
	private static final int VARIATION = 3;
	private static final int VAR_DIRECTION = 4;

	HDGTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.HDG);
	}

	@Override public double getDeviation() {
		double dev = getDoubleValue(DEVIATION);
		if (dev == 0) {
			return dev;
		}
		CompassPoint dir = CompassPoint.valueOf(getCharValue(DEV_DIRECTION));
		return dir == CompassPoint.WEST ? -dev : dev;
	}
	@Override public double getHeading() { return getDoubleValue(HEADING); }
	@Override public double getVariation() {
		double var = getDoubleValue(VARIATION);
		if (var == 0)
			return var;

		CompassPoint dir = CompassPoint.valueOf(getCharValue(VAR_DIRECTION));
		return dir == CompassPoint.WEST ? -var : var;
	}
	@Override public boolean isTrue() 	{ return false; }

}
