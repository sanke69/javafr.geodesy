module javafr.gis.osm {
	requires javafr.sdk;
	requires javafr.gis;
	requires javafr.gis.sdk;

	provides fr.gis.api.GisProvider with fr.gis.openstreetmap.MapProviderOpenStreetMap;

	exports fr.gis.openstreetmap.utils;

}