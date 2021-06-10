package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface GBSTrame extends TimeData {

    double getLatitudeError();
    double getLongitudeError();
    double getAltitudeError();

    String getSatelliteId();

    double getProbability();
    double getEstimate();
    double getDeviation();

}
