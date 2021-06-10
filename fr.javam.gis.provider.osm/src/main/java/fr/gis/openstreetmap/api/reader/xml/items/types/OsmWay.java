 package fr.gis.openstreetmap.api.reader.xml.items.types;

import java.util.ArrayList;
import java.util.List;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmNodeRef;

public class OsmWay extends OsmItem {

	private List<OsmNodeRef> 	refs;

	public OsmWay() { 
		super();

		refs 		= new ArrayList<OsmNodeRef>();
	}

	public List<OsmNodeRef>	getNodeRefs()  				{ return this.refs; }
	public OsmNodeRef		getNodeRef(int _i)  		{ return this.refs.get(_i); }

	public void 			addNodeRef(OsmNodeRef _ref) { this.refs.add(_ref); }

	public String toString() {
		return new String("[Way] ")
				+ "(id= " + getId()
				+ ")";
	}

}
