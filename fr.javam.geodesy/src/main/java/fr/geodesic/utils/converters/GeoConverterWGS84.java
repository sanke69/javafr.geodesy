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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.api.referential.properties.Ellipsoid;
import fr.geodesic.utils.GeoCoordinates;

public class GeoConverterWGS84 {
	
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

	public static void main(String[] args) {
		double[][] polarWGS84 = new double[][] {
			{    0.0000,     0.0000},
			{    0.1300, -   0.2324},
			{ - 45.6456,    23.3545},
			{ - 12.7650, -  33.8765},
			{ - 80.5434, - 170.6540},
			{   90.0000,   177.0000},
			{ - 90.0000, - 177.0000},
			{   90.0000,     3.0000},
			{   23.4578, - 135.4545},
			{   77.3450,   156.9876},
			{ - 89.3454, -  48.9306}
		};
		String[] utmWGS84 = new String[] {
			"31 N 166021 0",
			"30 N 808084 14385",
			"34 G 683473 4942631",
			"25 L 404859 8588690",
			"02 C 506346 1057742",
			"60 Z 500000 9997964",
			"01 A 500000 2035",
			"31 Z 500000 9997964",
			"08 Q 453580 2594272",
			"57 X 450793 8586116",
			"22 A 502639 75072"
		};
		
		GeoConverterWGS84 converter = new GeoConverterWGS84();
		
		int i = 0;
		for(double[] coord : polarWGS84) {
			GeoCoordinate utm = converter.polar2utm(coord[0], coord[1]);
			System.out.println(utm + "\t" + utmWGS84[i] + "\t" + (utmWGS84[i++].compareToIgnoreCase(utm.toString()) == 0 ? "OK" : "NOK"));
			
		}
		
		for(String utm : utmWGS84) {
			double[] polar = converter.utm2polar(utm);
			System.out.println(polar[0] + "  " + polar[1]);
		}

	}

	public GeoConverterWGS84() {

	}

	private void validate(double latitude, double longitude) {
		if(latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude >= 180.0)
			throw new IllegalArgumentException("Legal ranges: latitude [-90,90], longitude [-180,180).");
	}

	public GeoCoordinate polar2utm(GeoCoordinate _wgs84) {
		if(_wgs84.getDatum() != Datum.WGS84)
			throw new RuntimeException();
		
		GeoCoordinate.Spheric2D wgs84 = (GeoCoordinate.Spheric2D) _wgs84;

		Polar2UTM c = new Polar2UTM();
		UTMCoordinate utm = c.convertLatLonToUTM(wgs84.getLongitude(), wgs84.getLatitude());

		return GeoCoordinates.newUTM(utm.X, utm.zoneLg, utm.Y, utm.zoneLt);
	}
	public GeoCoordinate polar2utm(double latitude, double longitude) {
		Polar2UTM c = new Polar2UTM();
		UTMCoordinate utm = c.convertLatLonToUTM(latitude, longitude);

		return GeoCoordinates.newUTM(utm.X, utm.zoneLg, utm.Y, utm.zoneLt);
	}

	public GeoCoordinate utm2polar(GeoCoordinate _utm) {
		if(_utm.getDatum() != Datum.UTM)
			throw new RuntimeException();
		
		UTM2Polar c = new UTM2Polar();
		double[] coords = c.convertUTMToLatLong(_utm.toString());

		return GeoCoordinates.newWGS84(coords[0], coords[1]);
	}
	public double[]   utm2polar(String UTM) {
		UTM2Polar c = new UTM2Polar();
		return c.convertUTMToLatLong(UTM);
	}

	public String     polar2mgrs(double latitude, double longitude) {
		Polar2MGRS c = new Polar2MGRS();
		return c.convertLatLonToMGRUTM(latitude, longitude);

	}

	public double[]   mgrs2polar(String MGRUTM) {
		MGRS2Polar c = new MGRS2Polar();
		return c.convertMGRUTMToLatLong(MGRUTM);
	}


	private class Polar2UTM {
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

		public UTMCoordinate convertLatLonToUTM(double latitude, double longitude) {
			validate(latitude, longitude);
			String UTM = "";

			setVariables(latitude, longitude);

			String longZone = getLongZone(longitude);
			LatZones latZones = new LatZones();
			String latZone = latZones.getLatZone(latitude);

			double _easting = getEasting();
			double _northing = getNorthing(latitude);

			return new UTMCoordinate((int) _easting, (int) _northing, longZone, latZone);
			
			
			
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

	private class Polar2MGRS extends Polar2UTM {
		public String convertLatLonToMGRUTM(double latitude, double longitude) {
			validate(latitude, longitude);
			String mgrUTM = "";

			setVariables(latitude, longitude);

			String longZone = getLongZone(longitude);
			LatZones latZones = new LatZones();
			String latZone = latZones.getLatZone(latitude);

			double _easting = getEasting();
			double _northing = getNorthing(latitude);
			Digraphs digraphs = new Digraphs();
			String digraph1 = digraphs.getDigraph1(Integer.parseInt(longZone),
					_easting);
			String digraph2 = digraphs.getDigraph2(Integer.parseInt(longZone),
					_northing);

			String easting = String.valueOf((int) _easting);
			if(easting.length() < 5) {
				easting = "00000" + easting;
			}
			easting = easting.substring(easting.length() - 5);

			String northing;
			northing = String.valueOf((int) _northing);
			if(northing.length() < 5) {
				northing = "0000" + northing;
			}
			northing = northing.substring(northing.length() - 5);

			mgrUTM = longZone + latZone + digraph1 + digraph2 + easting + northing;
			return mgrUTM;
		}
	}

	private class UTM2Polar {
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

	private class MGRS2Polar extends UTM2Polar {
		public double[] convertMGRUTMToLatLong(String mgrutm) {
			double[] latlon = { 0.0, 0.0 };
			// 02CNR0634657742
			int zone = Integer.parseInt(mgrutm.substring(0, 2));
			String latZone = mgrutm.substring(2, 3);

			String digraph1 = mgrutm.substring(3, 4);
			String digraph2 = mgrutm.substring(4, 5);
			easting = Double.parseDouble(mgrutm.substring(5, 10));
			northing = Double.parseDouble(mgrutm.substring(10, 15));

			LatZones lz = new LatZones();
			double latZoneDegree = lz.getLatZoneDegree(latZone);

			double a1 = latZoneDegree * 40000000 / 360.0;
			double a2 = 2000000 * Math.floor(a1 / 2000000.0);

			Digraphs digraphs = new Digraphs();

			double digraph2Index = digraphs.getDigraph2Index(digraph2);

			double startindexEquator = 1;
			if((1 + zone % 2) == 1) {
				startindexEquator = 6;
			}

			double a3 = a2 + (digraph2Index - startindexEquator) * 100000;
			if(a3 <= 0) {
				a3 = 10000000 + a3;
			}
			northing = a3 + northing;

			zoneCM = -183 + 6 * zone;
			double digraph1Index = digraphs.getDigraph1Index(digraph1);
			int a5 = 1 + zone % 3;
			double[] a6 = { 16, 0, 8 };
			double a7 = 100000 * (digraph1Index - a6[a5 - 1]);
			easting = easting + a7;

			setVariables();

			double latitude = 0;
			latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;

			if(latZoneDegree < 0) {
				latitude = 90 - latitude;
			}

			double d = _a2 * 180 / Math.PI;
			double longitude = zoneCM - d;

			if(getHemisphere(latZone).equals("S")) {
				latitude = -latitude;
			}

			latlon[0] = latitude;
			latlon[1] = longitude;
			return latlon;
		}
	}

	private class Digraphs {
		private Map<Integer, String> digraph1;
		private Map<Integer, String> digraph2;

		private String[] digraph1Array = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		private String[] digraph2Array = { "V", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V" };

		public Digraphs() {
			AtomicInteger index = new AtomicInteger();

			index.set(0);
			digraph1 = Arrays.asList(digraph1Array).stream().collect(
							HashMap<Integer, String>::new, (map, str) -> map.put(index.getAndIncrement(), str), (i, u) -> {});

			index.set(0);
			digraph2 = Arrays.asList(digraph2Array).stream().collect(
							HashMap<Integer, String>::new, (map, str) -> map.put(index.getAndIncrement(), str), (i, u) -> {});
		}

		public int getDigraph1Index(String letter) {
			for(int i = 0; i < digraph1Array.length; i++)
				if(digraph1Array[i].equals(letter))
					return i + 1;
			return -1;
		}

		public int getDigraph2Index(String letter) {
			for(int i = 0; i < digraph2Array.length; i++)
				if(digraph2Array[i].equals(letter))
					return i;
			return -1;
		}

		public String getDigraph1(int longZone, double easting) {
			int a1 = longZone;
			double a2 = 8 * ((a1 - 1) % 3) + 1;

			double a3 = easting;
			double a4 = a2 + ((int) (a3 / 100000)) - 1;
			return (String) digraph1.get(new Integer((int) Math.floor(a4)));
		}

		public String getDigraph2(int longZone, double northing) {
			int a1 = longZone;
			double a2 = 1 + 5 * ((a1 - 1) % 2);
			double a3 = northing;
			double a4 = (a2 + ((int) (a3 / 100000)));
			a4 = (a2 + ((int) (a3 / 100000.0))) % 20;
			a4 = Math.floor(a4);
			if(a4 < 0)
				a4 = a4 + 19;
			return (String) digraph2.get(new Integer((int) Math.floor(a4)));

		}

	}

	private class LatZones {
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

}