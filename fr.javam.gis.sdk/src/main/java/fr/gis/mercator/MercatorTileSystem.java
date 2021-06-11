package fr.gis.mercator;

import fr.java.patterns.tiled.TileSystem;

public class MercatorTileSystem implements TileSystem {

	private final int			minLevel, 
								maxLevel;

	private final int			tileSize;

	private final long[]		tilesCountPerLevel;
	private final long[]		mapSizePerLevel;

	public MercatorTileSystem() {
		this(0, 19, 256);
	}
	public MercatorTileSystem(int _minLevel, int _maxLevel, int _tileSize) {
		super();

		minLevel = _minLevel;
		maxLevel = _maxLevel;
		tileSize = _tileSize;

		int nbLevel        = maxLevel - minLevel + 1;
		tilesCountPerLevel = new long[nbLevel];
		mapSizePerLevel    = new long[nbLevel];

		for(int i = 0; i <= maxLevel - minLevel; ++i) {
			tilesCountPerLevel[i] = (int) Math.pow(2, i);
			mapSizePerLevel[i]    = tileSize * tilesCountPerLevel[i];
		}
	}

	@Override
	public int 						maxLevel() {
		return maxLevel;
	}

	@Override
	public int    					tileSize(int _level) {
		return tileSize;
	}
	@Override
	public long   					tileCount(int _level) {
		return tilesCountPerLevel[_level - minLevel];
	}

	@Override
	public long   					mapSize(int _level) {
		if(_level - minLevel < 0 || _level > maxLevel)
			throw new IllegalArgumentException();
		return mapSizePerLevel[_level - minLevel];
	}

}
