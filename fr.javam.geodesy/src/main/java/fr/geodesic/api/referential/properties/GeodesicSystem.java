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
package fr.geodesic.api.referential.properties;

public enum GeodesicSystem {
	WGS84		("World Geodesic System 1984", 					Ellipsoid.WGS84),
	BD72		("Belgian Datum 72", 							Ellipsoid.Inter1924),
	ETRS89		("European Terrestrial Reference System 1989", 	Ellipsoid.GRS80),
	ED50		("European Datum 1950", 						Ellipsoid.Hayford1909),
	NTF			("Nouvelle Triangulation Francaise", 			Ellipsoid.Clarke_1880),
	RGF93		("Réseau Géodésique Français 1993", 			Ellipsoid.IAG_GRS80);

	private String    label;
	private Ellipsoid ellipsoid;
	
	private GeodesicSystem(String _label, Ellipsoid _ellipsoid) {
		label     = _label;
		ellipsoid = _ellipsoid;
	}

	public String 		description() {
		return label;
	}

	public Ellipsoid 	ellipsoid() {
		return ellipsoid;
	}

}
