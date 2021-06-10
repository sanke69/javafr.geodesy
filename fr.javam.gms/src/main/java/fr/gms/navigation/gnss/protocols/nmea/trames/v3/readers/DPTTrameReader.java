package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.DPTTrame;

class DPTTrameReader extends NmeaTrameReader implements DPTTrame {

	// field indices
	private static final int DEPTH   = 0;
	private static final int OFFSET  = 1;
	private static final int MAXIMUM = 2;

	DPTTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.DPT);
	}

	@Override public double getDepth() { return getDoubleValue(DEPTH); }
	@Override public double getOffset() { return getDoubleValue(OFFSET); }
	@Override public double getMaximum() { return getDoubleValue(MAXIMUM); }

}
