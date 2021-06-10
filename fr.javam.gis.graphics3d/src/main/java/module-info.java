module javafr.gis.graphics3d {
	requires java.desktop;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;

	requires transitive javafr.sdk;
	requires transitive javafr.graphics;

	requires transitive javafr.geodetic;
	requires transitive javafr.gis.graphics;

	requires transitive javafr.threedjinns;
	requires transitive javafr.threedjinns.graphics;
	requires transitive javafr.threedjinns.opengl;
	requires javafr.gis.sdk;

	exports fr.gis.graphics.control3d;
	exports fr.gis.viewer3d;
	exports fr.gis.viewer3d.shapes;

}