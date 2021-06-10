package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.VBWTrame;

class VBWTrameReader extends NmeaTrameReader implements VBWTrame {

	public static final int LONG_WATERSPEED = 0;
	public static final int TRAV_WATERSPEED = 1;
	public static final int WATER_SPEED_STATUS = 2;
	public static final int LONG_GROUNDSPEED = 3;
	public static final int TRAV_GROUNDSPEED = 4;
	public static final int GROUND_SPEED_STATUS = 5;
	public static final int STERN_WATERSPEED = 6;
	public static final int STERN_SPEED_STATUS = 7;
	public static final int STERN_GROUNDSPEED = 8;
	public static final int STERN_GROUNDSPEED_STATUS = 9;

	VBWTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.VBW);
	}

	@Override public double getLongWaterSpeed() {
		return getDoubleValue(LONG_WATERSPEED);
	}

	@Override public DataStatus getWaterSpeedStatus() {
		return DataStatus.valueOf(getCharValue(WATER_SPEED_STATUS));
	}

	@Override public DataStatus getGroundSpeedStatus() {
		return DataStatus.valueOf(getCharValue(GROUND_SPEED_STATUS));
	}

	@Override public double getLongGroundSpeed() {
		return getDoubleValue(LONG_GROUNDSPEED);
	}

	@Override public double getTravWaterSpeed() {
		return getDoubleValue(TRAV_WATERSPEED);
	}

	@Override public double getTravGroundSpeed() {
		return getDoubleValue(TRAV_GROUNDSPEED);
	}

	@Override public double getSternWaterSpeed() {
		return getDoubleValue(STERN_WATERSPEED);
	}

	@Override public DataStatus getSternWaterSpeedStatus() {
		return DataStatus.valueOf(getCharValue(STERN_SPEED_STATUS));
	}

	@Override public double getSternGroundSpeed() {
		return getDoubleValue(STERN_GROUNDSPEED);
	}

	@Override public DataStatus getSternGroundSpeedStatus() {
		return DataStatus.valueOf(getCharValue(STERN_GROUNDSPEED_STATUS));
	}

}
