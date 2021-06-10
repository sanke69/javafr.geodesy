package fr.gis.utils;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.GisService;
import fr.gis.api.Gis.Path;
import fr.gis.api.road.Road;
import fr.gis.api.road.RoadCoordinate;
import fr.gis.api.road.tools.RoadToolBox;
import fr.gis.api.tools.MapToolBox;
import fr.gis.sdk.SimpleGisProvider;
import fr.gis.sdk.SimpleGisService;
import fr.gis.sdk.layers.DefaultMapLayer;
import fr.gis.sdk.layers.EmptyMapLayer;
import fr.gis.sdk.layers.MapLayerType;
import fr.gis.sdk.objects.gis.GisPath;
import fr.gis.utils.tools.CustomMapToolBox;
import fr.gis.utils.tools.DefaultMapToolBox;
import fr.gis.utils.tools.road.CustomRoadToolBox;
import fr.gis.utils.tools.road.DefaultRoadToolBox;
import fr.java.lang.properties.ID;

public final class GisUtils {

	/**
	 * UTILS
	**/
	public static RoadToolBox 						createRoadToolBox(GisProvider _provider) {
		return new DefaultRoadToolBox(_provider);
	}
	public static RoadToolBox 						createRoadToolBox(Collection<Gis.Building> _buildings, Collection<Gis.Dynamics> _mobiles, Collection<Road.Element> _road, Collection<Road.TraficSign> _trafficSigns) {
		return new CustomRoadToolBox(_buildings, _mobiles, _road, _trafficSigns);
	}

	public static MapToolBox 						createMapToolBox(GisProvider _provider) {
		return new DefaultMapToolBox(_provider);
	}
	public static MapToolBox 						createMapToolBox(Collection<Gis.Building> _buildings, Collection<Gis.Dynamics> _mobiles) {
		return new CustomMapToolBox(_buildings, _mobiles);
	}

	/**
	 * OBJECTS
	 */
	public static Gis.Path createPath(ID _id, Datum _datum, GeoCoordinate... _coords) {
		return new GisPath(_id, _datum, _coords);
	}

	public static Path createPath(ID _id, Datum _datum, List<GeoCoordinate> _coords) {
		return new GisPath(_id, _datum, _coords);
	}

	public static double 							computeLength(Gis.Curve _curve) {
		double l = 0;

		for(int i = 0; i <  _curve.getTops().size() - 1; ++i) {
			final GeoCoordinate a = _curve.getTops().get(i);
			final GeoCoordinate b = _curve.getTops().get(i + 1);

			l += GeoCoordinates.computeDistance(a, b);
		}

		return l;
	}

	/**
	 * SERVICES AND PROVIDERS
	**/
	public static GisService.Registrable			createService() {
		return new SimpleGisService();
	}
	public static GisService.Registrable			createService(GisProvider _provider) {
		GisService.Registrable map = new SimpleGisService();
		map.registerProvider(_provider);
		return map;
	}
	public static GisService.Registrable			createService(GisProvider... _providers) {
		GisService.Registrable map = new SimpleGisService();
		for(GisProvider _provider : _providers)
			map.registerProvider(_provider);
		return map;
	}
	public static GisService.Registrable			createService(Collection<GisProvider> _providers) {
		GisService.Registrable map = new SimpleGisService();
		for(GisProvider _provider : _providers)
			map.registerProvider(_provider);
		return map;
	}

	public static GisProvider.Registrable 			createProvider(String _name) {
		return new SimpleGisProvider(_name);
	}
	public static GisProvider.Registrable 			createProvider(String _name, Collection<GisLayer> _availableLayers) {
		return new SimpleGisProvider(_name) {{ registerLayers(_availableLayers); }};
	}
	public static GisProvider.Registrable 			createProvider(String _name, GisLayer... _availableLayers) {
		return new SimpleGisProvider(_name) {{ registerLayers(_availableLayers); }};
	}
	public static GisProvider.Registrable 			createProvider(String _name, GisLayer _availableLayer) {
		return new SimpleGisProvider(_name) {{ registerLayer(_availableLayer); }};
	}

	public static <T extends Gis.Object> GisLayer 	createLayer(String _name, GisLayer.Type _layerConfig) {
		return new DefaultMapLayer(_name, _layerConfig);
	}
	public static <T extends Gis.Object> GisLayer 	createLayerEmpty(String _name, GisLayer.Type _layerConfig) {
		return new EmptyMapLayer(_name, _layerConfig);
	}

	public static GisLayer.Type 					createLayerType(String _label, boolean _isRenderable) {
		return new MapLayerType(_label, _isRenderable, true) {};
	}
	public static GisLayer.Type 					createLayerType(String _label, boolean _isRenderable, int _index) {
		return new MapLayerType(_index, _label, _isRenderable, true) {};
	}

	/**
	 * OTHERS
	**/
	public static String 							toString(RoadCoordinate _mapMatched) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("0.00 m");

		boolean isDirect = _mapMatched.getRoadElementDirection() == Gis.Direction.DIRECT;
		
		sb.append("GeoMapMatchedInformation:");
		sb.append("  + RoadElement:");
		sb.append("    - ID:      ").append(_mapMatched.getRoadElement().getId());
		sb.append("    - AbsCurv: ").append(df.format(_mapMatched.getCurvilinearAbscissa(!isDirect)));
		sb.append("    - SegID:   ").append(_mapMatched.getRoadElementSegmentId());
		sb.append("    - LaneID:  ").append(_mapMatched.getRoadElementLaneId());
		sb.append("  + Projection:");
		sb.append("    - Error:   ").append(_mapMatched.getProjectionError());
		
		return sb.toString();
	}

}
