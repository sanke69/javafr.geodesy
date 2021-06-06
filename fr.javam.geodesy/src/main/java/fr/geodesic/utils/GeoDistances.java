package fr.geodesic.utils;

import fr.geodesic.Location;
import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.api.referential.properties.Ellipsoid;

public final class GeoDistances {
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

	

	
	
	
	/**
	 * DISTANCES
	 */
	public static final Ellipsoid ellipsoid = Ellipsoid.WGS84;

	public static final Datum UTM   = Datum.UTM;
	public static final Datum WGS84 = Datum.WGS84;

    static final int 	maxIters = 10;
    static final double aSqMinusBSqOverBSq = (ellipsoid.equatorialRadius() * ellipsoid.equatorialRadius() - ellipsoid.polarRadius() * ellipsoid.polarRadius()) / (ellipsoid.polarRadius() * ellipsoid.polarRadius());

	public static double getDistanceBetween(GeoCoordinate a, GeoCoordinate b) {
		return getDistanceBetween(a, b, true);
	}
	public static double getDistanceBetween(GeoCoordinate a, GeoCoordinate b, boolean _useEllipsoid) {
		if(!_useEllipsoid) {
			GeoCoordinate.Cartesian2D ca = a.asUTM();
			GeoCoordinate.Cartesian2D cb = b.asUTM();
//			return Math.sqrt( (cb.getX() - ca.getX()) * (cb.getX() - ca.getX()) + (cb.getY() - ca.getY()) * (cb.getY() - ca.getY()) + (cb.getZ() - ca.getZ()) * (cb.getZ() - ca.getZ()));
			return Math.sqrt( (cb.getX() - ca.getX()) * (cb.getX() - ca.getX()) + (cb.getY() - ca.getY()) * (cb.getY() - ca.getY()));
		} else {
			GeoCoordinate.Spheric2D   sa = a.asWGS84();
			GeoCoordinate.Spheric2D   sb = b.asWGS84();
			return distanceTo(sa.getLatitude(), sa.getLongitude(), sb.getLatitude(), sb.getLongitude());
		}
	}

	static float distanceTo(double latitude, double longitude, double latitudeTo, double longitudeTo) {
        // Based on android.location.Location.java
    	// Based on http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
        // using the "Inverse Formula" (section 4)

        // Convert lat/long to radians
        double lat1 = Math.toRadians(latitude); 
        double lon1 = Math.toRadians(longitude);
        
        double lat2 = Math.toRadians(latitudeTo);
        double lon2 = Math.toRadians(longitudeTo);

        double L = lon2 - lon1;
        double A = 0.0;
        double U1 = Math.atan((1.0 - ellipsoid.excentricity()) * Math.tan(lat1));
        double U2 = Math.atan((1.0 - ellipsoid.excentricity()) * Math.tan(lat2));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha;
        double cos2SM;
        double cosSigma;
        double sinSigma;
        double cosLambda;
        double sinLambda;

        double lambda = L; // initial guess
        for(int iter = 0; iter < maxIters; iter++) {
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 :
                cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 :
                cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * // (3)
                (4096.0 + uSquared *
                 (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * // (4)
                (256.0 + uSquared *
                 (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (ellipsoid.excentricity() / 16.0) *
                cosSqAlpha *
                (4.0 + ellipsoid.excentricity() * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * // (6)
                (cos2SM + (B / 4.0) *
                 (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                  (B / 6.0) * cos2SM *
                  (-3.0 + 4.0 * sinSigma * sinSigma) *
                  (-3.0 + 4.0 * cos2SMSq)));

            lambda = L +
                (1.0 - C) * ellipsoid.excentricity() * sinAlpha *
                (sigma + C * sinSigma *
                 (cos2SM + C * cosSigma *
                  (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }
        }

        float distance = (float) (ellipsoid.polarRadius() * A * (sigma - deltaSigma));
        return distance;
    }

	/**
	 * Haversine formulae, implementation based on example at <a href=
	 * "http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe"
	 * >codecodex</a>.
	 * 
	 * @return Distance in meters
	 */
	public static double haversine(GeoCoordinate.Spheric2D _first, GeoCoordinate.Spheric2D _second) {
		assert(_first.getDatum() == _second.getDatum() && _first.getDatum() == Datum.WGS84);

		// Mean earth radius (IUGG) = 6371.009
		// Meridional earth radius = 6367.4491
		// Earth radius by assumption that 1 degree equals exactly 60 NM:
		// 1.852 * 60 * 360 / (2 * Pi) = 6366.7 km

		final double earthRadius = 6366.70702;

		double dLat = Math.toRadians(_second.getLatitude() - _first.getLatitude());
		double dLon = Math.toRadians(_second.getLongitude() - _first.getLongitude());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.cos(Math.toRadians(_first.getLatitude())) * Math.cos(Math.toRadians(_second.getLatitude()))
			* Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (earthRadius * c * 1000);
	}

}
