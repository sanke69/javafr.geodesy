package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.DepthData;

public interface DBTTrame extends DepthData {

	double getFathoms();
	double getFeet();

}
