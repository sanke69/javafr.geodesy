package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.GpsFixQuality;
import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.exceptions.ParseException;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.NmeaCodec;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GGATrame;
import fr.java.measure.Unit;

class GGATrameReader extends NmeaTrameReader implements GGATrame {

	// field indices
	private static final int UTC_TIME            = 0;
	private static final int LATITUDE            = 1;
	private static final int LAT_HEMISPHERE      = 2;
	private static final int LONGITUDE           = 3;
	private static final int LON_HEMISPHERE      = 4;
	private static final int FIX_QUALITY         = 5;
	private static final int SATELLITES_IN_USE   = 6;
	private static final int HORIZONTAL_DILUTION = 7;
	private static final int ALTITUDE            = 8;
	private static final int ALTITUDE_UNITS      = 9;
	private static final int GEOIDAL_HEIGHT      = 10;
	private static final int HEIGHT_UNITS        = 11;
	private static final int DGPS_AGE            = 12;
	private static final int DGPS_STATION_ID     = 13;

	GGATrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GGA);
	}

	@Override public double 		getAltitude() 									{ return getDoubleValue(ALTITUDE); }
	@Override public Unit 			getAltitudeUnits() {
		char ch   = getCharValue(ALTITUDE_UNITS);
		Unit unit = NmeaCodec.decodeUnit(ch);

		if (unit != Unit.METRE && unit != Unit.FEET) {
			String msg = "Invalid altitude unit indicator: %s";
			throw new ParseException(String.format(msg, ch));
		}

		return unit;
	}

	@Override public double 		getDgpsAge() 									{ return getDoubleValue(DGPS_AGE); }
	@Override public String 		getDgpsStationId() 								{ return getStringValue(DGPS_STATION_ID); }
	@Override public GpsFixQuality 	getFixQuality() 								{ return GpsFixQuality.valueOf(getIntValue(FIX_QUALITY)); }
	@Override public double 		getGeoidalHeight() 								{ return getDoubleValue(GEOIDAL_HEIGHT); }
	@Override public Unit 			getGeoidalHeightUnits() 						{ return NmeaCodec.decodeUnit(getCharValue(HEIGHT_UNITS)); }
	@Override public double 		getHorizontalDOP() 								{ return getDoubleValue(HORIZONTAL_DILUTION); }
	@Override public Position 		getPosition() {
		Position pos = parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);

		if(hasValue(ALTITUDE) && hasValue(ALTITUDE_UNITS)) {
			double alt = getAltitude();
			if (getAltitudeUnits().equals(Unit.FEET)) {
				alt = (alt / 0.3048);
			}
			pos.setAltitude(alt);
		}
				
		return pos;
	}

	@Override public int 			getSatelliteCount() 							{ return getIntValue(SATELLITES_IN_USE); }
	@Override public TimeX 			getTime() {
		String str = getStringValue(UTC_TIME);
		return new TimeX(str);
	}

}
