module javafr.gms.sdk {
	requires javafx.graphics;
	requires javafx.base;
	
	requires transitive javafr;
	requires transitive javafr.geodetic;
	requires transitive javafr.gms;

	requires javafr.sdk;
	requires javafr.gis.graphics;
	requires javafr.gis.graphics2d;
	requires javafr.gis.sdk;

	exports fr.gms.hmi;
	exports fr.gms.hmi.ehorizon;
	exports fr.gms.hmi.ego;

	exports fr.gms.sdk.navigation.waypath;
	exports fr.gms.sdk.graphics.drawers;
	exports fr.gms.sdk.graphics.plugins;
	exports fr.gms.sdk.graphics.tests;
	exports fr.gms.sdk.utils;

	exports fr.gms.planner;
	exports fr.gms.simulator;

	exports fr.gms.planner.agent;
	exports fr.gms.planner.agent.ant;
	exports fr.gms.planner.utils.hmi;
}