package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

public interface VLWTrame {
	/** Kilometers */
	public static final char KM = 'K';
	/** Nautical miles */
	public static final char NM = 'N';

	double 	getTotal();
	char 	getTotalUnits();

	double 	getTrip();
	char 	getTripUnits();

}
