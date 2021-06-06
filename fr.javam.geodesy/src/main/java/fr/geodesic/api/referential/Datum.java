package fr.geodesic.api.referential;

import fr.geodesic.api.referential.properties.Ellipsoid;
import fr.java.math.topology.CoordinateSystem;

public enum Datum {
	WGS84			("World Geodesic System 1984", 					CoordinateSystem.Spheric3D,		Ellipsoid.IAG_GRS80),
	WEB_MERCATOR	("Web Mercator", 								CoordinateSystem.Cartesian2D,	Ellipsoid.IAG_GRS80),
	UTM				("Universal Transverse Mercator", 				CoordinateSystem.Cartesian2D,	Ellipsoid.IAG_GRS80),
	MGRS			("Military Grid Reference System", 				CoordinateSystem.Cartesian2D,	Ellipsoid.IAG_GRS80),
	RGF93			("Réseau Géodésique Français 1993", 			CoordinateSystem.Cartesian2D,	Ellipsoid.IAG_GRS80),
	NTF				("Nouvelle Triangulation Francaise", 			CoordinateSystem.Cartesian2D,	Ellipsoid.Clarke_1880),
	ED50			("European Datum 1950", 						CoordinateSystem.Cartesian2D,	Ellipsoid.Hayford1909),
	ETRS89			("European Terrestrial Reference System 1989", 	CoordinateSystem.Cartesian2D,	Ellipsoid.GRS80),
	BD72			("Belgian Datum 72", 							null,							Ellipsoid.Inter1924),
	Lambert93		("Projection Lambert93",						CoordinateSystem.Cartesian2D,	Ellipsoid.Clarke_1880),	// TODO:: Check Value!!!
//	NAD83			("North American Datum 1983", null, null),
//	NAD27			("North American Datum 1927", null, null),
	;

	private String    			description;
	private CoordinateSystem 	system;			// Mandated
	private Ellipsoid 			ellipsoid;		// Mandated

	private Datum(String _description, CoordinateSystem _system, Ellipsoid _ellipsoid) {
		description = _description;
		system      = _system;
		ellipsoid   = _ellipsoid;
	}

	public String				getDescription() 		{ return description; }
	public CoordinateSystem     getCoordinateSystem() 	{ return system; }
	public Ellipsoid     		getEllipsoid() 			{ return ellipsoid; }

}
