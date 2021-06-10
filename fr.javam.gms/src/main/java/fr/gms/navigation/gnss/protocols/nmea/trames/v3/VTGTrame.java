package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.FaaMode;

public interface VTGTrame {

	/** Char indicator for "true" */
	char TRUE = 'T';
	/** Char indicator for "magnetic" */
	char MAGNETIC = 'M';
	/** Units indicator for kilometers per hour */
	char KMPH = 'K';
	/** Units indicator for knots (nautical miles per hour) */
	char KNOT = 'N';
	/** Operating in manual mode (forced 2D or 3D). */
	char MODE_MANUAL = 'M';
	/** Operating in automatic mode (2D/3D). */
	char MODE_AUTOMATIC = 'A';

	double getMagneticCourse();
	FaaMode getMode();
	double getSpeedKmh();
	double getSpeedKnots();
	double getTrueCourse();

}
