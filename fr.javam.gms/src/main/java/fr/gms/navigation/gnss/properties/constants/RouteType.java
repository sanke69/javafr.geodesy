package fr.gms.navigation.gnss.properties.constants;

public enum RouteType {
	ACTIVE('c'),		// Active route: complete, all waypoints in route order.
	WORKING('w');		// Working route: the waypoint you just left, the waypoint you're heading to and then all the rest.

	private final char nmea;

	private RouteType(char _ch) {
		nmea = _ch;
	}

	public char toChar() {
		return nmea;
	}

	public RouteType valueOf(char _ch) {
		for (RouteType type : values()) {
			if (type.toChar() == _ch) {
				return type;
			}
		}
		return valueOf(String.valueOf(_ch));
	}

}
