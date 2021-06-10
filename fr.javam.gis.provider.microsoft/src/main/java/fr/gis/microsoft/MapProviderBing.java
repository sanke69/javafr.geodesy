package fr.gis.microsoft;

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
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.utils.ServerConnectionChecker;
import fr.java.utils.Strings;

public class MapProviderBing extends SimpleGisProvider implements GisProvider {
	private static final String           		 name      = "VirtualEarth";
	private static final String           		 server    = "tiles.virtualearth.net";
	private static final ServerConnectionChecker checkConn = new ServerConnectionChecker(server, 80, 5000);

	public MapProviderBing() {
		super(name);

		List<MapMode> modes = Arrays.asList(MapMode.MAP, MapMode.SATELLITE, MapMode.HYBRID);
		for(MapMode mode : modes)
			registerLayer(new BaseMapLayer(mode.name, new BaseLayerInfoVirtualEarth(mode)));
	}

	public static class MapMode {
		public final static MapMode	MAP			= new MapMode("Map",       "r", "png");
		public final static MapMode	SATELLITE	= new MapMode("Satellite", "a", "jpeg");
		public final static MapMode	HYBRID		= new MapMode("Hybrid",    "h", "jpeg");

		private String	name;
		private String	type;
		private String	ext;

		private MapMode(final String _name, final String _type, final String _ext) {
			name = _name;
			type = _type;
			ext = _ext;
		}

	}

	public class BaseLayerInfoVirtualEarth extends BaseMapLayerInfo {	
		private MapMode mode;
	
		public BaseLayerInfoVirtualEarth(MapMode _mode) {
			super(name + " " + _mode.name, 1, 19, _mode.ext);
			mode = _mode;
		}
	
		@Override
		public OnLineInfos 	onlineInfos() {
			return new OnLineInfos() {
	
				@Override
				public String getServerName() {
					return "ortho.tiles.virtualearth.net";
				}
	
				@Override
				public boolean isServerOnLine() {
					return checkConn.isOnline();
				}
	
				@Override
				public URL getURL(TileCoordinate _coords) {
					final String quad = tileToQuadKey(_coords.getI(), _coords.getJ(), _coords.getLevel());
	
					String format = "http://${mode}s${quadId}s.ortho.tiles.virtualearth.net/tiles/${mode}s${quad}s.${ext}s?g=1";
	
					@SuppressWarnings("serial")
					HashMap<String, Object> data = new HashMap<String, Object>() {{
						put("mode",   mode.type);
						put("quadId", quad.charAt(quad.length() - 1));
						put("quad",   quad);
						put("ext",    mode.ext);
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
	
		private String 		tileToQuadKey(final int tx, final int ty, final int zl) {
			String quad = "";
	
			for(int i = zl; i > 0; i--) {
				int mask = 1 << (i - 1);
				int cell = 0;
	
				if((tx & mask) != 0)
					cell++;
	
				if((ty & mask) != 0)
					cell += 2;
	
				quad += cell;
			}
	
			return quad;
		}
	
	}

}
