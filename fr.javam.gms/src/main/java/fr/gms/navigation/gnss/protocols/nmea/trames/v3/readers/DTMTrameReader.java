package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.DTMTrame;

class DTMTrameReader extends NmeaTrameReader implements DTMTrame {

	// field indices
	private static final int DATUM_CODE = 0;
	private static final int DATUM_SUBCODE = 1;
	private static final int LATITUDE_OFFSET = 2;
	private static final int LAT_OFFSET_HEMISPHERE = 3;
	private static final int LONGITUDE_OFFSET = 4;
	private static final int LON_OFFSET_HEMISPHERE = 5;
	private static final int ALTITUDE_OFFSET = 6;
	private static final int DATUM_NAME = 7;

	DTMTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.DTM);
	}

	@Override public double getAltitudeOffset() 	{ return getDoubleValue(ALTITUDE_OFFSET); }
	@Override public String getDatumCode() 			{ return getStringValue(DATUM_CODE); }
	@Override public String getDatumSubCode() 		{ return getStringValue(DATUM_SUBCODE); }
	@Override public double getLatitudeOffset() 	{ return getDoubleValue(LATITUDE_OFFSET); }
	@Override public double getLongitudeOffset() 	{ return getDoubleValue(LONGITUDE_OFFSET); }
	@Override public String getName() 				{ return getStringValue(DATUM_NAME); }

}
