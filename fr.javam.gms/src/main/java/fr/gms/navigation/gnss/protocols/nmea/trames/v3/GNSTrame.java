package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.protocols.nmea.trames.std.PositionData;
import fr.gms.navigation.gnss.protocols.nmea.trames.std.TimeData;

public interface GNSTrame extends PositionData, TimeData {

	// TODO:: MoveIT !!!
    enum Mode {
        /**
         * Operating in autonomous mode (automatic 2D/3D).
         */
        AUTOMATIC('A'),
        /**
         * Operating in manual mode (forced 2D or 3D).
         */
        MANUAL('M'),
        /**
         * Operating in differential mode (DGPS).
         */
        DGPS('D'),
        /**
         * Operating in estimating mode (dead-reckoning).
         */
        ESTIMATED('E'),
        /**
         * Real Time Kinetic, satellite system used in RTK mode with fixed integers.
         */
        RTK('R'),
        /**
         * Float RTK, satellite system used in real time kinematic mode with floating integers.
         */
        FRTK('F'),
        /**
         * Precise mode, no deliberate degradation like Selective Availability (NMEA 4.00 and later).
         */
        PRECISE('P'),
        /**
         * Simulated data (running in simulator/demo mode)
         */
        SIMULATED('S'),
        /**
         * No valid GPS data available.
         */
        NONE('N');

        private final char ch;

        Mode(char c) {
            this.ch = c;
        }
        public char toChar() {
            return ch;
        }
        public static Mode valueOf(char ch) {
            for (Mode m : values()) {
                if (m.toChar() == ch) {
                    return m;
                }
            }
            return valueOf(String.valueOf(ch));
        }
    }

    Mode 	getGpsMode();
    Mode 	getGlonassMode();
    Mode[] 	getAdditionalModes();

    int 	getSatelliteCount();

    double 	getHorizontalDOP();
    double 	getOrthometricHeight();
    double 	getGeoidalSeparation();

    double 	getDgpsAge();
    String 	getDgpsStationId();

}
