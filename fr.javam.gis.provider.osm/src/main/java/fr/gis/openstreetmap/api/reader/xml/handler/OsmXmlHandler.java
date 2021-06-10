package fr.gis.openstreetmap.api.reader.xml.handler;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmBounds;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmNode;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmRelation;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmWay;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmMember;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmNodeRef;
import fr.gis.openstreetmap.api.reader.xml.items.types.attributes.OsmTag;
import fr.java.jvm.properties.id.IDs;

public class OsmXmlHandler extends DefaultHandler {

	private   List<OsmItem> osm;
	private   OsmItem       item, item2;
	private   StringBuffer 	buffer;
	protected boolean 		inOSM, inBounds, inNode, inWay, inRelation, inTag, inNodeRef, inMember;

	// simple constructeur
	public OsmXmlHandler(){
		super();
		osm 		= null;
		item 		= null;
		item2 		= null;
		
		inOSM   	= false;
		inBounds   	= false;
		inNode   	= false;
		inWay   	= false;
		inRelation  = false;
		inTag   	= false;
		inNodeRef   = false;
		inMember   	= false;
		
		buffer		= null;
	}

	public List<OsmItem> getList() {
		return osm;
	}

	//début du parsing
	public void startDocument() throws SAXException {
		System.out.println("Début du parsing");
	}
	//fin du parsing
	public void endDocument() throws SAXException {
		System.out.println("Fin du parsing");
		//System.out.println("Resultats du parsing");
		//for(OsmItem p : osm){
		//	System.out.println(p);
		//}
	}

	//détection d'ouverture de balise
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch(qName) {
		case "osm"	    :	osm   = new LinkedList<OsmItem>();	inOSM    	= true;	break;

		case "bounds"   : 	item = readBound(attributes);		inBounds 	= true;	break;

		case "node"     :	item = readNode(attributes);		inNode   	= true;	break;
		case "way"      : 	item = readWay(attributes);			inWay    	= true;	break;
		case "relation" : 	item = readRelation(attributes);	inRelation 	= true;	break;
							
		case "tag"      : 	item2 = readTag(attributes);		inTag 		= true;	break;
		case "nd"       : 	item2 = readNodeRef(attributes);	inNodeRef 	= true; break;
		case "member"   :	item2 = readMember(attributes);		inMember 	= true;	break;

//		default :			throw new SAXException("Balise " + qName + " inconnue.");
		default :			System.err.println("Balise " + qName + " inconnue.");
		}
	}

	//détection fin de balise
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch(qName) {
		case "osm" : 		item  = null;	item2 = null;	inOSM = false;	break;

		case "bounds" : 	osm.add(item);
							item = null;	inBounds   = false;	break;

		case "node" :		osm.add(item);	
							item = null;	inNode     = false;	break;
		case "way" :		osm.add(item);	
							item = null;	inWay      = false;	break;
		case "relation" :	osm.add(item);	
							item = null;	inRelation = false;	break;

		case "tag" : 		item.addTag(((OsmTag) item2).getKey(), ((OsmTag) item2).getValue());
							item2 = null;	inTag      = false;	break;
		case "nd" : 		((OsmWay) item).addNodeRef(new OsmNodeRef((OsmNodeRef) item2));
							item2 = null;	inNodeRef  = false;	break;
		case "member" :		((OsmRelation) item).addMember(((OsmMember) item2));
							item2 = null;	inMember   = false;	break;

//		default :			throw new SAXException("Balise " + qName + " inconnue.");
		default :			System.err.println("Balise " + qName + " inconnue.");
		}
	}

	public void characters(char[] _ch, int _start, int _length) throws SAXException {
		String lecture = new String(_ch, _start, _length);
		if(buffer != null) buffer.append(lecture);       
	}

	private OsmBounds   readBound(Attributes _attributes) throws SAXException {
		OsmBounds bounds = new OsmBounds();

		try {
			bounds.setMinLat( Double.parseDouble(_attributes.getValue("minlat")) );
			bounds.setMinLon( Double.parseDouble(_attributes.getValue("minlon")) );
			bounds.setMaxLat( Double.parseDouble(_attributes.getValue("maxlat")) );
			bounds.setMaxLon( Double.parseDouble(_attributes.getValue("maxlon")) );
		} catch(Exception e) {
			throw new SAXException(e);
		}

		return bounds;
	}
	private OsmNode     readNode(Attributes _attributes) throws SAXException {
		OsmNode node = new OsmNode();
		try {
			node.setId			( IDs.newUTF8(_attributes.getValue("id")) );
			node.setLongitude	( Double.parseDouble(_attributes.getValue("lon")) );
			node.setLatitude	( Double.parseDouble(_attributes.getValue("lat")) );
//			node.setTimestamp	( new Date( attributes.getValue("timestamp") ));
		} catch(Exception e) {
			throw new SAXException(e);
		}
		
		return node;
	}
	private OsmWay      readWay(Attributes _attributes) throws SAXException {
		OsmWay way = new OsmWay();

		try {
			way.setId( IDs.newUTF8(_attributes.getValue("id")) );
//			way.setTimestamp(new Date( attributes.getValue("timestamp") ));
		} catch(Exception e) {
			throw new SAXException(e);
		}
		
		return way;
	}
	private OsmRelation readRelation(Attributes _attributes) throws SAXException {
		OsmRelation relation = new OsmRelation();

		try {
			relation.setId(IDs.newUTF8(_attributes.getValue("id")));
//			relation.setTimestamp(new Date( _attributes.getValue("timestamp") ));
		} catch(Exception e) {
			throw new SAXException(e);
		}

		return relation;
	}
	private OsmTag      readTag(Attributes _attributes) throws SAXException {
		OsmTag tag = new OsmTag();

		try {
			tag.setKey( _attributes.getValue("k") );
			tag.setValue( _attributes.getValue("v") );
		} catch(Exception e) {
			throw new SAXException(e);
		}
		
		return tag;
	}
	private OsmNodeRef  readNodeRef(Attributes _attributes) throws SAXException {
		OsmNodeRef nodeRef = new OsmNodeRef();

		try {
			nodeRef.setRef(IDs.newUTF8(_attributes.getValue("ref")));
		} catch(Exception e) {
			throw new SAXException(e);
		}

		return nodeRef;
	}
	private OsmMember   readMember(Attributes _attributes) throws SAXException {
		OsmMember member = new OsmMember();

		try {
			member.setType( _attributes.getValue("type") );
			member.setRef( IDs.newUTF8(_attributes.getValue("ref")) );
			member.setRole( _attributes.getValue("role") );
		} catch(Exception e) {
			throw new SAXException(e);
		}

		return member;
	}

}
