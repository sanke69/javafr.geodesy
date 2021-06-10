package fr.gms.navigation.gnss.protocols.nmea.trames;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;

public class NmeaTrameWriter extends NmeaTrameReader  {

	protected NmeaTrameWriter(char begin, Nmea.TrameEmitter tid, Nmea.TrameType sid, int size) {
		super(null);

		beginChar = begin;
		String[] values = new String[size];
		Arrays.fill(values, "");
		fields.addAll(Arrays.asList(values));
	}

	public void setBeginChar(char ch) {
		if (ch != Nmea.BEGIN_CHAR && ch != Nmea.BEGIN_CHAR_ALT) {
			String msg = "Invalid begin char; expected '$' or '!'";
			throw new IllegalArgumentException(msg);
		}
		beginChar = ch;
	}

	public final void setEmitterId(Nmea.TrameEmitter id) {
		emitterId = id.name();
	}

	public final void reset() {
		for (int i = 0; i < fields.size(); i++) {
			fields.set(i, "");
		}
	}

	protected final void setCharValue(int index, char value) {
		setStringValue(index, String.valueOf(value));
	}

	protected final void setDegreesValue(int index, double deg) {
		if (deg < 0 || deg > 360)
			throw new IllegalArgumentException("Value out of bounds [0..360]");
		setDoubleValue(index, deg, 3, 1);
	}
	protected final void setDoubleValue(int index, double value) {
		setStringValue(index, String.valueOf(value));
	}
	protected final void setDoubleValue(int index, double value, int leading,
		int decimals) {

		StringBuilder pattern = new StringBuilder();
		for (int i = 0; i < leading; i++) {
			pattern.append('0');
		}
		if (decimals > 0) {
			pattern.append('.');
			for (int i = 0; i < decimals; i++) {
				pattern.append('0');
			}
		}
		if (pattern.length() == 0) {
			pattern.append('0');
		}

		DecimalFormat nf = new DecimalFormat(pattern.toString());
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(dfs);

		setStringValue(index, nf.format(value));
	}

	protected final void setFieldCount(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
				"Number of fields must be greater than zero.");
		}
		
		if(size < fields.size()) {
			fields = fields.subList(0, size);
		} else if (size > fields.size()) {
			for(int i = fields.size(); i < size; i++) {
				fields.add("");
			}
		}
	}

	protected final void setIntValue(int index, int value) {
		setStringValue(index, String.valueOf(value));
	}
	protected final void setIntValue(int index, int value, int leading) {
		String pattern = "%d";
		if (leading > 0) {
			pattern = "%0" + leading + "d";
		}
		setStringValue(index, String.format(pattern, value));
	}
	protected final void setStringValue(int index, String value) {
		fields.set(index, value == null ? "" : value);
	}

}
