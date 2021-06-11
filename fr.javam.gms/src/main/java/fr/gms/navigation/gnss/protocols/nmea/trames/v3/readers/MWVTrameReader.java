package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.NmeaCodec;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.MWVTrame;
import fr.java.measure.Unit;

class MWVTrameReader extends NmeaTrameReader implements MWVTrame {

	private static final int WIND_ANGLE = 0;
	private static final int REFERENCE = 1;
	private static final int WIND_SPEED = 2;
	private static final int SPEED_UNITS = 3;
	private static final int DATA_STATUS = 4;

    MWVTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.MWD);
	}

	@Override public double 	getAngle() 		{ return getDoubleValue(WIND_ANGLE); }
	@Override public double 	getSpeed() 		{ return getDoubleValue(WIND_SPEED); }
	@Override public Unit 		getSpeedUnit() 	{ return NmeaCodec.decodeUnit(getCharValue(SPEED_UNITS)); }
	@Override public DataStatus getStatus() 	{ return DataStatus.valueOf(getCharValue(DATA_STATUS)); }
	@Override public boolean 	isTrue() 		{ char ch = getCharValue(REFERENCE); return ch == 'T'; }

}
