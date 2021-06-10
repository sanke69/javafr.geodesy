module javafr.gms {
	requires transitive javafr;
	requires javafr.sdk;

	requires transitive javafr.geodetic;
	requires transitive javafr.gis;
	requires javafr.gis.sdk;

	exports fr.gms.api;
	exports fr.gms.api.ego;
	exports fr.gms.api.sensor;
	exports fr.gms.api.service;
	exports fr.gms.map.objects.ego;
	exports fr.gms.map.objects.sensor;
	exports fr.gms.map.utils.path;

	exports fr.gms.navigation.waypath;

	exports fr.gms.navigation.gnss.protocols.nmea;
	exports fr.gms.navigation.gnss.protocols.exceptions;
	exports fr.gms.navigation.gnss.protocols.nmea.trames.v3;

	exports fr.gms.utils;

}
