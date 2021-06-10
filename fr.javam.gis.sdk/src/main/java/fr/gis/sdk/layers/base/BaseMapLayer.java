package fr.gis.sdk.layers.base;

import fr.gis.api.GisLayer;
import fr.gis.api.GisTileProvider;
import fr.gis.sdk.layers.EmptyMapLayer;
import fr.gis.sdk.layers.base.tilesystem.GisTileProviderImpl;

public class BaseMapLayer extends EmptyMapLayer {

	private GisTileProvider tileProvider;

	public BaseMapLayer(String _name, BaseMapLayerInfo _info) {
		super(_name, GisLayer.BASE);
		tileProvider = new GisTileProviderImpl(_info);
	}

	@Override
	public GisTileProvider 	getTileProvider() {
		return tileProvider;
	}

	@Override
	public String 			toString() {
		return getName();
	}

}
