package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.HDTTrame;

class HDTTrameReader extends NmeaTrameReader implements HDTTrame {

	private static final int HEADING = 0;
	private static final int TRUE_INDICATOR = 1;

	HDTTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.HDT);
	}

	@Override public double 	getHeading() 	{ return getDoubleValue(HEADING); }
	@Override public boolean 	isTrue() 		{ return true; }

}
