package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;

public interface VBWTrame {

	double 		getLongWaterSpeed();
	double 		getLongGroundSpeed();

	double 		getTravWaterSpeed();
	double 		getTravGroundSpeed();

	DataStatus 	getWaterSpeedStatus();
	DataStatus 	getGroundSpeedStatus();

	double 		getSternWaterSpeed();
	DataStatus 	getSternWaterSpeedStatus();
	double 		getSternGroundSpeed();
	DataStatus 	getSternGroundSpeedStatus();

}
