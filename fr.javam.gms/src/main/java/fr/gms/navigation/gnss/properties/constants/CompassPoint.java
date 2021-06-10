package fr.gms.navigation.gnss.properties.constants;

public enum CompassPoint {
	NORTH('N'),
	EAST('E'),
	SOUTH('S'),
	WEST('W');

	private char nmea;

	private CompassPoint(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static CompassPoint valueOf(char _ch) {
		for (CompassPoint d : values())
			if (d.toChar() == _ch)
				return d;

		return valueOf(String.valueOf(_ch));
	}
}
