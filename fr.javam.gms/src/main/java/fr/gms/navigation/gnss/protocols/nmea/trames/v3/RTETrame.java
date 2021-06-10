package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface RTETrame {

	String 		getRouteId();
	
	int 		getWaypointCount();
	String[] 	getWaypointIds();

	int 		getSentenceCount();
	int 		getSentenceIndex();

	boolean 	isFirst();
	boolean 	isLast();

	boolean 	isActiveRoute();
	boolean 	isWorkingRoute();

}
