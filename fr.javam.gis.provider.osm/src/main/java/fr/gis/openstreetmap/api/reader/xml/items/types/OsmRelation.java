package fr.gis.openstreetmap.api.reader.xml.items.types;

import java.util.ArrayList;
import java.util.List;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmMember;

public class OsmRelation extends OsmItem {

	private List<OsmMember> members;

	public OsmRelation() { 
		super();

		members 	= new ArrayList<OsmMember>();
	}
	
	public List<OsmMember>	getMembers()  					{ return this.members; }
	public OsmMember		getMember(int _i)  				{ return this.members.get(_i); }
	
	public void 			addMember(OsmMember _member)  	{ this.members.add(_member); }

	public String toString(){
		return new String("[Relation] ")
				+ "(id= " + getId()
				+ ")";
	}

}
