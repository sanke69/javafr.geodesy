package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import java.util.ArrayList;
import java.util.List;

import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.properties.constants.GpsFixStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GSATrame;

class GSATrameReader extends NmeaTrameReader implements GSATrame {

	// field indices
	private static final int GPS_MODE        = 0;
	private static final int FIX_MODE        = 1;
	private static final int FIRST_SV        = 2;
	private static final int LAST_SV         = 13;
	private static final int POSITION_DOP    = 14;
	private static final int HORIZONTAL_DOP  = 15;
	private static final int VERTICAL_DOP    = 16;

	GSATrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GSA);
	}

	public GpsFixStatus 	getFixStatus() { return GpsFixStatus.valueOf(getIntValue(FIX_MODE)); }
	public double 			getHorizontalDOP() { return getDoubleValue(HORIZONTAL_DOP); }
	public FaaMode 			getMode() { return FaaMode.valueOf(getCharValue(GPS_MODE)); }
	public double 			getPositionDOP() { return getDoubleValue(POSITION_DOP); }

	public String[] 		getSatelliteIds() {
		List<String> result = new ArrayList<String>();
		for (int i = FIRST_SV; i <= LAST_SV; i++) {
			if (hasValue(i)) {
				result.add(getStringValue(i));
			}
		}
		return result.toArray(new String[result.size()]);
	}

	public double 			getVerticalDOP() {
		return getDoubleValue(VERTICAL_DOP);
	}

}
