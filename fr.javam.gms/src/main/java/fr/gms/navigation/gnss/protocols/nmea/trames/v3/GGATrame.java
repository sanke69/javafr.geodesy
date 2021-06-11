package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.GpsFixQuality;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.PositionData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;
import fr.java.measure.Unit;

public interface GGATrame extends PositionData, TimeData {

	double 			getAltitude();
	Unit 			getAltitudeUnits();

	double 			getDgpsAge();
	String 			getDgpsStationId();

	GpsFixQuality 	getFixQuality();

	double 			getGeoidalHeight();
	Unit 			getGeoidalHeightUnits();

	double 			getHorizontalDOP();

	int 			getSatelliteCount();

}