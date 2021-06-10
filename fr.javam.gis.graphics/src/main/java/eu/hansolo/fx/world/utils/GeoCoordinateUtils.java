package eu.hansolo.fx.world.utils;

import fr.geodesic.Location;

public class GeoCoordinateUtils {
    private static final double                   EARTH_RADIUS      = 6_371_000; // [m]

    public double calcDistanceInMeter			(final Location _l1, final Location _l2) {
        return calcDistanceInMeter(_l1.getLatitude(), _l1.getLongitude(), _l2.getLatitude(), _l2.getLongitude());
    }
    public double calcDistanceInKilometer		(final Location _l1, final Location _l2) {
        return calcDistanceInMeter(_l1, _l2) / 1000.0;
    }
    public double calcDistanceInMeter			(final double _lat1, final double _long1, final double _lat2, final double _long2) {
        final double LAT_1_RADIANS     = Math.toRadians(_lat1);
        final double LAT_2_RADIANS     = Math.toRadians(_lat2);
        final double DELTA_LAT_RADIANS = Math.toRadians(_lat2-_lat1);
        final double DELTA_LON_RADIANS = Math.toRadians(_long2-_long1);

        final double A = Math.sin(DELTA_LAT_RADIANS * 0.5) * Math.sin(DELTA_LAT_RADIANS * 0.5) + Math.cos(LAT_1_RADIANS) * Math.cos(LAT_2_RADIANS) * Math.sin(DELTA_LON_RADIANS * 0.5) * Math.sin(DELTA_LON_RADIANS * 0.5);
        final double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1-A));

        final double DISTANCE = EARTH_RADIUS * C;

        return DISTANCE;
    }
   
}
