module javafr.gis {
	requires transitive javafr;
	requires transitive javafr.geodetic;

	exports fr.gis.api.data.local;

	exports fr.gis.api;
	exports fr.gis.api.tools;

	exports fr.gis.api.road;
	exports fr.gis.api.road.tools;
	exports fr.gis.api.road.tools.engines;

//	uses fr.gis.api.GisProvider;

}
