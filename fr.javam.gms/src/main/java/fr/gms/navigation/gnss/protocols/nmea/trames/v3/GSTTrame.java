package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface GSTTrame extends TimeData {

	double getPseudoRangeResidualsRMS();
	double getSemiMajorError();
	double getSemiMinorError();
	double getErrorEllipseOrientation();
	double getLatitudeError();
	double getLongitudeError();
	double getAltitudeError();

}
