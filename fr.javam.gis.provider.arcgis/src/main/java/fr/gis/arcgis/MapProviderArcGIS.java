package fr.gis.arcgis;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import fr.gis.api.GisProvider;
import fr.gis.sdk.SimpleGisProvider;
import fr.gis.sdk.layers.base.BaseMapLayer;
import fr.gis.sdk.layers.base.BaseMapLayerInfo;
import fr.java.patterns.tiled.TileCoordinate;
import fr.java.utils.ServerConnectionChecker;
import fr.java.utils.Strings;

public class MapProviderArcGIS extends SimpleGisProvider implements GisProvider {
	private static final String           		 name      = "ArcGIS";
	private static final String           		 server    = "services.arcgisonline.com";
	private static final ServerConnectionChecker checkConn = new ServerConnectionChecker(server, 80, 5000);

	public MapProviderArcGIS() {
		super(name);

		registerLayer(new BaseMapLayer("Default", new BaseLayerInfoArcGIS()), true);
	}

	class BaseLayerInfoArcGIS extends BaseMapLayerInfo {
	
		public BaseLayerInfoArcGIS() {
			super(name, 0, 19, "png");
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
					String format = "http://${server}s/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/${zoom}d/${y}d/${x}d.${ext}s";
	
					@SuppressWarnings("serial")
					HashMap<String, Object> data = new HashMap<String, Object>() {{
						put("server", getServerName());
						put("x",      _coords.getI());
						put("y",      _coords.getJ());
						put("zoom",   _coords.getLevel());
						put("ext",    getTileExtension());
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
