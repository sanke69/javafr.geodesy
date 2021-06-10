package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.constants.FaaMode;

public interface XTETrame {

	DataStatus 	getCycleLockStatus();
	double 		getMagnitude();
	FaaMode 	getMode();
	DataStatus 	getStatus();
	Direction 	getSteerTo();

}
