package fr.gis.openstreetmap.api.reader.xml.items.types.attributes;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.java.lang.properties.ID;

public class OsmNodeRef extends OsmItem {

	private ID ref;

	public OsmNodeRef() { 
		ref = null; 
	}
	public OsmNodeRef(ID _ref) { 
		ref = _ref; 
	}
	public OsmNodeRef(OsmNodeRef _node) { 
		ref = _node.ref; 
	}
	
	public ID getRef() { return ref; }
	
	public void setRef(ID _ref) {
		ref = _ref;
	}

	public String toString(){
		return new String("[NodeRef] ")
				+ "(ref= " + ref
				+ ")";
	}

}
