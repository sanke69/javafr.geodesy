package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import java.util.ArrayList;
import java.util.List;

import fr.java.patterns.measurable.Measure;
import fr.java.patterns.measurable.Measurement;
import fr.java.patterns.measurable.Unit;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.NmeaCodec;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.XDRTrame;

class XDRTrameReader extends NmeaTrameReader implements XDRTrame {

	// length of each data set is 4 fields
	private static int DATA_SET_LENGTH = 4;
	
	// data set field indices, relative to first field of each set
	private static int TYPE_INDEX 	= 0;
	private static int VALUE_INDEX 	= 1;
	private static int UNITS_INDEX	= 2;
	private static int NAME_INDEX 	= 3;

	XDRTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.XDR);
	}

	@Override public List<Measurement> getMeasurements() {
		ArrayList<Measurement> result = new ArrayList<Measurement>();
		for (int i = 0; i < getFieldCount(); i += DATA_SET_LENGTH) {
			Measurement value = fetchValues(i);
			if(value != null)
				result.add(value);
		}
		return result;
	}

	private Measurement fetchValues(int i) {
		String       typeS = hasValue(i) ? getStringValue(i) : null;
		Measure.Type type  = null;
		double       value = hasValue(i + VALUE_INDEX) ? getDoubleValue(i + VALUE_INDEX) : Double.NaN;
		Unit         unit  = hasValue(i + UNITS_INDEX) ? NmeaCodec.decodeUnit( getStringValue(i + UNITS_INDEX).charAt(0) ) : null;
		String       name  = hasValue(i + NAME_INDEX)  ? getStringValue(i + NAME_INDEX)  : "NoName";

		return value != Double.NaN ? Measurement.of(name, type, value, unit) : null;
	}

}
