package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GBSTrame;

class GBSTrameReader extends NmeaTrameReader implements GBSTrame {

	// field indices
    private static final int UTC          = 0;
    private static final int LAT_ERROR    = 1;
    private static final int LON_ERROR    = 2;
    private static final int ALT_ERROR    = 3;
    private static final int SATELLITE_ID = 4;
    private static final int PROBABILITY  = 5;
    private static final int ESTIMATE     = 6;
    private static final int DEVIATION    = 7;

	GBSTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GBS);
	}

    @Override public double getLatitudeError() 	{ return getDoubleValue(LAT_ERROR); }
    @Override public double getLongitudeError() { return getDoubleValue(LON_ERROR); }
    @Override public double getAltitudeError() 	{ return getDoubleValue(ALT_ERROR); }
    @Override public String getSatelliteId() 	{ return getStringValue(SATELLITE_ID); }
    @Override public double getProbability() 	{ return getDoubleValue(PROBABILITY); }
    @Override public double getEstimate() 		{ return getDoubleValue(ESTIMATE); }
    @Override public double getDeviation() 		{ return getDoubleValue(DEVIATION); }
    @Override public TimeX 	getTime() 			{ return new TimeX(getStringValue(UTC)); }

}
