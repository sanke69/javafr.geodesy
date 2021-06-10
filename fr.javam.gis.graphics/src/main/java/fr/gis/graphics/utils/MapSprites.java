package fr.gis.graphics.utils;

import java.util.HashMap;
import java.util.Map;

import fr.gis.api.road.Road;
import javafx.scene.image.Image;

public class MapSprites {
	private static final int width = 32, height = 32;

	private static Map<Road.TraficSign.Type, String> paths   = new HashMap<Road.TraficSign.Type, String>() {{
		put(Road.TraficSign.Type.MANDATORY_SPEED_LIMIT, "/sprites/MSL_#.png");
		put(Road.TraficSign.Type.TRAFIC_LIGHT, 			"/sprites/TraficLight.png");
		put(Road.TraficSign.Type.YIELD,        			"/sprites/Yield.png");
		put(Road.TraficSign.Type.CROSS_WALK,   			"/sprites/Pedestrian.png");
		put(Road.TraficSign.Type.STOP,         			"/sprites/Stop.png");
	}};
	private static Map<Road.TraficSign.Type, Image> sprites = new HashMap<Road.TraficSign.Type, Image>();
	private static Map<Integer, Image>        msls    = new HashMap<Integer, Image>();

	public static Image get(Road.TraficSign.Type _type, int _value) {
		if(_type != Road.TraficSign.Type.MANDATORY_SPEED_LIMIT) {
			if(sprites.containsKey(_type))
				return sprites.get(_type);
	
			Image img = new Image(MapSprites.class.getResourceAsStream(paths.get(_type)), width, height, true, true);
			sprites.put(_type, img);
	
			return img;
		} else {
			if(msls.containsKey(_value))
				return msls.get(_value);
	
			Image img = new Image(MapSprites.class.getResourceAsStream(paths.get(_type).replaceAll("#", "" + _value)), width, height, true, true);
			msls.put(_value, img);
	
			return img;
		}
	}

}
