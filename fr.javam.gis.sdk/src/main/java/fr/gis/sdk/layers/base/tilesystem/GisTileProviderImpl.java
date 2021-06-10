package fr.gis.sdk.layers.base.tilesystem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import fr.gis.api.GisTileProvider;
import fr.gis.mercator.MercatorTileSystem;
import fr.gis.sdk.layers.base.BaseMapLayerInfo;
import fr.java.data.Data;
import fr.java.data.DataNotFoundException;
import fr.java.jvm.properties.PropertiesEx;
import fr.java.lang.enums.AccessMode;
import fr.java.maths.Coordinates;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileSystem;
import fr.java.sdk.data.async.AsyncDataProvider;
import fr.java.sdk.data.async.caches.AbstractDataCacheOnLineWithMaxRequests;
import fr.java.sdk.data.async.caches.AbstractDataCacheOnStorageWithInfos;
import fr.java.sdk.data.async.caches.DefaultDataCacheInMemory;
import fr.java.sdk.nio.file.FileObject;
import fr.java.utils.LocalFiles;

public class GisTileProviderImpl
	extends 	AsyncDataProvider<TileCoordinate, BufferedImage> 
	implements 	GisTileProvider {

	private static final Path		defaultRootFolder  = PropertiesEx.applyOrElse("resources_path", 
																					p -> Paths.get(p).resolve("skyview"), 
																					Paths.get(System.getProperty("user.home")).resolve(".javafr"));

	private static final Path 		defaultCacheFolder = Paths.get("tilesCache");

	private static final Pattern	folderPattern      = Pattern.compile("(?<ZOOM>\\d+)");
	private static final Pattern	filePattern	       = Pattern.compile("(?<I>\\d*)x(?<J>\\d*)\\.(?<EXT>\\w*)");

	private static final String		folderFormat	   = "${ZOOM}d";
	private static final String		fileFormat	       = "${I}dx${J}d.${EXT}s";

	private static final TileSystem tileProvider       = new MercatorTileSystem();

	public static class CacheOnLine    extends AbstractDataCacheOnLineWithMaxRequests<TileCoordinate, BufferedImage> {
		GisTileProvider.OnLineInfos infos;
	
		public CacheOnLine(GisTileProvider.OnLineInfos _infos) {
			super("CacheOnLine-" + _infos.getName(), 8);
			infos = _infos;
		}
	
		@Override
		public boolean 			isServerOnline() {
			return infos.isServerOnLine();
		}
	
		@Override
		public URL 				getURL(TileCoordinate _coords) {
			return infos.getURL(_coords);
		}
	
		@Override
		public BufferedImage 	getContent(InputStream _stream) throws DataNotFoundException {
			try {
				return (BufferedImage) ImageIO.read(_stream);
			} catch(IOException e) {
				throw new DataNotFoundException();
			}
		}
	
	}

	public static class CacheOnStorage extends AbstractDataCacheOnStorageWithInfos<TileCoordinate, BufferedImage> {
		GisTileProvider.OnStorageInfos 									infos;
		protected transient Map<Integer, Map<Integer, List<Integer>>>	cacheInfo;
	
		protected transient int currentVisitedLevel = -1;
		
		public CacheOnStorage(GisTileProvider.OnStorageInfos _infos) {
			super(defaultRootFolder.resolve(defaultCacheFolder).resolve(_infos.getName()), 
					AccessMode.ReadWrite);
			
			infos     = _infos;
			cacheInfo = new HashMap<Integer, Map<Integer, List<Integer>>>();
			updateCacheInfos();
		}

		public boolean 			isDataFolder(Path _path) {
			String name = _path.getFileName().toString();
	
			Matcher folderMatcher = folderPattern.matcher(name);
			if(folderMatcher.find()) {
				currentVisitedLevel = Integer.parseInt(folderMatcher.group("ZOOM"));
				return true;
			}
	
			currentVisitedLevel = -1;
			return false;
		}
		public boolean 			isDataFile(Path _file) {
			String path = _file.getFileName().toString();
	
			return filePattern.matcher(path).matches();
		}
	
		@Override
		public Path 			getPath(TileCoordinate _coords) {
			return getCachePath().resolve(infos.getRelativePath(_coords));
		}
		@Override
		public TileCoordinate 	getCoordinates(Path _file) {
			if(currentVisitedLevel == -1)
				throw new IllegalArgumentException();
	
			String   name = _file.getFileName().toString();
			int      i = 0, j = 0;
	
			Matcher fileMatcher = filePattern.matcher(name);
			if(fileMatcher.find()) {
				i = Integer.parseInt( fileMatcher.group("I") );
				j = Integer.parseInt( fileMatcher.group("J") );
			} else {
				return null;
			}
	
			return Coordinates.newTileCoordinate(0d, 0d, i, j, currentVisitedLevel);
		}
		@Override
		public BufferedImage 	getContent(Path _file) {
			try {
				return (BufferedImage) ImageIO.read(_file.toFile());
			} catch(IOException e) {
				e.printStackTrace();
				System.err.println("see: " + _file);
				try {
					LocalFiles.rm(_file);
				} catch (IOException e1) { e1.printStackTrace(); }
	
				return null;
			}
		}
	
		@Deprecated
		public void 			clearCacheInfo() {
			cacheInfo.clear();
		}
		@Override
		public void 			addToCacheInfo(TileCoordinate _dataCoords, long _dataSize) {
			Map<Integer, List<Integer>> zoomMap = cacheInfo.get(_dataCoords.getLevel());
			if(zoomMap == null) {
				zoomMap = new HashMap<>();
				cacheInfo.put(_dataCoords.getLevel(), zoomMap);
			}
	
			List<Integer> yCoords = zoomMap.get(_dataCoords.getI());
			if(yCoords == null) {
				yCoords = new ArrayList<>();
				zoomMap.put(_dataCoords.getI(), yCoords);
			}
	
			yCoords.add(_dataCoords.getJ());
		}
		@Override
		public boolean 			contains(TileCoordinate _coords) {
			Map<Integer, List<Integer>> lvlMap = cacheInfo.get(_coords.getLevel());
			
			if(lvlMap == null)
				return false;
	
			List<Integer> yList = lvlMap.get(_coords.getI());
	
			if(yList == null)
				return false;
	
			return yList.contains(_coords.getJ());
		}
	
		@Override
		public void 			addToStorage(TileCoordinate _coords, BufferedImage _data) throws IOException {
			Path absolutePath   = getCachePath().resolve( infos.getRelativePath(_coords) );
	
			FileObject dir = new FileObject(absolutePath.getParent());
			if(!dir.isExist())
				dir.mkdir();

			ImageIO.write(_data, infos.getExtension(), absolutePath.toFile());
			addToCacheInfo(_coords, _data.getRaster().getDataBuffer().getSize());
		}

	}

	public static class CacheInMemory  extends DefaultDataCacheInMemory<TileCoordinate, BufferedImage> {

		public CacheInMemory(long _capacity) {
			super(_capacity, _data -> (long) 4 * _data.getHeight() * _data.getWidth());
		}
		
	}

	private BaseMapLayerInfo info;

	public GisTileProviderImpl(BaseMapLayerInfo _info) {
		super();
		info = _info;

		registerNewCache("InMemory",  new CacheInMemory(2_000_000));
		registerNewCache("OnStorage", new CacheOnStorage(_info.storageInfos()));
		registerNewCache("OnLine",    new CacheOnLine(_info.onlineInfos()));
	}

	public BaseMapLayerInfo 							getInfo() {
		return info;
	}

	@Override
	public int 											maxLevel() {
		return info.maxLevel();
	}
	@Override
	public int 											tileSize(int _level) {
		return tileProvider.tileSize(_level);
	}
	@Override
	public long 										tileCount(int _level) {
		return tileProvider.tileCount(_level);
	}
	@Override
	public long 										mapSize(int _level) {
		return tileProvider.mapSize(_level);
	}

	public Data.Async<TileCoordinate, BufferedImage> 	get(int _level, int _i, int _j) {
		if(!isValidTile(_level, _i, _j))
			return null;
		return get(Coordinates.newTileCoordinate(_i, _j, _level));
	}

	protected boolean 									isValidTile(int _level, int _i, int _j) {
		if(_i < 0 || _j < 0)
			return false;
		if(mapSize(_level) <= _i * tileSize(_level))
			return false;
		if(mapSize(_level) <= _j * tileSize(_level))
			return false;
		if(_level < info.minLevel() || _level > info.maxLevel())
			return false;
		return true;
	}

}
