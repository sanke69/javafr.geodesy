package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.constants.SteeringMode;
import fr.gms.navigation.gnss.properties.constants.TurnMode;

public interface HTCTrame {

    DataStatus 		getOverride();
    SteeringMode 	getSelectedSteeringMode();
    TurnMode 		getTurnMode();

    double 			getCommandedRudderAngle();
    Direction 		getCommandedRudderDirection();
    double 			getCommandedRudderLimit();
    double 			getCommandedOffHeadingLimit();
    double 			getCommandedRadiusOfTurnForHeadingChanges();
    double 			getCommandedRateOfTurnForHeadingChanges();
    double 			getCommandedHeadingToSteer();
    double 			getCommandedOffTrackLimit();
    double 			getCommandedTrack();

    boolean 		isHeadingReferenceInUseTrue();

}
