package fr.gis.openstreetmap.api.reader.xml.items;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import fr.java.lang.properties.ID;

public abstract class OsmItem {
	private ID  	  			id;
	private Map<String, String> tags;
	private Instant				timestamp;
	
	protected OsmItem() {
		super();
		tags = new HashMap<String, String>();
	}

	public ID    				getId()        						{ return id; }
	public void 				setId(ID _id)						{ id = _id; }

	public Instant 				getTimstamp()  						{ return timestamp; }
	public void   				setTimestamp(Instant _date)  		{ timestamp = _date; }

	public Map<String, String>	getTags()  							{ return tags; }
	public String				getTag(String _key)  				{ return tags.get(_key); }
	public void					addTag(String _key, String _value) 	{ tags.put(_key, _value); }

}
