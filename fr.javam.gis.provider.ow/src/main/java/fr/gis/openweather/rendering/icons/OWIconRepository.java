package fr.gis.openweather.rendering.icons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fr.java.utils.ServerConnectionChecker;
import fr.media.image.utils.BufferedImages;

public class OWIconRepository {
	private static final ServerConnectionChecker checkConn = new ServerConnectionChecker("openweathermap.org", 80, 5000);

	private static final String URL = "http://openweathermap.org/img/w/01d.png";

	private Map<String, BufferedImage> iconMap;

	public OWIconRepository() {
		iconMap = new HashMap<String, BufferedImage>();
	}

	public BufferedImage loadIcon(String iconCode) {
		BufferedImage icon = iconMap.get(iconCode);
		if(icon == null && checkConn.isOnline()) {
			try {
				icon = loadIconFromUrl(String.format(URL, iconCode));
				iconMap.put(iconCode, icon);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return icon;
	}

	private BufferedImage loadIconFromUrl(String url) throws IOException {
		byte[] bimg = cacheInputStream(new URL(url));
		if(bimg != null) {
			BufferedImage img = BufferedImages.createCompatibleImage(new ByteArrayInputStream(bimg));
			return img;
		} else {
			return null;
		}
	}

	protected byte[] cacheInputStream(URL url) throws IOException {
		try {
			InputStream ins = url.openStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf = new byte[256];
			while(true) {
				int n = ins.read(buf);
				if(n == -1) {
					break;
				}
				bout.write(buf, 0, n);
			}
			return bout.toByteArray();
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
