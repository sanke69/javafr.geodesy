package fr.gms.api;

import fr.gis.api.GisLayer;

enum StandardGmsLayerType implements GisLayer.Type {
	// Paths & Trajectories
	PATHS	 		(300, true,  "Paths"),
	MM_PATHS 		(310, true,  "Paths [MapMatched]"),
	TRAJECTORIES	(400, true,  "Trajectories"),
	MM_TRAJECTORIES	(410, true,  "Trajectories [MapMatched]"),
	// Managed Mobiles
	EHORIZONS       (500, true,  "e-Horizons"),
	EGO_VEHICLE     (999, true,  "egoVehicle");

	int     index;
	String  label;
	boolean isRenderable;

	StandardGmsLayerType(int _index, boolean _isRenderable, String _label) {
		index        = _index;
		label        = _label;
		isRenderable = _isRenderable;
	}

	@Override
	public int getIndex() {
		return index;
	}
	@Override
	public String getName() {
		return label;
	}

	@Override
	public boolean isRenderable() {
		return isRenderable;
	}
	@Override
	public boolean isUserDefined() {
		return false;
	}

}
