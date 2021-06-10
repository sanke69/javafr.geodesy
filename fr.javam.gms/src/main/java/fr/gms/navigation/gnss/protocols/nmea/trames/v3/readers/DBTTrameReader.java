package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.DBTTrame;

class DBTTrameReader extends NmeaTrameReader implements DBTTrame {

	// field indices
	private static final int DEPTH_FEET    = 0;
	private static final int FEET          = 1;
	private static final int DEPTH_METERS  = 2;
	private static final int METERS        = 3;
	private static final int DEPTH_FATHOMS = 4;
	private static final int FATHOMS       = 5;

	DBTTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.DBT);
	}

	@Override public double getDepth() 		{ return getDoubleValue(DEPTH_METERS); }
	@Override public double getFathoms() 	{ return getDoubleValue(DEPTH_FATHOMS); }
	@Override public double getFeet() 		{ return getDoubleValue(DEPTH_FEET); }

}
