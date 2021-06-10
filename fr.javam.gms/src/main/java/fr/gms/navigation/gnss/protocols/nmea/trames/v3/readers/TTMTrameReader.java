package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.java.patterns.measurable.Unit;

import fr.gms.navigation.gnss.properties.constants.AcquisitionType;
import fr.gms.navigation.gnss.properties.constants.TargetStatus;
import fr.gms.navigation.gnss.properties.values.TimeX;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.NmeaCodec;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.TTMTrame;

class TTMTrameReader extends NmeaTrameReader implements TTMTrame {

	private static final int NUMBER 			= 0;
	private static final int DISTANCE 			= 1;
	private static final int BEARING 			= 2;
	private static final int BEARING_TRUE_REL 	= 3;
	private static final int SPEED 				= 4;
	private static final int COURSE 			= 5;
	private static final int COURSE_TRUE_REL 	= 6;
	private static final int DISTANCE_CPA 		= 7;
	private static final int TIME_CPA 			= 8;
	private static final int UNITS 				= 9;
	private static final int NAME 				= 10;
	private static final int STATUS 			= 11;
	private static final int REFERENCE 			= 12;
	private static final int UTC_TIME 			= 13;
	private static final int ACQUISITON_TYPE 	= 14;

	TTMTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.TTM);
	}

	@Override public TimeX getTime() { String str = getStringValue(UTC_TIME); return new TimeX(str); }
	@Override public int getNumber() { return getIntValue(NUMBER); }
	@Override public double getDistance() { return getDoubleValue(DISTANCE); }
	@Override public double getBearing() { return getDoubleValue(BEARING); }
	@Override public double getSpeed() { return getDoubleValue(SPEED); }
	@Override public double getCourse() { return getDoubleValue(COURSE); }
	@Override public double getDistanceOfCPA() { return getDoubleValue(DISTANCE_CPA); }
	@Override public double getTimeToCPA() { return getDoubleValue(TIME_CPA); }
	@Override public Unit getUnits() { return NmeaCodec.decodeUnit(getCharValue(UNITS)); }
	@Override public String getName() { return getStringValue(NAME); }
	@Override public TargetStatus getStatus() { return TargetStatus.valueOf(getCharValue(STATUS)); }
	@Override public AcquisitionType getAcquisitionType() { return AcquisitionType.valueOf(getCharValue(ACQUISITON_TYPE)); }
	@Override public boolean getReference() { return getCharValue(REFERENCE) == 'R'; }

}
