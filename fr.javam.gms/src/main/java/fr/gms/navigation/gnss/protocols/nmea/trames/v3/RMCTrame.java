package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.CompassPoint;
import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.FaaMode;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.DateData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.PositionData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface RMCTrame extends PositionData, TimeData, DateData {


	double 			getCourse();
	double 			getCorrectedCourse();

	CompassPoint 	getDirectionOfVariation();

	FaaMode 		getMode();

	double 			getSpeed();
	default String 	getSpeedUnit() { return "knots"; }

	DataStatus 		getStatus();
	double 			getVariation();

}
