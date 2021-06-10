package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.RPMTrame;

class RPMTrameReader extends NmeaTrameReader implements RPMTrame {

	private static final int SOURCE 		= 0;
	private static final int SOURCE_NUMBER 	= 1;
	private static final int REVOLUTIONS 	= 2;
	private static final int PITCH 			= 3;
	private static final int STATUS 		= 4;

	RPMTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.RPM);
	}

	@Override public int 		getId() 	{ return getIntValue(SOURCE_NUMBER); }
	@Override public double 	getPitch() 	{ return getDoubleValue(PITCH); }
	@Override public double 	getRPM() 	{ return getDoubleValue(REVOLUTIONS); }
	@Override public char 		getSource() { return getCharValue(SOURCE); }
	@Override public DataStatus getStatus() { return DataStatus.valueOf(getCharValue(STATUS)); }
	@Override public boolean 	isEngine() 	{ return getCharValue(SOURCE) == RPMTrame.ENGINE; }
	@Override public boolean 	isShaft() 	{ return getCharValue(SOURCE) == RPMTrame.SHAFT; }

}
