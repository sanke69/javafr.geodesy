module javafr.gis.google {
	requires javafr.sdk;
	requires javafr.gis;
	requires javafr.gis.sdk;

	provides fr.gis.api.GisProvider with fr.gis.google.MapProviderGoogle;

}
