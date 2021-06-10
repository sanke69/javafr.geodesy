package fr.gms.navigation.gnss.protocols.nmea.trames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.gms.navigation.gnss.properties.constants.CompassPoint;
import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.protocols.exceptions.DataNotAvailableException;
import fr.gms.navigation.gnss.protocols.exceptions.ParseException;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.Checksum;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameEmitter;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameType;

public /*abstract*/ class NmeaTrameReader implements Nmea.Trame {

	char         		beginChar;
	String 				emitterId;
	String				trameId;
	List<String> 		fields = new ArrayList<String>();

	public NmeaTrameReader(String _nmea) {
		this(_nmea, null);
	}
	public NmeaTrameReader(String _nmea, Nmea.TrameType _type) {
		if (!Nmea.Validator.isValid(_nmea)) {
			String msg = String.format("Invalid data [%s]", _nmea);
			throw new IllegalArgumentException(msg);
		}

		beginChar       = _nmea.charAt(0);
		emitterId       = Nmea.Trame.emitterAsString(_nmea);
		trameId      	= Nmea.Trame.typeAsString(_nmea);

		int csToken     = Nmea.Checksum.index(_nmea);
		int checksum    = Integer.valueOf(_nmea.substring(csToken + 1, csToken + 2));

		String[] values = _nmea.substring(7, csToken).split(new String(new byte[] { Nmea.FIELD_DELIMITER }), -1);
		fields.addAll(Arrays.asList(values));
	}
	public NmeaTrameReader(NmeaTrameReader _nmea, Nmea.TrameType _type) {
		super();
		
		if(_nmea.getType() != _type)
			throw new IllegalArgumentException("Content of _nmea is not compliant with " + _type);
		
		beginChar       = _nmea.beginChar;
		emitterId       = _nmea.emitterId;
		trameId      	= _nmea.trameId;
		fields.addAll(_nmea.fields);
	}

	public char				getStartByte() {
		return beginChar;
	}
	public TrameEmitter 	getEmitter() {
		return TrameEmitter.valueOf(emitterId);
	}
	public TrameType		getType() {
		return TrameType.valueOf(trameId);
	}

	@Override
	public int 				getFieldCount() {
		return fields.size();
	}

	protected final boolean hasValue(int index) {
		return fields.size() > index &&
			fields.get(index) != null && !fields.get(index).isEmpty();
	}

	protected final char 	getCharValue(int index) {
		String val = getStringValue(index);
		if (val.length() > 1) {
			String msg = String.format("Expected char, found String [%s]", val);
			throw new ParseException(msg);
		}
		return val.charAt(0);
	}
	protected final String 	getStringValue(int index) {
		String value = fields.get(index);
		if (value == null || "".equals(value)) {
			throw new DataNotAvailableException("Data not available");
		}
		return value;
	}
	protected final int 	getIntValue(int index) {
		int value;
		try {
			value = Integer.parseInt(getStringValue(index));
		} catch (NumberFormatException ex) {
			throw new ParseException("Field does not contain integer value", ex);
		}
		return value;
	}
	protected final double 	getDoubleValue(int index) {
		double value;
		try {
			value = Double.parseDouble(getStringValue(index));
		} catch (NumberFormatException ex) {
			throw new ParseException("Field does not contain double value", ex);
		}
		return value;
	}

	protected Position 		parsePosition(int latIndex, int latHemIndex, int lonIndex, int lonHemIndex) {
		double 			lat  = parseLatitude(latIndex);
		double 			lon  = parseLongitude(lonIndex);
		CompassPoint 	lath = parseHemisphereLat(latHemIndex);
		CompassPoint 	lonh = parseHemisphereLon(lonHemIndex);
		if (lath.equals(CompassPoint.SOUTH)) {
			lat = -lat;
		}
		if (lonh.equals(CompassPoint.WEST)) {
			lon = -lon;
		}
		return new Position(lat, lon);
	}
	protected double 		parseLongitude(int index) {
		String field = getStringValue(index);
		int deg = Integer.parseInt(field.substring(0, 3));
		double min = Double.parseDouble(field.substring(3));
		return deg + (min / 60);
	}
	protected CompassPoint 	parseHemisphereLon(int index) {
		char ch = getCharValue(index);
		CompassPoint d = CompassPoint.valueOf(ch);
		if (d != CompassPoint.EAST && d != CompassPoint.WEST) {
			throw new ParseException("Invalid longitude hemisphere " + ch + "'");
		}
		return d;
	}
	protected double 		parseLatitude(int index) {
		String field = getStringValue(index);
		int    deg   = Integer.parseInt(field.substring(0, 2));
		double min   = Double.parseDouble(field.substring(2));
		return deg + (min / 60);
	}
	protected CompassPoint 	parseHemisphereLat(int index) {
		char ch = getCharValue(index);
		CompassPoint d = CompassPoint.valueOf(ch);
		if (d != CompassPoint.NORTH && d != CompassPoint.SOUTH) {
			throw new ParseException("Invalid latitude hemisphere '" + ch + "'");
		}
		return d;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Nmea.MAX_LENGTH);

		sb.append(emitterId);
		sb.append(trameId);
		
		for (String field : fields) {
			sb.append((char) Nmea.FIELD_DELIMITER);
			sb.append(field == null ? "" : field);
		}
		
		final String checksum = Checksum.xor(sb.toString());
		sb.append((char) Nmea.CHECKSUM_DELIMITER);
		sb.append(checksum);

		sb.insert(0, beginChar);

		return sb.toString();
	}

}
