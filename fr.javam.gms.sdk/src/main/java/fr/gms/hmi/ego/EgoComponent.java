package fr.gms.hmi.ego;

public enum EgoComponent {
	STATE					("State"),
	HISTORY					("History"),

	BUILDINGS				("Buildings"),
	MOBILES					("Obstacles"),

	TRAFIC_SIGNS			("TraficSigns"),
	ROAD_ELEMENTS			("RoadElements"),

	INFLUENCE_AREA			("InfluenceArea"), 

	FORWARD_ROAD_TREE		("ForwardRoadTree"), 
	FORWARD_ROAD_VECTOR		("ForwardRoadVector"),

	FORWARD_TRAJECTORY		("ForwardTrajectory"),
	BACKWARD_TRAJECTORY		("BackwardTrajectory"),
	
	HUD_COMPASS				("HUD:: Compass"),
	HUD_SPEEDOMETER			("HUD:: SpeedOMeter"),
	HUD_RADAR				("HUD:: RADAR");

	String label;

	private EgoComponent(String _label) {
		label = _label;
	}

	public String label() {
		return label;
	}

}
