package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface CURTrame {

	double getCurrentSpeed();
	double getCurrentDirection();
	String getCurrentDirectionReference();
	String getCurrentHeadingReference();

}
