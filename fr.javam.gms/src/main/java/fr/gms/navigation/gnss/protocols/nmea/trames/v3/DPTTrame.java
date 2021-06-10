package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.DepthData;

public interface DPTTrame extends DepthData {

	double getOffset();
	double getMaximum();

}
