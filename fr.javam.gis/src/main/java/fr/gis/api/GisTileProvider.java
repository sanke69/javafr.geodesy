package fr.gis.api;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.file.Path;

import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProvider;

public interface GisTileProvider extends TileProvider.Async<BufferedImage> {

	public interface OnLineInfos {

		public String 		getName();
		public String 		getServerName();

		public boolean 		isServerOnLine();

		public URL 			getURL(TileCoordinate _coords);
	
	}
	public interface OnStorageInfos {

		public String 		getName();

		public Path 		getRelativePath(TileCoordinate _coords);

		public String 		getExtension();

	}

}
