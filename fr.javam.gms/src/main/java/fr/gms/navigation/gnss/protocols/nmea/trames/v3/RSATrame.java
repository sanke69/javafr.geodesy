package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Side;

public interface RSATrame {

	double 		getRudderAngle(Side side);
	DataStatus 	getStatus(Side side);

}
