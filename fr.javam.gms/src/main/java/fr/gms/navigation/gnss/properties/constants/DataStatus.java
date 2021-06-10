package fr.gms.navigation.gnss.properties.constants;

public enum DataStatus {
	ACTIVE('A'),
	VOID('V');

	private final char nmea;

	DataStatus(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static DataStatus valueOf(char _ch) {
		for (DataStatus ds : values())
			if (ds.toChar() == _ch)
				return ds;

		return valueOf(String.valueOf(_ch));
	}
}
