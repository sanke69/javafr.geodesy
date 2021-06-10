package fr.gis.openstreetmap.utils;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.data.local.LocalReader;
import fr.gis.api.data.local.LocalSource;
import fr.gis.api.road.Road;
import fr.gis.openstreetmap.api.reader.xml.OsmXmlReader;
import fr.gis.sdk.objects.gis.GisBuilding;
import fr.gis.utils.GisUtils;
import fr.gis.utils.tools.road.topology.RoadTopologyGraphBuilder;
import fr.java.sdk.log.LogInstance;

public final class Osm extends LocalReader {
	public static final LogInstance log = LogInstance.getLogger();

	public static Collection<GisLayer> parseAndMerge(LocalSource... sources) {
		Map<GisLayer.Type, GisLayer> layers = new HashMap<GisLayer.Type, GisLayer>();

		for(LocalSource src : sources) {
			InputStream  is     = LocalReader.getSourceStream(src);
			OsmXmlReader loader = new OsmXmlReader(is);

			Collection<Road.Element.Linkable> topoElements = RoadTopologyGraphBuilder.buildGraph( loader.getLinkableRoadElements() );
			Collection<Road.Element>          roadElements = loader.getRoadElements().values();
			Collection<Gis.Building>          buildings    = loader.getBuildingElements().values();
			
			
			if( ! roadElements.isEmpty() ) {
				GisLayer geometryLayer = layers.get(GisLayer.ROAD_GEOMETRY);
				
				if(geometryLayer == null) {
					geometryLayer = GisUtils.createLayer("RoadElements", GisLayer.ROAD_GEOMETRY);
					layers.put(GisLayer.ROAD_GEOMETRY, geometryLayer);
				}

				for(Road.Element elt : loader.getRoadElements().values())
					geometryLayer.getContentEditable().add(elt);
			}


			if( ! topoElements.isEmpty() ) {
				GisLayer graphLayer = layers.get(GisLayer.ROAD_TOPOLOGY);
				
				if(graphLayer == null) {
					graphLayer = GisUtils.createLayer("RoadGraph", GisLayer.ROAD_TOPOLOGY);
					layers.put(GisLayer.ROAD_TOPOLOGY, graphLayer);
				}

				for(Road.Element elt : topoElements)
					graphLayer.getContentEditable().add(elt);
			}

			if( ! buildings.isEmpty() ) {
				GisLayer buildingLayer = layers.get(GisLayer.BUILDINGS);
				
				if(buildingLayer == null) {
					buildingLayer = GisUtils.createLayer("Buildings", GisLayer.BUILDINGS);
					layers.put(GisLayer.BUILDINGS, buildingLayer);
				}

				for(Gis.Building elt : loader.getBuildingElements().values())
					buildingLayer.getContentEditable().add(elt);
			}

			loader.unload();
		}

		
		return layers.values();
	}

}
