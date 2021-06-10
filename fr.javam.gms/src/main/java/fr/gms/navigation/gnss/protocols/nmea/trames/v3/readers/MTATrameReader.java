package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.MTATrame;

class MTATrameReader extends NmeaTrameReader implements MTATrame {

	private static final int TEMPERATURE    = 0;
	private static final int UNIT_INDICATOR = 1;

	MTATrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.MTA);
	}

	@Override public double getTemperature() { return getDoubleValue(TEMPERATURE); }

}
