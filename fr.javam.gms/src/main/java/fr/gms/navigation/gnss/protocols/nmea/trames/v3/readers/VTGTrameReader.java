package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.VTGTrame;

class VTGTrameReader extends NmeaTrameReader implements VTGTrame {

	private static final int TRUE_COURSE        = 0;
	private static final int TRUE_INDICATOR     = 1;
	private static final int MAGNETIC_COURSE    = 2;
	private static final int MAGNETIC_INDICATOR = 3;
	private static final int SPEED_KNOTS        = 4;
	private static final int KNOTS_INDICATOR    = 5;
	private static final int SPEED_KMPH         = 6;
	private static final int KMPH_INDICATOR     = 7;
	private static final int MODE               = 8;

	VTGTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.VTG);
	}

	public double getMagneticCourse() {
		return getDoubleValue(MAGNETIC_COURSE);
	}

	public FaaMode getMode() {
		return FaaMode.valueOf(getCharValue(MODE));
	}

	public double getSpeedKmh() {
		return getDoubleValue(SPEED_KMPH);
	}

	public double getSpeedKnots() {
		return getDoubleValue(SPEED_KNOTS);
	}

	public double getTrueCourse() {
		return getDoubleValue(TRUE_COURSE);
	}

}
