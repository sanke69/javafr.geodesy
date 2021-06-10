package fr.gis.sdk.layers;

import fr.gis.api.GisLayer;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;

public abstract class AbstractMapLayer implements GisLayer {
	private static int  newIndex = 0;

	final ID         	id;
	final String       	name;
	final Type         	type;

	public AbstractMapLayer(GisLayer.Type _type) {
		this("layer-" + newIndex++, _type);
	}
	public AbstractMapLayer(String _name, GisLayer.Type _type) {
		super();
		id    = IDs.random(32);
		name  = _name;
		type  = _type;
	}

	@Override
	public ID 					getId() {
		return id;
	}
	@Override
	public String 				getName() {
		return name;
	}
	@Override
	public Type 				getType() {
		return type;
	}

	@Override
	public String 				toString() {
		return getName();
	}

}
