package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.MWDTrame;

class MWDTrameReader extends NmeaTrameReader implements MWDTrame {

    private static int WIND_DIRECTION_TRUE 			= 0;	// Wind direction, degrees True, to the nearest 0,1 degree.
    private static int WIND_DIRECTION_TRUE_UNIT 	= 1;	// T = true
    private static int WIND_DIRECTION_MAGNETIC 		= 2;	// Wind direction, degrees Magnetic, to the nearest 0,1 degree.
    private static int WIND_DIRECTION_MAGNETIC_UNIT = 3;	// M = magnetic.
    private static int WIND_SPEED_KNOTS 			= 4;	// Wind speed, knots, to the nearest 0,1 knot.
    private static int WIND_SPEED_KNOTS_UNIT 		= 5;	// N = knots.
    private static int WIND_SPEED_METERS 			= 6;	// Wind speed, meters per second, to the nearest 0,1 m/s.
    private static int WIND_SPEED_METERS_UNIT 		= 7;	// M = meters per second

    MWDTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.MWD);
	}

    @Override
    public double getMagneticWindDirection() {
        if (hasValue(WIND_DIRECTION_MAGNETIC) && hasValue(WIND_DIRECTION_MAGNETIC_UNIT) && getStringValue(WIND_DIRECTION_MAGNETIC_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_DIRECTION_MAGNETIC);
        } else {
            return Double.NaN;
        }
    }
    @Override
    public double getTrueWindDirection() {
        if (hasValue(WIND_DIRECTION_TRUE) && hasValue(WIND_DIRECTION_TRUE_UNIT) && getStringValue(WIND_DIRECTION_TRUE_UNIT).equalsIgnoreCase("T")) {
            return getDoubleValue(WIND_DIRECTION_TRUE);
        } else {
            return Double.NaN;
        }
    }
    @Override
    public double getWindSpeed() {
        if (hasValue(WIND_SPEED_METERS) && hasValue(WIND_SPEED_METERS_UNIT) && getStringValue(WIND_SPEED_METERS_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_SPEED_METERS);
        } else {
            return Double.NaN;
        }
    }
    @Override
    public double getWindSpeedKnots() {
        if (hasValue(WIND_SPEED_KNOTS) && hasValue(WIND_SPEED_KNOTS_UNIT) && getStringValue(WIND_SPEED_KNOTS_UNIT).equalsIgnoreCase("N")) {
            return getDoubleValue(WIND_SPEED_KNOTS);
        } else {
            return Double.NaN;
        }
    }

}
