package fr.gis.sdk.layers;

import fr.gis.api.GisLayer;
import fr.java.lang.exceptions.NotYetImplementedException;

public class MapLayerType implements GisLayer.Type {
	static int userDefinedIndex = 999;

	int     index;
	String  label;
	boolean isRenderable, userDefined;

	public MapLayerType(String _label) {
		super();
		index        = userDefinedIndex++;
		label        = _label;
		userDefined  = false;
		isRenderable = false;
	}
	public MapLayerType(String _label, boolean _isRenderable) {
		super();
		index        = userDefinedIndex++;
		label        = _label;
		userDefined  = false;
		isRenderable = _isRenderable;
	}
	public MapLayerType(String _label, boolean _isRenderable, boolean _userDefined) {
		super();
		index        = userDefinedIndex++;
		label        = _label;
		userDefined  = _userDefined;
		isRenderable = _isRenderable;
	}
	public MapLayerType(int _id, String _label) {
		super();
		index        = _id;
		label        = _label;
		userDefined  = false;
		isRenderable = false;
	}
	public MapLayerType(int _id, String _label, boolean _isRenderable) {
		super();
		index        = _id;
		label        = _label;
		userDefined  = false;
		isRenderable = _isRenderable;
	}
	public MapLayerType(int _id, String _label, boolean _isRenderable, boolean _userDefined) {
		super();
		index        = _id;
		label        = _label;
		userDefined  = _userDefined;
		isRenderable = _isRenderable;
	}
	public MapLayerType(GisLayer.Type _type) {
		super();
		index        = _type.getIndex();
		label        = _type.getName();
		userDefined  = _type.isUserDefined();
		isRenderable = _type.isRenderable();
	}
	public MapLayerType(GisLayer.Type _type, String _label) {
		this(_type);
		label        = _type.getName() + '-' + _label;
	}
	public MapLayerType(GisLayer.Type _type, String _label, boolean _isRenderable) {
		this(_type);
		label        = _type.getName() + '-' + _label;
		isRenderable = _isRenderable;
	}

	@Override
	public int 		getIndex() {
		return index;
	}

	@Override
	public String 	getName() {
		return label;
	}

	@Override
	public boolean 	isRenderable() {
		return isRenderable;
	}
	@Override
	public boolean 	isUserDefined() {
		return userDefined;
	}

	@Override
	public String 	toString() {
		return "(" + index + ")  " + label + " - " + (isRenderable ? "Renderable" : "Hidden");
	}

	@Override
	public boolean 	equals(Object _o) {
		if(!(_o instanceof MapLayerType))
			return false;
		throw new NotYetImplementedException();
	}

}
