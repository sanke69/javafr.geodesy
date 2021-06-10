/**
 * Copyright (C) 2007-?
 * 
 * @author   <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>
 *
 * @section license License
 *    [EN] This file is the intellectual property of Steve PECHBERTI.
 *         Any use, partial or complete copy, modification of the file
 *         without my approval is forbidden
 *    [FR] Ce fichier est la propriete intellectuelle de Steve PECHBERTI.
 *         Toute utilisation, copie partielle ou totale, modification
 *         du fichier sans mon autorisation est interdite
 *
 * @section disclaimer Disclaimer
 *    [EN] This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.
 *
 */
package fr.gis.sdk.objects.core;

import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisObject;
import fr.java.lang.properties.ID;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;

public class GisArea extends GisObject implements Gis.Area {
	protected List<GeoCoordinate> 	geometry;
	protected BoundingBox.TwoDims	boundaries;

	public GisArea(ID _id, List<GeoCoordinate> _geometry) {
		super(_id);
		geometry = _geometry;
		boundaries = new SimpleRectangle2D(getGroundGeometry(_geometry));
	}
	public GisArea(GisArea _area) {
		this(_area.id, _area.geometry);
	}

	public List<GeoCoordinate> getOutline() {
		return geometry;
	}
	public void setGeometry(List<GeoCoordinate> _geom) {
		geometry = _geom;
	}

	public BoundingBox.TwoDims getBounds() {
		return boundaries;
	}

	private static Point2D[] getGroundGeometry(List<GeoCoordinate> _geometry) {
		Point2D[] planeGeom = new Point2D[_geometry.size()];
		
		int i = 0;
		for(GeoCoordinate c : _geometry)
			planeGeom[i++] = Points.of(c.asWGS84().getLongitude(), c.asWGS84().getLatitude());
		
		return planeGeom;
	}

}
