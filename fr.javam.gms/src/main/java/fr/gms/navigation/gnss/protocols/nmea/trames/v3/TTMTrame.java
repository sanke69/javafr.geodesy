package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.java.patterns.measurable.Unit;

import fr.gms.navigation.gnss.properties.constants.AcquisitionType;
import fr.gms.navigation.gnss.properties.constants.TargetStatus;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface TTMTrame extends TimeData {

	int 			getNumber();

	double 			getDistance();
	double 			getBearing();

	double 			getSpeed();

	double 			getCourse();

	double 			getDistanceOfCPA();
	double 			getTimeToCPA();

	Unit 			getUnits();

	String 			getName();

	TargetStatus 	getStatus();

	boolean 		getReference();

	AcquisitionType getAcquisitionType();

}
