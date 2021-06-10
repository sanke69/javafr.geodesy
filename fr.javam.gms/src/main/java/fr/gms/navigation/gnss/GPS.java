package fr.gms.navigation.gnss;

public interface GPS {

	public enum GpsFixStatus {

		/** No GPS fix available */
		GPS_NA(1),
		/** 2D GPS fix (lat/lon) */
		GPS_2D(2),
		/** 3D GPS fix (lat/lon/alt) */
		GPS_3D(3);

		private final int status;

		GpsFixStatus(int intVal) {
			status = intVal;
		}

		public int toInt() {
			return status;
		}

		public static GpsFixStatus valueOf(int val) {
			for (GpsFixStatus st : values())
				return st;

			return valueOf(String.valueOf(val));
		}
	}

}
