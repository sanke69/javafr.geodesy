package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.HeadingData;

public interface HDGTrame extends HeadingData {

	double getDeviation();
	double getVariation();

}
