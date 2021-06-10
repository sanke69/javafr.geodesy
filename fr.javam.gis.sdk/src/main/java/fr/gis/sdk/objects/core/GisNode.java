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

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisObject;
import fr.java.lang.properties.ID;

public class GisNode extends GisObject implements Gis.Node {
	protected GeoCoordinate coordinate;

    public GisNode(ID _id, double _longitude, double _latitude) {
    	super(_id);
    	id    		= _id;
    	coordinate 	= GeoCoordinates.newWGS84(_longitude, _latitude);
    }
    public GisNode(ID _id, GeoCoordinate _coord) {
    	super(_id);
    	id    		= _id;
    	coordinate 	= _coord;
    }

	public void 			setPosition(GeoCoordinate _coords) {
		coordinate = _coords;
	}
	public GeoCoordinate 	getPosition() {
		return coordinate;
	}

}
