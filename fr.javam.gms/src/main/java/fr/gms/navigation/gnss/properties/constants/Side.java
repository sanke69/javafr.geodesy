package fr.gms.navigation.gnss.properties.constants;

public enum Side {
	PORT('P'),		// Port
	STARBOARD('S');	// Right

	private char nmea;

	private Side(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static Side valueOf(char _ch) {
		for (Side d : values())
			if (d.toChar() == _ch)
				return d;

		return valueOf(String.valueOf(_ch));
	}
}
