package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface BODTrame {
	// field indices
	static final int BEARING_TRUE   = 0;
	static final int TRUE_INDICATOR = 1;
	static final int BEARING_MAGN   = 2;
	static final int MAGN_INDICATOR = 3;
	static final int DESTINATION    = 4;
	static final int ORIGIN         = 5;

	String getDestinationWaypointId();
	double getMagneticBearing();
	String getOriginWaypointId();
	double getTrueBearing();

}
