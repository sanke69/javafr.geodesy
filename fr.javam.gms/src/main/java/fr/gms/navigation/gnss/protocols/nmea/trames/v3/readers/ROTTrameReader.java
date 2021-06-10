package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.ROTTrame;

class ROTTrameReader extends NmeaTrameReader implements ROTTrame {

	private static final int RATE_OF_TURN 	= 0;
	private static final int STATUS 		= 1;

	ROTTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.ROT);
	}

	@Override public double 	getRateOfTurn() { return getDoubleValue(RATE_OF_TURN); }
	@Override public DataStatus getStatus() 	{ return DataStatus.valueOf(getCharValue(STATUS)); }

}
