module javafr.gis.graphics {
	requires java.desktop;
	requires javafx.base;
	requires transitive javafx.controls;
	requires javafx.graphics;

	requires javafr;
	requires javafr.sdk;
	requires transitive javafr.graphics;
	requires transitive javafr.geodetic;
	requires transitive javafr.gis;

	exports fr.gis.graphics.api.render;
	exports fr.gis.graphics.api.render.items;
	exports fr.gis.graphics.api.render.options;
	exports fr.gis.graphics.api.control;
	exports fr.gis.graphics.api.control.items;

	exports fr.gis.graphics.core;
	exports fr.gis.graphics.core.controller;
	exports fr.gis.graphics.core.hud;

	exports fr.gis.graphics.sdk.control;
	exports fr.gis.graphics.utils;

	// Must be adapted ...
	exports eu.hansolo.fx.world;

	// Deprecated
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.iconli.core;
	requires org.kordamp.ikonli.materialdesign;

}
