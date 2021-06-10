package fr.gis.sdk.objects;

import java.util.HashMap;
import java.util.Map;

import fr.gis.api.Gis;
import fr.java.lang.properties.ID;

public class GisObject implements Gis.Object {
	protected ID 					id;
	protected Map<String, Object> 	properties;

	protected GisObject(ID _id) {
		super();

    	id         = _id;
		properties = new HashMap<String, Object>();
	}

    public ID 		getId() {
		return id;
	}

	public void 	addProperty(String _name, Object _value) {
		properties.put(_name,  _value);
	}
	public Object 	getProperty(String _name) {
		return properties.get(_name);
	}

}
