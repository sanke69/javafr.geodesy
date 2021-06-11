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

import java.util.concurrent.atomic.AtomicInteger;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;

public class GeoConverterMGRS extends GeoConverterUTM implements GeoCoordinate.Converter {

	public GeoCoordinate.Cartesian2D fromLatLong(GeoCoordinate _wgs84) {
		Polar2MGRS c = new Polar2MGRS();
		return c.convertLatLonToMGRUTM(_wgs84);
	}

	public GeoCoordinate.Spheric2D toLatLong(GeoCoordinate _mgrs) {
		MGRS2Polar c = new MGRS2Polar();
		return c.convertMGRUTMToLatLong(_mgrs);
	}

	private class MGRS2Polar extends GeoConverterUTM.UTM2Polar {

		public GeoCoordinate.Spheric2D convertMGRUTMToLatLong(GeoCoordinate _coord) {
			String mgrutm = _coord.toString();

			double[] latlon = { 0.0, 0.0 };
			// 02CNR0634657742
			int zone = Integer.parseInt(mgrutm.substring(0, 2));
			String latZone = mgrutm.substring(2, 3);

			String digraph1 = mgrutm.substring(3, 4);
			String digraph2 = mgrutm.substring(4, 5);
			easting = Double.parseDouble(mgrutm.substring(5, 10));
			northing = Double.parseDouble(mgrutm.substring(10, 15));

			GeoConverterUTM.LatZones lz = new GeoConverterUTM.LatZones();
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
			return GeoCoordinates.newWGS84(longitude, latitude);
		}
	}

	private class Polar2MGRS extends GeoConverterUTM.Polar2UTM {
		public GeoCoordinate.Cartesian2D convertLatLonToMGRUTM(GeoCoordinate _wgs84) {
			assert(_wgs84.getDatum() == Datum.WGS84);
			GeoCoordinate.Spheric2D wgs84 = (GeoCoordinate.Spheric2D) _wgs84;

			GeoConverterUTM.validate(wgs84.getLatitude(), wgs84.getLongitude());

			setVariables(wgs84.getLatitude(), wgs84.getLongitude());

//			String longZone = getLongZone(wgs84.getLongitude());
//			UTMConverter.LatZones latZones = new UTMConverter.LatZones();
//			String latZone = latZones.getLatZone(wgs84.getLatitude());

			double _easting = getEasting();
			double _northing = getNorthing(wgs84.getLatitude());
//			Digraphs digraphs = new Digraphs();
//			String digraph1 = digraphs.getDigraph1(Integer.parseInt(longZone), _easting);
//			String digraph2 = digraphs.getDigraph2(Integer.parseInt(longZone), _northing);

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

//			String mgrUTM = longZone + latZone + digraph1 + digraph2 + easting + northing;

			return GeoCoordinates.newMGRS(0, "", 0, "");
		}
	}

	private class Digraphs {
//		private Map<Integer, String>	digraph1;
//		private Map<Integer, String>	digraph2;

		private String[]	digraph1Array	= { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		private String[]	digraph2Array	= { "V", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V" };

		public Digraphs() {
			AtomicInteger index = new AtomicInteger();

			index.set(0);
/*
			digraph1 = Arrays.asList(digraph1Array).stream().collect(
					HashMap<Integer, String>::new, (map, str) -> map.put(index.getAndIncrement(), str), (i, u) -> {
					});

			index.set(0);
			digraph2 = Arrays.asList(digraph2Array).stream().collect(
					HashMap<Integer, String>::new, (map, str) -> map.put(index.getAndIncrement(), str), (i, u) -> {
					});
*/
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
/*
		public String getDigraph1(int longZone, double easting) {
			int a1 = longZone;
			double a2 = 8 * ((a1 - 1) % 3) + 1;

			double a3 = easting;
			double a4 = a2 + ((int) (a3 / 100000)) - 1;
			return (String) digraph1.get( (int) Math.floor(a4) );
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
			return (String) digraph2.get( (int) Math.floor(a4) );

		}
*/
	}

}
