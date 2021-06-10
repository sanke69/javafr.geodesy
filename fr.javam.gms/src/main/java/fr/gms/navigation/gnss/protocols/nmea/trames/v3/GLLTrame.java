package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.PositionData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface GLLTrame extends PositionData, TimeData {

	DataStatus 	getStatus();
	FaaMode 	getMode();

}