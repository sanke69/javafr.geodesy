package fr.gms.navigation.gnss.properties.constants;

public enum SteeringMode {
	MANUAL('M'),
	STANDALONE('S'),
	HEADING_CONTROL('H'),
	TRACK_CONTROL('T'),
	RUDDER_CONTROL('R');

	private final char nmea;

	SteeringMode(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static SteeringMode valueOf(char ch) {
		for (SteeringMode sm : values())
			if (sm.toChar() == ch)
				return sm;

		return valueOf(String.valueOf(ch));
	}

}
