package fr.gis.graphics.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.gis.graphics.sdk.control.AbstractGisLayerController;
import fr.java.lang.properties.ID;

public class GisControllerFactory {
	private final Map<Object, Class<? extends GisLayerController>> layerControllerClassRegister;

	public GisControllerFactory() {
		super();
		layerControllerClassRegister = new HashMap<Object,  Class<? extends GisLayerController>>();
	}

	public final void 									registerLayerController(Class<? extends GisLayer> _layerClass, Class<? extends GisLayerController> _layerController) {
		layerControllerClassRegister.put(_layerClass, _layerController);
	}
	public final void 									registerLayerController(GisLayer.Type _layerType, Class<? extends GisLayerController> _layerController) {
		layerControllerClassRegister.put(_layerType, _layerController);
	}
	public final void 									registerLayerController(ID _layerId, Class<? extends GisLayerController> _layerController) {
		layerControllerClassRegister.put(_layerId, _layerController);
	}

	public final  Class<? extends GisLayerController>	getLayerController(Class<? extends GisLayer> _layerClass) {
		return layerControllerClassRegister.get(_layerClass);
	}
	public final  Class<? extends GisLayerController>	getLayerController(GisLayer.Type _layerType) {
		return layerControllerClassRegister.get(_layerType);
	}
	public final  Class<? extends GisLayerController>	getLayerController(ID _layerId) {
		return layerControllerClassRegister.get(_layerId);
	}

	public final  AbstractGisLayerController 					newInstance(GisLayer _layer) {
		Class<? extends GisLayerController> 
		layerCtrlClass = getLayerController( _layer.getClass() );
		if(layerCtrlClass != null)
			return newInstance(_layer, layerCtrlClass);

		layerCtrlClass = getLayerController( _layer.getType() );
		if(layerCtrlClass != null)
			return newInstance(_layer, layerCtrlClass);

		layerCtrlClass = getLayerController( _layer.getId() );
		if(layerCtrlClass != null)
			return newInstance(_layer, layerCtrlClass);

		return null;
	}
	private final AbstractGisLayerController 					newInstance(GisLayer _layer, Class<? extends GisLayerController> _controllerClass) {
		try {
			Constructor<? extends GisLayerController> 	constructor = _controllerClass.getConstructor(GisLayer.class);
			AbstractGisLayerController                  instance    = (AbstractGisLayerController) constructor.newInstance(_layer);

			return instance;
		} catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
