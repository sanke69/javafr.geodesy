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
package fr.geodesic.api.mercator;

import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tiled.TileViewport;
import fr.utils.geodesic.BoundingBoxes;

public interface Mercator {
	public static final BoundingBox.TwoDims	GeoSphere = BoundingBoxes.fromBounds(-180.0, -85.051129, 180.0, 85.051129);

	public static interface Projector extends fr.java.math.geometry.Projector.TwoDims.Levelable<GeoCoordinate.Spheric2D, Coordinate.TwoDims> {
		public static final Function<Double, Double> gudermann = lat -> 2.0 * Math.atan(Math.exp(lat)) - Math.PI / 2.0;
	}

	public interface Viewport extends TileViewport.Editable<BoundingBox.TwoDims, GeoCoordinate.Spheric2D> {
	}

}
