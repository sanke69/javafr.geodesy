package fr.gms.navigation.gnss.properties.constants;

public enum TurnMode {
	RADIUS_CONTROLLED('R'),
	TURN_RATE_CONTROLLED('T'),
	NOT_CONTROLLED('N');

	private final char nmea;

	TurnMode(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static TurnMode valueOf(char _ch) {
		for (TurnMode tm : values())
			if (tm.toChar() == _ch)
				return tm;

		return valueOf(String.valueOf(_ch));
	}
}
