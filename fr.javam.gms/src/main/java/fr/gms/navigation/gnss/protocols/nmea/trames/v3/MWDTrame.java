package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface MWDTrame {

	double getMagneticWindDirection();
	double getTrueWindDirection();
	double getWindSpeed();
	double getWindSpeedKnots();

}
