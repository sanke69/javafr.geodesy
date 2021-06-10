package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.DateData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface ZDATrame extends TimeData, DateData {

	int getLocalZoneHours();
	int getLocalZoneMinutes();

}
