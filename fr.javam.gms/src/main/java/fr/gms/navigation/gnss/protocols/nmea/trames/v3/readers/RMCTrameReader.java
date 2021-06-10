package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.CompassPoint;
import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.properties.values.DateX;
import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.RMCTrame;

class RMCTrameReader extends NmeaTrameReader implements RMCTrame {

	private static final int UTC_TIME = 0;
	private static final int DATA_STATUS = 1;
	private static final int LATITUDE = 2;
	private static final int LAT_HEMISPHERE = 3;
	private static final int LONGITUDE = 4;
	private static final int LON_HEMISPHERE = 5;
	private static final int SPEED = 6;
	private static final int COURSE = 7;
	private static final int UTC_DATE = 8;
	private static final int MAG_VARIATION = 9;
	private static final int VAR_HEMISPHERE = 10;
	private static final int MODE = 11;

	RMCTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.RMC);
	}

	public double getCorrectedCourse() {
		return getCourse() + getVariation();
	}

	public double getCourse() {
		return getDoubleValue(COURSE);
	}

	public DateX getDate() {
		return new DateX(getStringValue(UTC_DATE));
	}

	public CompassPoint getDirectionOfVariation() {
		return CompassPoint.valueOf(getCharValue(VAR_HEMISPHERE));
	}

	public FaaMode getMode() {
		return FaaMode.valueOf(getCharValue(MODE));
	}

	public Position getPosition() {
		return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
	}

	public double getSpeed() {
		return getDoubleValue(SPEED);
	}

	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(DATA_STATUS));
	}

	public TimeX getTime() {
		String str = getStringValue(UTC_TIME);
		return new TimeX(str);
	}

	public double getVariation() {
		double variation = getDoubleValue(MAG_VARIATION);
		if (CompassPoint.EAST == getDirectionOfVariation() && variation > 0) {
			variation = -(variation);
		}
		return variation;
	}

}
