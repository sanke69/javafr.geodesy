package fr.gms.navigation.gnss.properties.constants;

public enum TargetStatus {
	QUERY('Q'),
	LOST('L'),
	TRACKING('T');

	private char nmea;

	private TargetStatus(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static TargetStatus valueOf(char _ch) {
		for (TargetStatus d : values())
			if (d.toChar() == _ch)
				return d;

		return valueOf(String.valueOf(_ch));
	}
}
