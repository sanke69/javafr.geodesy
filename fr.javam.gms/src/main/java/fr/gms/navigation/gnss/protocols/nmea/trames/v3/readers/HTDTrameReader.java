package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.HTDTrame;

class HTDTrameReader extends HTCTrameReader implements HTDTrame {

	private static final int RUDDER_STATUS 		= 13;
	private static final int OFF_HEADING_STATUS = 14;
	private static final int OFF_TRACK_STATUS 	= 15;
	private static final int HEADING 			= 16;

	HTDTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.HTD);
	}

	@Override
	public DataStatus getRudderStatus() {
		if (hasValue(RUDDER_STATUS)) {
			return DataStatus.valueOf(getCharValue(RUDDER_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public DataStatus getOffHeadingStatus() {
		if (hasValue(OFF_HEADING_STATUS)) {
			return DataStatus.valueOf(getCharValue(OFF_HEADING_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public DataStatus getOffTrackStatus() {
		if (hasValue(OFF_TRACK_STATUS)) {
			return DataStatus.valueOf(getCharValue(OFF_TRACK_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public double getHeading() {
		if (hasValue(HEADING)) {
			return getDoubleValue(HEADING);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public boolean isTrue() {
		return isHeadingReferenceInUseTrue();
	}

}
