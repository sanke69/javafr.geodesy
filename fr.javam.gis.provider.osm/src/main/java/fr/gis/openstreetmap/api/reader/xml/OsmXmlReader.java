package fr.gis.openstreetmap.api.reader.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import fr.gis.api.Gis;
import fr.gis.api.road.Road;
import fr.gis.openstreetmap.api.reader.xml.converter.OsmConverter;
import fr.gis.openstreetmap.api.reader.xml.handler.OsmXmlHandler;
import fr.gis.openstreetmap.api.reader.xml.items.OsmItem;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmNode;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmRelation;
import fr.gis.openstreetmap.api.reader.xml.items.types.OsmWay;
import fr.java.lang.properties.ID;
import fr.java.sdk.log.LogInstance;

public class OsmXmlReader {
	public static final LogInstance log = LogInstance.getLogger();

	protected Map<ID, OsmNode>					nodes;
	protected Map<ID, OsmWay> 					ways;
	protected Map<ID, OsmRelation> 				relations;

	protected Map<ID, Gis.Building> 			buildings;
	protected Map<ID, Gis.Relief> 				reliefs;
	protected Map<ID, Road.Element.Linkable>	roads;

	// For all unclassified
	protected Map<ID, Gis.Node> 				pois;	// Point of Interest
	protected Map<ID, Gis.Curve> 				lois;	// Line of Interest
	protected Map<ID, Gis.Area> 				rois;	// Region of Interest

	private OsmXmlReader() {
		super();
		nodes    	= new HashMap<ID, OsmNode>();
		ways 		= new HashMap<ID, OsmWay>();
		relations   = new HashMap<ID, OsmRelation>();
		
		roads   	= new HashMap<ID, Road.Element.Linkable>();
		buildings   = new HashMap<ID, Gis.Building>();
		reliefs   	= new HashMap<ID, Gis.Relief>();
		
		pois   		= new HashMap<ID, Gis.Node>();
		lois   		= new HashMap<ID, Gis.Curve>();
		rois   		= new HashMap<ID, Gis.Area>();
	}
	public OsmXmlReader(Path _dbPath) {
		this();

		try { preload(new FileInputStream(_dbPath.toFile()));
		} catch(FileNotFoundException e) { }
		load();

		summary();
	}
	public OsmXmlReader(InputStream _dbFile) {
		this();

		preload(_dbFile);
		load();

		summary();
	}

	public void preload(InputStream _stream) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		OsmXmlHandler    handler = new OsmXmlHandler();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(_stream, (DefaultHandler) handler);
		} catch (Exception e) {
			System.err.println("File Read Error: " + e);
			e.printStackTrace();
		}

		// Populate Lists of Nodes / Ways and Relations
		for(OsmItem item : handler.getList())
			switch(item.getClass().getSimpleName()) {
			case "OsmBounds" : 		//OsmBounds ibound = (OsmBounds) item;
									break;
			case "OsmNode" : 		nodes.put(item.getId(), (OsmNode) item);
				    				break;
			case "OsmWay" : 		ways.put(item.getId(), (OsmWay) item);
				    				break;
			case "OsmRelation" : 	relations.put(item.getId(), (OsmRelation) item);
				    				break;
		    default :				System.err.println("Unknown " + item.getClass().getName() + " as OSM Class Name");
		    						break;
			}
		
	}
	public void load() {
    	for(OsmWay way : ways.values()) {
    		switch(OsmConverter.getNature(way)) {
			case ROAD_ELEMENT:				roads.put(way.getId(), OsmConverter.asRoadElement(way, nodes));
											break;
			case ENVIRONMENT_ELEMENT:		
											break;
			case BUILDING:					buildings.put(way.getId(), OsmConverter.asBuildingElement(way, nodes));
											break;
			case UNKNOWN:					
											break;
			default:						
											break;
    		}
    	}

	}
	public void unload() {
		nodes.clear();
		ways.clear();
		relations.clear();

		roads.clear();
		buildings.clear();
		reliefs.clear();

		pois.clear();
		lois.clear();
		rois.clear();
	}

	public void summary() {
		System.out.println("Summary:");
		System.out.println("+ OSM Items:");
		System.out.println("  - Nb Nodes:     " + nodes.size());
		System.out.println("  - Nb Ways:      " + ways.size());
		System.out.println("  - Nb Relations: " + relations.size());
		System.out.println("+ Identified Objects:");
		System.out.println("  - Nb Roads:     " + roads.size());
		System.out.println("  - Nb Buildings: " + buildings.size());
		System.out.println("  - Nb Reliefs:   " + reliefs.size());
		System.out.println("+ Unidentified Objects:");
		System.out.println("  - Nb POIS:      " + pois.size());
		System.out.println("  - Nb LOIS:      " + lois.size());
		System.out.println("  - Nb ROIS:      " + rois.size());
		
	}

	// ACCESS DATA
	public Map<ID, OsmNode> 				getNodes() 					{ return nodes; }
	public Map<ID, OsmWay>  				getWays() 					{ return ways; }
	public Map<ID, OsmRelation> 			getRelations() 				{ return relations; }

	public Map<ID, Gis.Building> 			getBuildingElements() 		{ return buildings; }
	public Map<ID, Gis.Relief> 				getReliefs() 				{ return reliefs; }
	public Map<ID, Road.Element> 			getRoadElements() 			{ 
		return roads.entrySet().stream().collect(Collectors.toMap(
													            e -> e.getKey(),
													            e -> (Road.Element) e.getValue()
													        ));
	}
	public Map<ID, Road.Element.Linkable> 	getLinkableRoadElements() 	{ return roads; }

	public Map<ID, Gis.Node> 				getPOIS() 					{ return pois; }
	public Map<ID, Gis.Curve> 				getLOIS() 					{ return lois; }
	public Map<ID, Gis.Area> 				getROIS() 					{ return rois; }

}
