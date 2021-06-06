package fr.geodesic.utils.converters;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.sdk.math.Angles;

public class Lambert93Converter implements GeoCoordinate.Converter {

	private final static double	M_PI_2			= Math.PI / 2.0;
	private final static double	DEFAULT_EPS		= 1e-10;
	private final static double	LON_MERID_IERS	= 3.0 * Math.PI / 180.0;
	private final static double	N				= 0.725_607_7650;
	private final static double	C				= 11_754_255.426;
	private final static double	E_WGS84			= 0.08181919106;
	private final static double	XS				= 700_000.000;
	private final static double	YS				= 12_655_612.049876;

	private final static double E2 = E_WGS84 / 2.0;

	double atanh(double x) {
		return 0.5 * Math.log((1.0 + x) / (1.0 - x));
	}

	public GeoCoordinate.Cartesian2D fromLatLong(GeoCoordinate _coord) {
		if(_coord.getDatum() != Datum.WGS84)
			throw new NotYetImplementedException();

		double longitude = ((GeoCoordinate.Spheric2D) _coord).getLongitude();
		double latitude  = ((GeoCoordinate.Spheric2D) _coord).getLatitude();

		double latRad = Angles.Degree2Radian(latitude);

		double latISO = atanh(Math.sin(latRad)) - E_WGS84 * atanh(E_WGS84 * Math.sin(latRad));

		double X = (C * exp(-N * (latISO))) * Math.sin(N * (longitude - 3) / 180 * Math.PI) + XS;
		double Y = (YS - (C * exp(-N * (latISO))) * Math.cos(N * (longitude - 3) / 180 * Math.PI));

		return GeoCoordinates.newLambert93(X, Y);
		/*
		double dX = longitude - XS;
		double dY = latitude - YS;
		double R = sqrt(dX * dX + dY * dY);
		//		double latISO = -1 / N * log(abs(R / C));
		
		final double dX = _coord.spheric().getLongitude() - XS;
		final double dY = _coord.spheric().getLatitude() - YS;
		final double R = sqrt(dX * dX + dY * dY);
		final double gamma = atan(dX / -dY);
		final double latIso = -1 / N * log(abs(R / C));
		
		Coordinate out = new Coordinate();
		
		out.x = Math.toDegrees(LON_MERID_IERS + gamma / N);
		out.y = Math.toDegrees(latitudeFromLatitudeISO(latIso));
		*/
	}

	public GeoCoordinate.Spheric2D toLatLong(GeoCoordinate _coord) {
		if(_coord.getDatum() != Datum.Lambert93)
			throw new NotYetImplementedException();
		
		GeoCoordinate.Cartesian2D lambert93 = (GeoCoordinate.Cartesian2D) _coord; 
/*
		double X = _coord.cartesian().getX();
		double Y = _coord.cartesian().getY();

		double a = Math.log(C / (sqrt(pow((X - XS), 2) + pow((Y - YS), 2)))) / N;

		double longitude = Math.atan(-(X - XS) / (Y - YS) / N + LON_MERID_IERS) / Math.PI * 180.0;
		double latitude = Math.asin(Math.tanh(
				(log(C / sqrt(pow((X - XS), 2) + pow((Y - YS), 2))) / N)
						+ E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * (Math.tanh(a + E_WGS84 * atanh(E_WGS84 * sin(1))))))))))))))))))))))
				/ Math.PI * 180;

		return Coordinates.geoWGS84(longitude, latitude);
		*/
		double X = lambert93.getX();
		double Y = lambert93.getY();
		
		final double dX     = X - XS;
		final double dY     = Y - YS;
		final double R      = sqrt( dX * dX + dY * dY );
		final double gamma  = atan( dX / -dY );
		final double latIso = -1 / N * log( abs( R / C ) );

		return GeoCoordinates.newWGS84(Math.toDegrees( LON_MERID_IERS + gamma / N ), Math.toDegrees( latitudeFromLatitudeISO( latIso )));
	}

	public static void main(String[] args) {
		GeoCoordinate wgs84 = GeoCoordinates.newWGS84(2.144804, 48.793139);
		GeoCoordinate lambert93 = GeoCoordinates.newLambert93(637175.9, 6855124.1);

		Lambert93Converter conv = new Lambert93Converter();

		System.out.println(conv.fromLatLong(wgs84) + "\t" + lambert93);
		System.out.println(conv.toLatLong(lambert93) + "\t" + wgs84);
//		System.out.println(conv.toLatLong(lambert93).coord0());
//		System.out.println(conv.toLatLong(lambert93).coord1());

	}

	private static double latitudeFromLatitudeISO(final double latISo) {
		double phi0 = 2 * atan(exp(latISo)) - M_PI_2;
		double phiI = 2
				* atan(pow((1 + E_WGS84 * sin(phi0)) /
						(1 - E_WGS84 * sin(phi0)), E2) * exp(latISo))
				- M_PI_2;
		double delta = abs(phiI - phi0);
		while(delta > DEFAULT_EPS) {
			phi0 = phiI;
			phiI = 2 * atan(pow(
					(1 + E_WGS84 * sin(phi0)) /
							(1 - E_WGS84 * sin(phi0)),
					E2)
					* exp(latISo)) - M_PI_2;
			delta = abs(phiI - phi0);
		}
		return phiI;
	}

}