package fr.gis.mappy;

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

public class MapProviderMappy extends SimpleGisProvider implements GisProvider {
	private static final String           		 name      = "Mappy";
	private static final String           		 server    = "map3.mappy.net";
	private static final ServerConnectionChecker checkConn = new ServerConnectionChecker(server, 80, 5000);

	public MapProviderMappy() {
		super(name);

		List<MapMode> modes = Arrays.asList(MapMode.STANDARD, MapMode.STANDARD_HD, MapMode.PHOTO);
		for(MapMode mode : modes)
			registerLayer(new BaseMapLayer(mode.name, new BaseLayerInfoMappy(mode)));
	}

	public static class MapMode {
	    public final static MapMode	STANDARD		= new MapMode("Standard",       "standard",    256, "png");
	    public final static MapMode	STANDARD_HD		= new MapMode("Standard HD",    "standard_hd", 256, "png");
		public final static MapMode	PHOTO			= new MapMode("Photo",  		"photo",       256, "jpeg");
	
		private String	name;
		private String	type;
		private int     size;
		private String	ext;

		private MapMode(final String _name, final String _type, final int _size, final String _ext) {
			name = _name;
			type = _type;
			size = _size;
			ext  = _ext;
		}

	}

	public class BaseLayerInfoMappy extends BaseMapLayerInfo {
		private MapMode mode;
	
		public BaseLayerInfoMappy(MapMode _mode) {
			super("Mappy " + _mode.name, 0, 19, _mode.ext);
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
					String format = "https://${server}s/map/1.0/slab/${type}s/${size}d/${zoom}d/${x}d/${y}d";
					
					@SuppressWarnings("serial")
					HashMap<String, Object> data = new HashMap<String, Object>() {{ 
						put("server", getServerName());
						put("type",   mode.type);
						put("size",   mode.size);
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
