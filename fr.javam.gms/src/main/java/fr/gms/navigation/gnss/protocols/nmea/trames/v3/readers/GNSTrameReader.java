package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GNSTrame;

class GNSTrameReader extends NmeaTrameReader implements GNSTrame {

    // NMEA field indices
    private static final int UTC_TIME           = 0;
    private static final int LATITUDE           = 1;
    private static final int LAT_DIRECTION      = 2;
    private static final int LONGITUDE          = 3;
    private static final int LON_DIRECTION      = 4;
    private static final int MODE               = 5;
    private static final int SATELLITE_COUNT    = 6;
    private static final int HDOP               = 7;
    private static final int ORTHOMETRIC_HEIGHT = 8;
    private static final int GEOIDAL_SEPARATION = 9;
    private static final int DGPS_AGE           = 10;
    private static final int DGPS_STATION       = 11;

    // MODE string character indices
    private static final int GPS_MODE = 0;
    private static final int GNS_MODE = 1;
    private static final int VAR_MODE = 2;

	GNSTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GNS);
	}

    @Override public TimeX 		getTime() 				{ return new TimeX(getStringValue(UTC_TIME)); }
    @Override public Position 	getPosition() 			{ return parsePosition(LATITUDE, LAT_DIRECTION, LONGITUDE, LON_DIRECTION); }
    @Override public Mode 		getGpsMode() {
        String modes = getStringValue(MODE);
        return Mode.valueOf(modes.charAt(GPS_MODE));
    }

    @Override public Mode 		getGlonassMode() {
        String modes = getStringValue(MODE);
        return Mode.valueOf(modes.charAt(GNS_MODE));
    }

    @Override public Mode[] 	getAdditionalModes() {
        String mode = getStringValue(MODE);
        if(mode.length() == 2) {
            return new Mode[0];
        }
        String additional = mode.substring(VAR_MODE);
        Mode[] modes = new Mode[additional.length()];
        for (int i = 0; i < additional.length(); i++) {
            modes[i] = Mode.valueOf(additional.charAt(i));
        }
        return modes;
    }

    @Override public int 		getSatelliteCount() 	{ return getIntValue(SATELLITE_COUNT); }
    @Override public double 	getHorizontalDOP() 		{ return getDoubleValue(HDOP); }
    @Override public double 	getOrthometricHeight() 	{ return getDoubleValue(ORTHOMETRIC_HEIGHT); }
    @Override public double 	getGeoidalSeparation() 	{ return getDoubleValue(GEOIDAL_SEPARATION); }
    @Override public double 	getDgpsAge() 			{ return getDoubleValue(DGPS_AGE); }
    @Override public String 	getDgpsStationId() 		{ return getStringValue(DGPS_STATION); }

}
