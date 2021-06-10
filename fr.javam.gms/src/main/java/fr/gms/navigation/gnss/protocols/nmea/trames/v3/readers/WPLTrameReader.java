package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.values.Position;
import fr.gms.navigation.gnss.properties.values.Waypoint;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.WPLTrame;

class WPLTrameReader extends NmeaTrameReader implements WPLTrame {

	// field ids
	private static final int LATITUDE 		= 0;
	private static final int LAT_HEMISPHERE = 1;
	private static final int LONGITUDE 		= 2;
	private static final int LON_HEMISPHERE = 3;
	private static final int WAYPOINT_ID 	= 4;

	WPLTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.WPL);
	}

	public Waypoint getWaypoint() {
		String   id = getStringValue(WAYPOINT_ID);
		Position p  = parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
		
		return p.toWaypoint(id);
	}

}
