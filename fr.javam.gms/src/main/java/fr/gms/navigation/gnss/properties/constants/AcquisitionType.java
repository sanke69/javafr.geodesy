package fr.gms.navigation.gnss.properties.constants;

public enum AcquisitionType {
	AUTO('A'),
	MANUAL('M'),
	REPORTED('R');

	private char nmea;

	private AcquisitionType(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public static AcquisitionType valueOf(char _ch) {
		for(AcquisitionType d : values())
			if (d.toChar() == _ch)
				return d;

		return valueOf(String.valueOf(_ch));
	}

}
