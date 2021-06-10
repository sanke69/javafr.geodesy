package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.BODTrame;

class BODTrameReader extends NmeaTrameReader implements BODTrame {

	public BODTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.BOD);
	}

	@Override public String getDestinationWaypointId() 	{ return getStringValue(DESTINATION); }
	@Override public double getMagneticBearing() 		{ return getDoubleValue(BEARING_MAGN); }
	@Override public String getOriginWaypointId() 		{ return getStringValue(ORIGIN); }
	@Override public double getTrueBearing() 			{ return getDoubleValue(BEARING_TRUE); }

}
