module javafr.geodetic {
	requires transitive javafr;
	requires javafr.sdk;

	exports fr.geodesic;
	exports fr.geodesic.api;
	exports fr.geodesic.api.exceptions;

	exports fr.geodesic.api.referential;
	exports fr.geodesic.api.referential.countries;
	exports fr.geodesic.api.referential.properties;

	exports fr.geodesic.utils;
	exports fr.geodesic.utils.converters;
	exports fr.geodesic.utils.mercator;

}
