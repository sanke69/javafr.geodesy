module javafr.gis.graphics2d {
	requires java.desktop;
	
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;

	requires javafr;
	requires javafr.sdk;
	requires javafr.graphics;

	requires transitive javafr.geodetic;
	requires transitive javafr.gis.graphics;
	requires javafr.gis.sdk;

	exports fr.gis.viewer2d;
	exports fr.gis.viewer2d.api;
	exports fr.gis.viewer2d.renderer;

	exports fr.gis.viewer2d.renderer.objects;
	exports fr.gis.viewer2d.renderer.objects.gis;
	exports fr.gis.viewer2d.renderer.objects.road;

	exports fr.gis.viewer2d.renderer.layers;
	exports fr.gis.viewer2d.renderer.layers.base;
	exports fr.gis.viewer2d.renderer.layers.gis;
	exports fr.gis.viewer2d.renderer.layers.road;

	exports fr.gis.region2d;

}