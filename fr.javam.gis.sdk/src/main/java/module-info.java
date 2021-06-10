module javafr.gis.sdk {
	requires transitive javafr;
	requires javafr.sdk;

	requires transitive javafr.geodetic;
	requires transitive javafr.gis;

	exports fr.gis.mercator;

	exports fr.gis.sdk;
	exports fr.gis.sdk.layers;
	exports fr.gis.sdk.layers.base;
	exports fr.gis.sdk.layers.base.tilesystem;

	exports fr.gis.sdk.objects;
	exports fr.gis.sdk.objects.core;
	exports fr.gis.sdk.objects.gis;
	exports fr.gis.sdk.objects.road;
	exports fr.gis.sdk.objects.weather;

	exports fr.gis.utils;
	exports fr.gis.utils.road;
	exports fr.gis.utils.tools;
	exports fr.gis.utils.tools.road;
	exports fr.gis.utils.tools.road.mapmatching;
	exports fr.gis.utils.tools.road.shortpath.astar;
	exports fr.gis.utils.tools.road.shortpath.dijkstra;
	exports fr.gis.utils.tools.road.topology;

	uses fr.gis.api.GisProvider;

}
