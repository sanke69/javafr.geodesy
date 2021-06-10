package fr.gis.sdk.layers.base;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.gis.api.GisTileProvider;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileSystem;

public abstract class BaseMapLayerInfo implements TileSystem {

	public abstract class OnLineInfos    implements GisTileProvider.OnLineInfos {
		public String 			getName() {
			return BaseMapLayerInfo.this.getName();
		}
	}
	public abstract class OnStorageInfos implements GisTileProvider.OnStorageInfos {

		public String 			getName() {
			return BaseMapLayerInfo.this.getName();
		}
		public String 			getExtension() {
			return BaseMapLayerInfo.this.getTileExtension();
		}

		public Path 			getRelativePath(TileCoordinate _coords) {
			Path relativePath = Paths.get("");//BaseMapLayerInfo.this.getName());

			relativePath = relativePath.resolve(Integer.toString(_coords.getLevel()));
			relativePath = relativePath.resolve(_coords.getI() + "x" + _coords.getJ() + "." + getExtension());

			return relativePath;
		}

	}

	private String	name;
	private String	tileExt;
	private int		minLevel, maxLevel;
	private boolean persistent = true;

	public BaseMapLayerInfo(String _name, int _minLevel, int _maxLevel, String _tileExt) {
		super();
		name 	 = _name;
		minLevel = _minLevel;
		maxLevel = _maxLevel;
		tileExt  = _tileExt;
	}

	public final    OnStorageInfos 	  storageInfos() { return new OnStorageInfos() {}; }
	public abstract OnLineInfos 	  onlineInfos();

	public String 	getName() {
		return name;
	}
	public String 	getTileExtension() {
		return tileExt;
	}

	@Override
	public int minLevel() {
		return minLevel;
	}
	@Override
	public int maxLevel() {
		return maxLevel;
	}

	@Override
	public int tileSize(int _level) {
		return 256;
	}

	@Override
	public long tileCount(int _level) {
		return 0;
	}

	@Override
	public long mapSize(int _level) {
		return 0;
	}

	public void    setPersistent(boolean _persistent) {
		persistent = _persistent;
	}
	public boolean isPersistent() {
		return persistent;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		BaseMapLayerInfo other = (BaseMapLayerInfo) obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}


	private URL		iconUrl;
	@Deprecated
	public void setIconUrl(URL _iconUrl) {
		iconUrl = _iconUrl;
	}
	@Deprecated
	public URL  getIconUrl() {
		return iconUrl;
	}

}