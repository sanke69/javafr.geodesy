module javafr.gis.arcgis {
	requires javafr.sdk;
	requires javafr.gis;
	requires javafr.gis.sdk;

	provides fr.gis.api.GisProvider with fr.gis.arcgis.MapProviderArcGIS;

}