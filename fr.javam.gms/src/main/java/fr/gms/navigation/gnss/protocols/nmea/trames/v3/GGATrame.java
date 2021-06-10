package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.java.patterns.measurable.Unit;

import fr.gms.navigation.gnss.properties.constants.GpsFixQuality;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.PositionData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

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