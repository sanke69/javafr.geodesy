package fr.gis.api;

public enum StandardGisLayerType implements GisLayer.Type {
	BASE				(  0, true,  true, "Base Map"),
	// Environment
	RELIEFS	    		(100, true,  "Relief"),
	BUILDINGS   		(110, true,  "Buildings"),
	ROAD_TRAFICSIGNS 	(200, true,  "TraficSigns"),
	MOBILES 			(300, true,  "Mobiles"),

	// Road Networks
	ROAD_GEOMETRY		( 10, true,  "Road Geometry"),
	ROAD_TOPOLOGY		( 20, true,  "Road Tolopogy"),
	ROAD_METADATA		( 30, false, "Road Metadata");

	int     index;
	String  label;
	boolean isRenderable, hasTileSystem;

	StandardGisLayerType(int _index, boolean _isRenderable, String _label) {
		index         = _index;
		label         = _label;
		isRenderable  = _isRenderable;
		hasTileSystem = false;
	}
	StandardGisLayerType(int _index, boolean _isRenderable, boolean _hasTileSystem, String _label) {
		index         = _index;
		label         = _label;
		isRenderable  = _isRenderable;
		hasTileSystem = _hasTileSystem;
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

	@Override
	public boolean hasTileProvider() {
		return hasTileSystem;
	}

}
