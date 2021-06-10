package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.HeadingData;

public interface VHWTrame extends HeadingData {

	double getMagneticHeading();
	double getSpeedKmh();
	double getSpeedKnots();

}
