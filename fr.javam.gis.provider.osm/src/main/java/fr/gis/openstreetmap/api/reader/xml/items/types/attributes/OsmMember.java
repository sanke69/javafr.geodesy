package fr.gis.openstreetmap.api.reader.xml.items.types.attributes;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.java.lang.properties.ID;

public class OsmMember extends OsmItem {
	
	private String type;
	private ID     ref;
	private String role;

	public OsmMember() { 
		type = null;
		ref  = null;
		role = null;
	}

	public String    		getType()        				{ return type; }
	public ID   			getRef()  						{ return ref; }
	public String			getRole()  						{ return role; }
	
	public void 			setType(String _type)			{ this.type = _type; }
	public void   			setRef(ID _ref)  				{ this.ref 	= _ref; }
	public void 			setRole(String _role)  			{ this.role = _role; }

	public String toString(){
		return new String("[Member] ")
				+ "(type= " + type
				+ ",ref= " + ref.toString()
				+ ",role= " + role + ")";
	}

}
