package fr.geodesic.api.referential.properties;

public enum GeodesicSystem {
	WGS84		("World Geodesic System 1984", 					Ellipsoid.WGS84),
	BD72		("Belgian Datum 72", 							Ellipsoid.Inter1924),
	ETRS89		("European Terrestrial Reference System 1989", 	Ellipsoid.GRS80),
	ED50		("European Datum 1950", 						Ellipsoid.Hayford1909),
	NTF			("Nouvelle Triangulation Francaise", 			Ellipsoid.Clarke_1880),
	RGF93		("Réseau Géodésique Français 1993", 			Ellipsoid.IAG_GRS80);

	private String    label;
	private Ellipsoid ellipsoid;
	
	private GeodesicSystem(String _label, Ellipsoid _ellipsoid) {
		label     = _label;
		ellipsoid = _ellipsoid;
	}

	public String 		description() {
		return label;
	}

	public Ellipsoid 	ellipsoid() {
		return ellipsoid;
	}

}
