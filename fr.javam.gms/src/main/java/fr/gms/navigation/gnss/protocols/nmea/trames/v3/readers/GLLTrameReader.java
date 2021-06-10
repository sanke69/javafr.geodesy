package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GLLTrame;

class GLLTrameReader extends NmeaTrameReader implements GLLTrame {

	// field indices
	private static final int LATITUDE       = 0;
	private static final int LAT_HEMISPHERE = 1;
	private static final int LONGITUDE      = 2;
	private static final int LON_HEMISPHERE = 3;
	private static final int UTC_TIME       = 4;
	private static final int DATA_STATUS    = 5;
	private static final int MODE           = 6;

	GLLTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GLL);
	}

	@Override public Position 	getPosition() 	{ return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE); }
	@Override public TimeX 		getTime() 		{ return new TimeX( getStringValue(UTC_TIME) ); }
	@Override public DataStatus getStatus() 	{ return DataStatus.valueOf(getCharValue(DATA_STATUS)); }
	@Override public FaaMode 	getMode() 		{ return getFieldCount() > MODE ? FaaMode.valueOf(getCharValue(MODE)) : null; }

}
