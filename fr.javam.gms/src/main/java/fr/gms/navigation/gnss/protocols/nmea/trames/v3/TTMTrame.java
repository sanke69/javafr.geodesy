package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.AcquisitionType;
import fr.gms.navigation.gnss.properties.constants.TargetStatus;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;
import fr.java.measure.Unit;

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
