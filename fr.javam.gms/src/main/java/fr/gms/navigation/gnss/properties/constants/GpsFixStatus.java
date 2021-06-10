package fr.gms.navigation.gnss.properties.constants;

public enum GpsFixStatus {
	GPS_NA(1),		// No GPS fix available
	GPS_2D(2),		// 2D GPS fix (lat/lon)
	GPS_3D(3);		// 3D GPS fix (lat/lon/alt)

	private final int status;

	GpsFixStatus(int _value) {
		status = _value;
	}

	public int toInt() {
		return status;
	}

	public static GpsFixStatus valueOf(int val) {
		for (GpsFixStatus st : values())
			if (st.toInt() == val)
				return st;

		return valueOf(String.valueOf(val));
	}
}
