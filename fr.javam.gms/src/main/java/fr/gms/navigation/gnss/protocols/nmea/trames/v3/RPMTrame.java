package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;

public interface RPMTrame {
	public static final char ENGINE = 'E';

	/** Source indicator for shaft */
	public static final char SHAFT = 'S';

	int 		getId();
	double 		getPitch();
	double 		getRPM();
	char 		getSource();
	DataStatus 	getStatus();
	boolean 	isEngine();
	boolean 	isShaft();

}
