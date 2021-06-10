package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.properties.constants.GpsFixStatus;

public interface GSATrame {

	GpsFixStatus getFixStatus();

	double getHorizontalDOP();

	FaaMode getMode();

	double getPositionDOP();

	String[] getSatelliteIds();

	double getVerticalDOP();

}
