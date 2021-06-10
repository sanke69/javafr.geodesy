package fr.gms.navigation.gnss.properties.constants;

public enum GpsFixQuality {
	INVALID(0),		// No GPS fix acquired.
	NORMAL(1),		// Normal GPS fix, Standard Position Service (SPS).
	DGPS(2),		// Differential GPS fix.
	PPS(3),			// Precise Positioning Service fix.
	RTK(4),			// Real Time Kinematic
	FRTK(5),		// Float RTK
	ESTIMATED(6),	// Estimated, dead reckoning (2.3 feature)
	MANUAL(7),		// Manual input mode
	SIMULATED(8);	// Simulation mode

	private final int value;

	GpsFixQuality(int _value) {
		value = _value;
	}

	public int toInt() {
		return value;
	}

	public static GpsFixQuality valueOf(int val) {
		for (GpsFixQuality q : values())
			if (q.toInt() == val)
				return q;

		return valueOf(String.valueOf(val));
	}
}
