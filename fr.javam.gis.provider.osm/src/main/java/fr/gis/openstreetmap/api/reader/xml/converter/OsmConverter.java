package fr.gis.openstreetmap.api.reader.xml.converter;

import java.util.List;
import java.util.Map;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmNode;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmWay;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmTag;
import fr.gis.sdk.objects.gis.GisBuilding;
import fr.gis.sdk.objects.road.RoadElement;
import fr.java.lang.properties.ID;

public class OsmConverter {
	
	public static enum Nature {
		ROAD_ELEMENT, RAIL_ELEMENT, ENVIRONMENT_ELEMENT, BUILDING, UNKNOWN;
	}

	public static Nature getNature(OsmWay _item) {
		if(isRoad(_item))
			return Nature.ROAD_ELEMENT;

		if(isBuilding(_item))
			return Nature.BUILDING;

		if(isArea(_item)) {
			return Nature.ENVIRONMENT_ELEMENT;
		}
		
		return Nature.UNKNOWN;
	}
	
	public static RoadElement asRoadElement(OsmWay _item, Map<ID, OsmNode> _osmNodes) {
		assert( isRoad(_item) );
		
		return new RoadElement.Builder(_item.getId())
									.setGeometry( OsmProperties.getGeometry(_item, _osmNodes) )
									.setDirection( OsmProperties.getDrivingWay(_item) )
									.build();
	}
	public static GisBuilding asBuildingElement(OsmWay _item, Map<ID, OsmNode> _osmNodes) {
		assert( isBuilding(_item) );

		return new GisBuilding.Builder(_item.getId())
									.setGeometry( OsmProperties.getGeometry(_item, _osmNodes) )
									.setElevation( OsmProperties.getElevation(_item) )
									.build();
	}

	
	
	/**
	 * NODES
	 */
	public static boolean isShop(OsmItem _item) {
		String
		tagValue = _item.getTag("shop");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "variety_store", "supermarket", "butcher", "clothes", "bakery" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}
		return false;
	}
	
	/**
	 * SEGMENTS
	 */
	public static boolean isRoad(OsmItem _item) {
		String
		tagValue = _item.getTag("highway");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "primary", "secondary", "tertiary", "living_street", "residential", "service", "unclassified" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		tagValue = _item.getTag("denotation");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "avenue" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		tagValue = _item.getTag("maxspeed");
		if(tagValue != null)
			return true;

		return false;
	}
	public static boolean isBicycleWay(OsmItem _item) {
		String
		tagValue = _item.getTag("bicycle");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "designated" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		return false;
	}
	public static boolean isPedestrianWay(OsmItem _item) {
		String
		tagValue = _item.getTag("bicycle");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "designated" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		return false;
	}
	public static boolean isRail(OsmItem _item) {
		String
		tagValue = _item.getTag("railway");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "rail", "platform" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		tagValue = _item.getTag("type:RATP");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "rer" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		tagValue = _item.getTag("ref:FR:RATP");
		if(tagValue != null) {
			return true;
		}

		tagValue = _item.getTag("ref:SNCF:RER");
		if(tagValue != null) {
			return true;
		}

		return false;
	}

	public static boolean isElectricWay(OsmItem _item) {
		return false;
	}
	public static boolean isWaterWay(OsmItem _item) {
		return false;
	}
	public static boolean isOilWay(OsmItem _item) {
		return false;
	}
	
	public static boolean isWall(OsmItem _item) {
		String
		tagValue = _item.getTag("bicycle");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "designated" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		return false;
	}

	/**
	 * AREA
	 */
	public static boolean isArea(OsmItem _item) {
		String
		tagValue = _item.getTag("area");
		if(tagValue != null)
			return tagValue.compareToIgnoreCase("yes") == 0;

		if(OsmWay.class.isAssignableFrom(_item.getClass())) {
			OsmWay asWay = (OsmWay) _item;
			if(asWay.getNodeRefs().get(0) == asWay.getNodeRefs().get(asWay.getNodeRefs().size() - 1)) {
				if(_item.getTag("junction") != null || _item.getTag("highway") != null)
					return false;
				return true;
			}
		}

		return false;
	}
	public static boolean isPedestrianArea(OsmItem _item) {
		String
		tagValue = _item.getTag("highway");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "pedestrian" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		return false;
	}
	 
	public static boolean isBuilding(OsmItem _item) {
		String
		tagValue = _item.getTag("building");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "yes", "commercial", "retail" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0) {
					if(OsmWay.class.isAssignableFrom(_item.getClass())) {
						OsmWay asWay = (OsmWay) _item;
						return asWay.getNodeRefs().size() > 2;
					}
				}
		}

		tagValue = _item.getTag("building");
		if(tagValue != null) {
			String[] possibleValues = new String[] { "yes", "commercial", "retail" };
			for(String value : possibleValues)
				if(tagValue.compareToIgnoreCase(value) == 0)
					return true;
		}

		tagValue = _item.getTag("building:levels");
		if(tagValue != null)
			return true;

		return false;
	}

	public static boolean isNatural(List<OsmTag> _tags) {
		for(OsmTag tag : _tags) {
			switch(tag.getKey()) {
			case "natural" : return true;
			}
		}
		
		return false;
	}

}
