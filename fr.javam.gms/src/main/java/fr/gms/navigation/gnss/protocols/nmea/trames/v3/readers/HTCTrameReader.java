package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;
import fr.gms.navigation.gnss.properties.constants.SteeringMode;
import fr.gms.navigation.gnss.properties.constants.TurnMode;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.HTCTrame;

class HTCTrameReader extends NmeaTrameReader implements HTCTrame {

    private static final int OVERRIDE = 0;
    private static final int COMMANDED_RUDDER_ANGLE = 1;
    private static final int COMMANDED_RUDDER_DIRECTION = 2;
    private static final int SELECTED_STEERING_MODE = 3;
    private static final int TURN_MODE = 4;
    private static final int COMMANDED_RUDDER_LIMIT = 5;
    private static final int COMMANDED_OFF_HEADING_LIMIT = 6;
    private static final int COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES = 7;
    private static final int COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES = 8;
    private static final int COMMANDED_HEADING_TO_STEER = 9;
    private static final int COMMANDED_OFF_TRACK_LIMIT = 10;
    private static final int COMMANDED_TRACK = 11;
    private static final int HEADING_REFERENCE_IN_USE = 12;

    HTCTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.HTC);
	}
    HTCTrameReader(NmeaTrameReader _nmeaTrame, Nmea.TrameType _type) {
		super(_nmeaTrame, _type);
	}

    @Override public DataStatus getOverride() 			{ return hasValue(OVERRIDE) 				? DataStatus.valueOf(getCharValue(OVERRIDE)) 	: null; }
    @Override public double getCommandedRudderAngle() 	{ return hasValue(COMMANDED_RUDDER_ANGLE) 	? getDoubleValue(COMMANDED_RUDDER_ANGLE) 		: Double.NaN; }

    @Override
    public Direction getCommandedRudderDirection() {
        if (hasValue(COMMANDED_RUDDER_DIRECTION)) {
            return Direction.valueOf(getCharValue(COMMANDED_RUDDER_DIRECTION));
        } else {
            return null;
        }
    }

    @Override
    public SteeringMode getSelectedSteeringMode() {
        if (hasValue(SELECTED_STEERING_MODE)) {
            return SteeringMode.valueOf(getCharValue(SELECTED_STEERING_MODE));
        } else {
            return null;
        }
    }

    @Override
    public TurnMode getTurnMode() {
        if (hasValue(TURN_MODE)) {
            return TurnMode.valueOf(getCharValue(TURN_MODE));
        } else {
            return null;
        }
    }

    @Override
    public double getCommandedRudderLimit() {
        if (hasValue(COMMANDED_RUDDER_LIMIT)) {
            return getDoubleValue(COMMANDED_RUDDER_LIMIT);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedOffHeadingLimit() {
        if (hasValue(COMMANDED_OFF_HEADING_LIMIT)) {
            return getDoubleValue(COMMANDED_OFF_HEADING_LIMIT);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedRadiusOfTurnForHeadingChanges() {
        if (hasValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES)) {
            return getDoubleValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedRateOfTurnForHeadingChanges() {
        if (hasValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES)) {
            return getDoubleValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedHeadingToSteer() {
        if (hasValue(COMMANDED_HEADING_TO_STEER)) {
            return getDoubleValue(COMMANDED_HEADING_TO_STEER);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedOffTrackLimit() {
        if (hasValue(COMMANDED_OFF_TRACK_LIMIT)) {
            return getDoubleValue(COMMANDED_OFF_TRACK_LIMIT);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getCommandedTrack() {
        if (hasValue(COMMANDED_TRACK)) {
            return getDoubleValue(COMMANDED_TRACK);
        } else {
            return Double.NaN;
        }
    }

    @Override
    public boolean isHeadingReferenceInUseTrue() {
        return hasValue(HEADING_REFERENCE_IN_USE)
                && getCharValue(HEADING_REFERENCE_IN_USE) == 'T';
    }
}
