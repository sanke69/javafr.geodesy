package fr.gms.navigation.gnss.properties.constants;

public enum FaaMode {
	AUTOMATIC('A'),		// Operating in autonomous mode (automatic 2D/3D).
	MANUAL('M'),		// Operating in manual mode (forced 2D or 3D).
	DGPS('D'),			// Operating in differential mode (DGPS).
	ESTIMATED('E'),		// Operating in estimating mode (dead-reckoning).
	PRECISE('P'),		// Operating in precise mode, no degradation like Selective Availability (NMEA 4.00 and later).
	SIMULATED('S'),		// Simulated data (running in simulator/demo mode)
	NONE('N');			// No valid GPS data available.

	private final char mode;

	FaaMode(char _ch) {
		mode = _ch;
	}

	public char toChar() {
		return mode;
	}

	public static FaaMode valueOf(char ch) {
		for (FaaMode gm : values()) {
			if (gm.toChar() == ch) {
				return gm;
			}
		}
		return valueOf(String.valueOf(ch));
	}
}
