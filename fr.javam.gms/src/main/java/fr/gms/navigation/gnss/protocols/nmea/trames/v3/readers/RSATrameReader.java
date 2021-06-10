package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Side;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.RSATrame;

class RSATrameReader extends NmeaTrameReader implements RSATrame {

	private static final int STARBOARD_SENSOR 	= 0;
	private static final int STARBOARD_STATUS 	= 1;
	private static final int PORT_SENSOR 		= 2;
	private static final int PORT_STATUS 		= 3;

	RSATrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.RSA);
	}

	@Override
	public double getRudderAngle(Side side) {
		if (Side.STARBOARD.equals(side)) {
			return getDoubleValue(STARBOARD_SENSOR);
		}
		return getDoubleValue(PORT_SENSOR);
	}

	@Override
	public DataStatus getStatus(Side side) {
		if (Side.STARBOARD.equals(side)) {
			return DataStatus.valueOf(getCharValue(STARBOARD_STATUS));
		}
		return DataStatus.valueOf(getCharValue(PORT_STATUS));
	}

}
