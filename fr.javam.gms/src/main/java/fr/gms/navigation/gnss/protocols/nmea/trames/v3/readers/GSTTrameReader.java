package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GSTTrame;

class GSTTrameReader extends NmeaTrameReader implements GSTTrame {

	// GST field indices
	private static final int UTC_TIME 					= 0;
	private static final int PSEUDORANGE_RESIDUALS_RMS 	= 1;
	private static final int ERROR_ELLIPSE_SEMI_MAJOR 	= 2;
	private static final int ERROR_ELLIPSE_SEMI_MINOR 	= 3;
	private static final int ERROR_ELLIPSE_ORIENTATION 	= 4;
	private static final int LATITUDE_ERROR 			= 5;
	private static final int LONGITUDE_ERROR 			= 6;
	private static final int ALTITUDE_ERROR 			= 7;

	GSTTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GST);
	}

	@Override public TimeX 	getTime() {
		String str = getStringValue(UTC_TIME);
		return new TimeX(str);
	}
	@Override public double getPseudoRangeResidualsRMS() 	{ return getDoubleValue(PSEUDORANGE_RESIDUALS_RMS); }
	@Override public double getSemiMajorError() 			{ return getDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR); }
	@Override public double getSemiMinorError() 			{ return getDoubleValue(ERROR_ELLIPSE_SEMI_MINOR); }
	@Override public double getErrorEllipseOrientation() 	{ return getDoubleValue(ERROR_ELLIPSE_ORIENTATION); }
	@Override public double getLatitudeError() 				{ return getDoubleValue(LATITUDE_ERROR); }
	@Override public double getLongitudeError() 			{ return getDoubleValue(LONGITUDE_ERROR); }
	@Override public double getAltitudeError() 				{ return getDoubleValue(ALTITUDE_ERROR); }

}
