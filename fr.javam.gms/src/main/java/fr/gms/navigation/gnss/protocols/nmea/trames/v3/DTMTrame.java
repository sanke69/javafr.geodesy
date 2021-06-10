package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface DTMTrame {

	double getAltitudeOffset();
	String getDatumCode();
	String getDatumSubCode();
	double getLatitudeOffset();
	double getLongitudeOffset();
	String getName();

}
