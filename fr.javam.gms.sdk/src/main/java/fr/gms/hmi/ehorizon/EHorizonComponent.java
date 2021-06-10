package fr.gms.hmi.ehorizon;

public enum EHorizonComponent {
	CENTER					("Center"),
	RADIUS					("Radius"),

	// Surrounding
	MOBILES					("Mobiles"),

	ROAD					("Road"),
	BUILDINGS				("Buildings"),
	TRAFIC_SIGNS			("TraficSigns");

	String label;

	private EHorizonComponent(String _label) {
		label = _label;
	}

	public String label() {
		return label;
	}

}
