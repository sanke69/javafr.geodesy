package fr.gms.navigation.gnss.properties.constants;

public enum Direction {
	LEFT('L'),
	RIGHT('R');

	private char nmea;

	private Direction(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static Direction valueOf(char _ch) {
		for (Direction d : values())
			if (d.toChar() == _ch)
				return d;

		return valueOf(String.valueOf(_ch));
	}
}
