package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.VLWTrame;

class VLWTrameReader extends NmeaTrameReader implements VLWTrame {

	private static final int TOTAL 			= 0;
	private static final int TOTAL_UNITS 	= 1;
	private static final int TRIP 			= 2;
	private static final int TRIP_UNITS 	= 3;

	VLWTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.VLW);
	}

	@Override public double getTotal() 		{ return getDoubleValue(TOTAL); }
	@Override public char 	getTotalUnits() { return getCharValue(TOTAL_UNITS); }
	@Override public double getTrip() 		{ return getDoubleValue(TRIP); }
	@Override public char 	getTripUnits() 	{ return getCharValue(TRIP_UNITS); }

}
