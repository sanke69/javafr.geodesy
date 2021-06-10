package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.values.Waypoint;

public interface RMBTrame {

	DataStatus 	getArrivalStatus();
	double 		getBearing();
	double 		getCrossTrackError();
	Waypoint 	getDestination();
	String 		getOriginId();
	double 		getRange();
	DataStatus 	getStatus();
	Direction 	getSteerTo();
	double 		getVelocity();
	boolean 	hasArrived();

}
