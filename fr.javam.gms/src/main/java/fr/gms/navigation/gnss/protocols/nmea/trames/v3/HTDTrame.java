package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.HeadingData;

public interface HTDTrame extends HTCTrame, HeadingData {

    DataStatus getRudderStatus();
    DataStatus getOffHeadingStatus();
    DataStatus getOffTrackStatus();

}
