module javafr.gis.mappy {
	requires javafr.sdk;
	requires javafr.gis;
	requires javafr.gis.sdk;

	provides fr.gis.api.GisProvider with fr.gis.mappy.MapProviderMappy;

}