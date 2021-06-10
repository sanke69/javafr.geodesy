package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.java.patterns.measurable.Unit;

import fr.gms.navigation.gnss.properties.constants.DataStatus;

public interface MWVTrame {

	double 		getAngle();
	double 		getSpeed();
	Unit 		getSpeedUnit();
	DataStatus 	getStatus();
	boolean 	isTrue();

}
