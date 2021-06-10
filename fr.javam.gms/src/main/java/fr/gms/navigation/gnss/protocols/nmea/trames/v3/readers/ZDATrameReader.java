package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.values.DateX;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.ZDATrame;

class ZDATrameReader extends NmeaTrameReader implements ZDATrame {

	// field indices
	private static final int UTC_TIME = 0;
	private static final int DAY = 1;
	private static final int MONTH = 2;
	private static final int YEAR = 3;
	private static final int LOCAL_ZONE_HOURS = 4;
	private static final int LOCAL_ZONE_MINUTES = 5;

	ZDATrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.ZDA);
	}


	public DateX getDate() {
		int y = getIntValue(YEAR);
		int m = getIntValue(MONTH);
		int d = getIntValue(DAY);
		return new DateX(y, m, d);
	}

	public int getLocalZoneHours() {
		return getIntValue(LOCAL_ZONE_HOURS);
	}

	public int getLocalZoneMinutes() {
		return getIntValue(LOCAL_ZONE_MINUTES);
	}

	public TimeX getTime() {

		String str = getStringValue(UTC_TIME);
		int tzHrs = getLocalZoneHours();
		int tzMin = getLocalZoneMinutes();

		TimeX t = new TimeX(str);
		t.setOffsetHours(tzHrs);
		t.setOffsetMinutes(tzMin);

		return t;
	}

}
