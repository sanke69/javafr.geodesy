package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.MTWTrame;

class MTWTrameReader extends NmeaTrameReader implements MTWTrame {

	private static final int TEMPERATURE    = 0;
	private static final int UNIT_INDICATOR = 1;

	MTWTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.MTW);
	}

	@Override public double getTemperature() { return getDoubleValue(TEMPERATURE); }

}
