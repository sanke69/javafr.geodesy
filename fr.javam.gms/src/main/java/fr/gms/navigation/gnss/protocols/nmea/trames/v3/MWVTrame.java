package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.java.measure.Unit;

public interface MWVTrame {

	double 		getAngle();
	double 		getSpeed();
	Unit 		getSpeedUnit();
	DataStatus 	getStatus();
	boolean 	isTrue();

}
