/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.geodesic.utils.converters;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.api.referential.properties.Ellipsoid;
import fr.geodesic.utils.GeoCoordinates;

public class GeoConverterUTM implements GeoCoordinate.Converter {

	public GeoCoordinate.Cartesian2D fromLatLong(GeoCoordinate _wgs84) {
		assert(_wgs84.getDatum() == Datum.WGS84);
		GeoCoordinate.Spheric2D wgs84 = (GeoCoordinate.Spheric2D) _wgs84;

		UTMCoordinate utm = new Polar2UTM().convertLatLonToUTM(wgs84.getLongitude(), wgs84.getLatitude());
		return GeoCoordinates.newUTM(utm.X, utm.zoneLg, utm.Y, utm.zoneLt);
	}

	public GeoCoordinate.Spheric2D toLatLong(GeoCoordinate _utm) {
		assert(_utm.getDatum() == Datum.UTM);
		GeoCoordinate.Cartesian2D utm = (GeoCoordinate.Cartesian2D) _utm;
		
		String utmStr = utm.asUTM().getZoneX() + " " + utm.asUTM().getZoneY() + " " + utm.asUTM().getX() + " " + utm.asUTM().getY();
		
		double[] coords = new UTM2Polar().convertUTMToLatLong(utmStr);
		return GeoCoordinates.newWGS84(coords[1], coords[0]);
	}

	static void validate(double longitude, double latitude) {
		if(latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude >= 180.0)
			throw new IllegalArgumentException("Legal ranges: latitude [-90,90], longitude [-180,180).");
	}

	public static class UTMCoordinate {
		double X, Y;
		String zoneLg;
		String zoneLt;
		
		public UTMCoordinate(double _easting, double _northing, String _zoneLg, String _zoneLt) {
			X = _easting;
			Y = _northing;
			zoneLg = _zoneLg;
			zoneLt = _zoneLt;
		}
	}

	static class LatZones {
		private char[] letters    = { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Z' };
		private int[]  degrees    = { -90, -84, -72, -64, -56, -48, -40, -32, -24, -16, -8, 0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 84 };
		private char[] negLetters = { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M' };
		private int[]  negDegrees = { -90, -84, -72, -64, -56, -48, -40, -32, -24, -16, -8 };
		private char[] posLetters = { 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Z' };
		private int[]  posDegrees = { 0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 84 };

		private int arrayLength = 22;

		public LatZones() { }

		public int getLatZoneDegree(String letter) {
			char ltr = letter.charAt(0);
			for(int i = 0; i < arrayLength; i++) {
				if(letters[i] == ltr) {
					return degrees[i];
				}
			}
			return -100;
		}

		public String getLatZone(double latitude) {
			int latIndex = -2;
			int lat = (int) latitude;

			if(lat >= 0) {
				int len = posLetters.length;
				for(int i = 0; i < len; i++) {
					if(lat == posDegrees[i]) {
						latIndex = i;
						break;
					}

					if(lat > posDegrees[i]) {
						continue;
					} else {
						latIndex = i - 1;
						break;
					}
				}
			} else {
				int len = negLetters.length;
				for(int i = 0; i < len; i++) {
					if(lat == negDegrees[i]) {
						latIndex = i;
						break;
					}

					if(lat < negDegrees[i]) {
						latIndex = i - 1;
						break;
					} else {
						continue;
					}

				}

			}

			if(latIndex == -1) {
				latIndex = 0;
			}
			if(lat >= 0) {
				if(latIndex == -2) {
					latIndex = posLetters.length - 1;
				}
				return String.valueOf(posLetters[latIndex]);
			} else {
				if(latIndex == -2) {
					latIndex = negLetters.length - 1;
				}
				return String.valueOf(negLetters[latIndex]);

			}
		}

	}

	static class Polar2UTM {
		Ellipsoid ellipsoid = Ellipsoid.WGS84;

		// scale factor
		double k0 = 0.9996;

		double e1sq = ellipsoid.excentricity() * ellipsoid.excentricity() / (1 - ellipsoid.excentricity() * ellipsoid.excentricity());

		double n = (ellipsoid.equatorialRadius() - ellipsoid.polarRadius())
				/ (ellipsoid.equatorialRadius() + ellipsoid.polarRadius());

		// r curv 1
		double rho = 6368573.744;

		// r curv 2
		double nu = 6389236.914;

		// Calculate Meridional Arc Length
		// Meridional Arc
		double S = 5103266.421;

		double A0 = 6367449.146;

		double B0 = 16038.42955;

		double C0 = 16.83261333;

		double D0 = 0.021984404;

		double E0 = 0.000312705;

		// Calculation Constants
		// Delta Long
		double p = -0.483084;

		double sin1 = 4.84814E-06;

		// Coefficients for UTM Coordinates
		double K1 = 5101225.115;

		double K2 = 3750.291596;

		double K3 = 1.397608151;

		double K4 = 214839.3105;

		double K5 = -2.995382942;

		double A6 = -1.00541E-07;

		public UTMCoordinate convertLatLonToUTM(double longitude, double latitude) {
			validate(longitude, latitude);
//			String UTM = "";

			setVariables(latitude, longitude);

			String longZone = getLongZone(longitude);
			LatZones latZones = new LatZones();
			String latZone = latZones.getLatZone(latitude);

			double _easting = getEasting();
			double _northing = getNorthing(latitude);

			return new UTMCoordinate(_easting, _northing, longZone, latZone);
			
			
			
			/*
			UTM = longZone + " " + latZone + " " + ((int) _easting) + " "
					+ ((int) _northing);
			// UTM = longZone + " " + latZone + " " + decimalFormat.format(_easting) +
			// " "+ decimalFormat.format(_northing);

			return UTM;
			*/

		}

		protected void setVariables(double latitude, double longitude) {
			latitude *= Math.PI / 180.0f; // Degree -> Radian

			rho = ellipsoid.equatorialRadius() * (1 - ellipsoid.excentricity() * ellipsoid.excentricity()) / Math.pow(1 - Math.pow(ellipsoid.excentricity() * Math.sin(latitude), 2), 3 / 2.0);
			nu  = ellipsoid.equatorialRadius() / Math.pow(1 - Math.pow(ellipsoid.excentricity() * Math.sin(latitude), 2), (1 / 2.0));

			double var1 = (longitude < 0.0) ? ((int) ((180 + longitude) / 6.0)) + 1 : ((int) (longitude / 6)) + 31;
			double var2 = (6 * var1) - 183;
			double var3 = longitude - var2;

			p = var3 * 3600 / 10000;

			S = A0 * latitude - B0 * Math.sin(2 * latitude) + C0 * Math.sin(4 * latitude) - D0 * Math.sin(6 * latitude) + E0 * Math.sin(8 * latitude);

			K1 = S * k0;
			K2 = nu * Math.sin(latitude) * Math.cos(latitude) * Math.pow(sin1, 2) * k0 * (100000000) / 2;
			K3 = ((Math.pow(sin1, 4) * nu * Math.sin(latitude) * Math.pow(Math.cos(latitude), 3)) / 24)
					* (5 - Math.pow(Math.tan(latitude), 2) + 9 * e1sq * Math.pow(Math.cos(latitude), 2)
					+ 4 * Math.pow(e1sq, 2) * Math.pow(Math.cos(latitude), 4)) * k0 * (10000000000000000L);

			K4 = nu * Math.cos(latitude) * sin1 * k0 * 10000;

			K5 = Math.pow(sin1 * Math.cos(latitude), 3) * (nu / 6) * (1 - Math.pow(Math.tan(latitude), 2) 
					+ e1sq * Math.pow(Math.cos(latitude), 2)) * k0 * 1000000000000L;

			A6 = (Math.pow(p * sin1, 6) * nu * Math.sin(latitude) * Math.pow(Math.cos(latitude), 5) / 720) * (61 - 58 * Math.pow(Math.tan(latitude), 2) 
					+ Math.pow(Math.tan(latitude), 4) + 270 * e1sq * Math.pow(Math.cos(latitude), 2) 
					- 330 * e1sq * Math.pow(Math.sin(latitude), 2)) * k0 * (1E+24);
		}

		protected String getLongZone(double longitude) {
			double longZone = (longitude < 0.0) ? ((180.0 + longitude) / 6) + 1 : (longitude / 6) + 31;
			String val = String.valueOf((int) longZone);
			if(val.length() == 1)
				val = "0" + val;
			return val;
		}

		protected double getNorthing(double latitude) {
			double northing = K1 + K2 * p * p + K3 * Math.pow(p, 4);
			if(latitude < 0.0) {
				northing = 10000000 + northing;
			}
			return northing;
		}

		protected double getEasting() {
			return 500000 + (K4 * p + K5 * Math.pow(p, 3));
		}

	}

	static class UTM2Polar {
		double easting;

		double northing;

		int zone;

		String southernHemisphere = "ACDEFGHJKLM";

		protected String getHemisphere(String latZone) {
			String hemisphere = "N";
			if(southernHemisphere.indexOf(latZone) > -1) {
				hemisphere = "S";
			}
			return hemisphere;
		}

		public double[] convertUTMToLatLong(String UTM) {
			double[] latlon = { 0.0, 0.0 };
			String[] utm = UTM.split(" ");
			zone = Integer.parseInt(utm[0]);
			String latZone = utm[1];
			easting = Double.parseDouble(utm[2]);
			northing = Double.parseDouble(utm[3]);
			String hemisphere = getHemisphere(latZone);
			double latitude = 0.0;
			double longitude = 0.0;

			if(hemisphere.equals("S")) {
				northing = 10000000 - northing;
			}
			setVariables();
			latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;

			if(zone > 0) {
				zoneCM = 6 * zone - 183.0;
			} else {
				zoneCM = 3.0;

			}

			longitude = zoneCM - _a3;
			if(hemisphere.equals("S")) {
				latitude = -latitude;
			}

			latlon[0] = latitude;
			latlon[1] = longitude;
			return latlon;

		}

		protected void setVariables() {
			arc = northing / k0;
			mu = arc / (a * (1 - Math.pow(e, 2) / 4.0 - 3 * Math.pow(e, 4) / 64.0 - 5 * Math.pow(e, 6) / 256.0));

			ei = (1 - Math.pow((1 - e * e), (1 / 2.0))) / (1 + Math.pow((1 - e * e), (1 / 2.0)));

			ca = 3 * ei / 2 - 27 * Math.pow(ei, 3) / 32.0;

			cb = 21 * Math.pow(ei, 2) / 16 - 55 * Math.pow(ei, 4) / 32;
			cc = 151 * Math.pow(ei, 3) / 96;
			cd = 1097 * Math.pow(ei, 4) / 512;
			phi1 = mu + ca * Math.sin(2 * mu) + cb * Math.sin(4 * mu) + cc * Math.sin(6 * mu) + cd * Math.sin(8 * mu);

			n0 = a / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (1 / 2.0));

			r0 = a * (1 - e * e) / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (3 / 2.0));
			fact1 = n0 * Math.tan(phi1) / r0;

			_a1 = 500000 - easting;
			dd0 = _a1 / (n0 * k0);
			fact2 = dd0 * dd0 / 2;

			t0 = Math.pow(Math.tan(phi1), 2);
			Q0 = e1sq * Math.pow(Math.cos(phi1), 2);
			fact3 = (5 + 3 * t0 + 10 * Q0 - 4 * Q0 * Q0 - 9 * e1sq) * Math.pow(dd0, 4) / 24;

			fact4 = (61 + 90 * t0 + 298 * Q0 + 45 * t0 * t0 - 252 * e1sq - 3 * Q0 * Q0) * Math.pow(dd0, 6) / 720;

			//
			lof1 = _a1 / (n0 * k0);
			lof2 = (1 + 2 * t0 + Q0) * Math.pow(dd0, 3) / 6.0;
			lof3 = (5 - 2 * Q0 + 28 * t0 - 3 * Math.pow(Q0, 2) + 8 * e1sq + 24 * Math.pow(t0, 2)) * Math.pow(dd0, 5) / 120;
			_a2 = (lof1 - lof2 + lof3) / Math.cos(phi1);
			_a3 = _a2 * 180 / Math.PI;

		}

		double arc;

		double mu;

		double ei;

		double ca;

		double cb;

		double cc;

		double cd;

		double n0;

		double r0;

		double _a1;

		double dd0;

		double t0;

		double Q0;

		double lof1;

		double lof2;

		double lof3;

		double _a2;

		double phi1;

		double fact1;

		double fact2;

		double fact3;

		double fact4;

		double zoneCM;

		double _a3;

		double b = 6356752.314;

		double a = 6378137;

		double e = 0.081819191;

		double e1sq = 0.006739497;

		double k0 = 0.9996;

	}

}
