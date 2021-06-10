package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;

public interface ROTTrame {

	double 		getRateOfTurn();
	DataStatus 	getStatus();

}
