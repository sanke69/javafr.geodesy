package fr.gis.google;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fr.gis.api.GisProvider;
import fr.gis.sdk.SimpleGisProvider;
import fr.gis.sdk.layers.base.BaseMapLayer;
import fr.gis.sdk.layers.base.BaseMapLayerInfo;
import fr.java.patterns.tiled.TileCoordinate;
import fr.java.utils.ServerConnectionChecker;
import fr.java.utils.Strings;

public class MapProviderGoogle extends SimpleGisProvider implements GisProvider {
	private static final String           		 name      = "Google";
	private static final String           		 server    = "mts1.google.com";
	private static final ServerConnectionChecker checkConn = new ServerConnectionChecker(server, 80, 5000);

	public MapProviderGoogle() {
		super(name);

		List<MapMode> modes = Arrays.asList(MapMode.ROAD_ONLY, MapMode.TERRAIN_ONLY, MapMode.SATELLITE_ONLY, MapMode.ROAD, MapMode.ROAD_ALT, MapMode.TERRAIN, MapMode.HYBRID);
		for(MapMode mode : modes)
			registerLayer(new BaseMapLayer(mode.name, new BaseLayerInfoGoogle(mode)));
	}


	public static class MapMode {
	    public final static MapMode	ROAD_ONLY		= new MapMode("Road",       "h", "png");
		public final static MapMode	TERRAIN_ONLY	= new MapMode("Terrain",    "t", "jpeg");
		public final static MapMode	SATELLITE_ONLY	= new MapMode("Satellite",  "s", "jpeg");
	    public final static MapMode	ROAD			= new MapMode("RoadStd",    "m", "png");
	    public final static MapMode	ROAD_ALT		= new MapMode("RoadAlt",    "r", "png");
		public final static MapMode	TERRAIN			= new MapMode("TerrainAlt", "p", "jpeg");
		public final static MapMode	HYBRID			= new MapMode("Hybrid",     "y", "jpeg");

		private String	name;
		private String	type;
		private String	ext;

		private MapMode(final String _name, final String _type, final String _ext) {
			name = _name;
			type = _type;
			ext  = _ext;
		}
	}

	class BaseLayerInfoGoogle extends BaseMapLayerInfo {
	
		private MapMode mode;
	
		public BaseLayerInfoGoogle(MapMode _mode) {
			super("GoogleEarth" + _mode.name, 0, 19, _mode.ext);
			mode = _mode;
		}
	
		@Override
		public OnLineInfos 		onlineInfos() {
			return new OnLineInfos() {
	
				@Override
				public String getServerName() {
					return server;
				}
	
				@Override
				public boolean isServerOnLine() {
					return checkConn.isOnline();
				}
	
				@Override
				public URL getURL(TileCoordinate _coords) {
					String format = "https://${server}s/vt/lyrs=${layers}s&x=${x}d&y=${y}d&z=${zoom}d";
	
					@SuppressWarnings("serial")
					HashMap<String, Object> data = new HashMap<String, Object>() {{ 
						put("server", getServerName());
						put("layers", mode.type);
						put("x",      _coords.getI());
						put("y",      _coords.getJ());
						put("zoom",   _coords.getLevel());
					}};
	
					String url = Strings.format(format, data);
					try {
						return new URI(url).toURL();
					} catch(URISyntaxException e) {
						e.printStackTrace();
						return null;
					} catch (MalformedURLException e) {
						e.printStackTrace();
						return null;
					}
				}
	
			};
		}
	
	}

}
