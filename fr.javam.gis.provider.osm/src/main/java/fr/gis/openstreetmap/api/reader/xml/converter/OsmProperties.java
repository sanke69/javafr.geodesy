package fr.gis.openstreetmap.api.reader.xml.converter;

import java.util.ArrayList;
import java.util.Map;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmNode;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmWay;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmNodeRef;
import fr.java.lang.properties.ID;

public class OsmProperties {

	public static ArrayList<GeoCoordinate> getGeometry(OsmWay _way, Map<ID, OsmNode> _nodes) {
		ArrayList<GeoCoordinate> coords = new ArrayList<GeoCoordinate>();

		for(OsmNodeRef nodeRef : _way.getNodeRefs()) {
			OsmNode       node    = _nodes.get(nodeRef.getRef());
			GeoCoordinate newNode = GeoCoordinates.newWGS84(node.getLongitude(), node.getLatitude());

			coords.add(newNode);
		}

		return coords;
	}
	
	public static String getName(OsmItem _item) {
		String
		tagValue = _item.getTag("name");
		
		if(tagValue != null)
			return tagValue;
		
		return null;
	}
	
	public static String getAddress(OsmItem _item) {
		StringBuilder sb = new StringBuilder();

		String
		tagValue = _item.getTag("addr:housenumber");
		if(tagValue != null)
			sb.append(tagValue + ", ");

		tagValue = _item.getTag("addr:street");
		if(tagValue != null)
			sb.append(tagValue + " - ");

		tagValue = _item.getTag("addr:postcode");
		if(tagValue != null)
			sb.append(tagValue + " - ");

		tagValue = _item.getTag("addr:city");
		if(tagValue != null)
			sb.append(tagValue + " - ");

		return sb.toString();
		
	}
	public static float  getElevation(OsmWay _item) {
		String
		tagValue = _item.getTag("building:levels");
		if(tagValue != null)
			return Float.parseFloat(tagValue) * 2.5f;

		return -1.0f;
	}

	// Assume that OsmItem represents a RoadElement !!!
	public static Gis.Direction getDrivingWay(OsmItem _item) {
		String tagValue = _item.getTag("oneway");
		
		if(tagValue == null)
			return Gis.Direction.BOTH;
					
		return tagValue.compareToIgnoreCase("yes") == 0 ? Gis.Direction.DIRECT : Gis.Direction.BOTH;
	}
	public static int getMandatorySpeedLimit(OsmItem _item) {
		String
		tagValue = _item.getTag("maxspeed");
		if(tagValue != null)
			return Integer.parseInt(tagValue);

		return -1;
	}


}
