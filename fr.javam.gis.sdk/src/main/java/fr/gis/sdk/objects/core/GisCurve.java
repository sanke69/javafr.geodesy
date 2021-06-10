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

import java.util.Arrays;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisObject;
import fr.gis.utils.GisUtils;
import fr.java.lang.properties.ID;

public class GisCurve extends GisObject implements Gis.Curve {
	protected List<GeoCoordinate> geometry;
	protected double length = -1;

    public GisCurve(ID _id, GeoCoordinate... _geometry) {
    	super(_id);
    	geometry = Arrays.asList(_geometry);
    }
    public GisCurve(ID _id, List<GeoCoordinate> _geometry) {
    	super(_id);
    	geometry = _geometry;
    }
    public GisCurve(GisCurve _area) {
    	this(_area.id, _area.geometry);
    }

    public double getLength() {
    	if(length < 0)
    		length = GisUtils.computeLength( this );
    	return length;
    }
    
	public List<GeoCoordinate> getTops() {
		return geometry;
	}
	public void setTops(List<GeoCoordinate> _geom) {
		geometry = _geom;
	}

	public String toString() {
		return "Segment: id=" + id + " " + geometry;
	}

}
