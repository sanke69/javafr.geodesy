package fr.gis.graphics.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.GisServiceRenderer;
import fr.gis.graphics.api.render.items.GisLayerRenderer;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.java.lang.properties.ID;

public abstract class GisRendererFactory<MR extends GisServiceRenderer<MR>> {
	protected final Map<Object, GisObjectRenderer<MR>> objectRenderers;
	protected final Map<Object, GisLayerRenderer<MR>>  layerRenderers;

	public GisRendererFactory() {
		super();
		objectRenderers = new HashMap<Object, GisObjectRenderer<MR>>();
		layerRenderers  = new HashMap<Object, GisLayerRenderer<MR>>();
	}

	public final void 					registerObjectRenderer(Class<? extends Gis.Object> _mapObjectClass, Class<? extends GisObjectRenderer<MR>> _mapObjectDrawerClass) {
		objectRenderers.put(_mapObjectClass, newInstanceObject(_mapObjectDrawerClass));
	}

	public final void 					registerLayerRenderer(Class<? extends GisLayer>   _mapLayerClass,  Class<? extends GisLayerRenderer<MR>> _mapLayerDrawerClass) {
		layerRenderers.put(_mapLayerClass, newInstanceLayer(_mapLayerDrawerClass));
	}
	public final void 					registerLayerRenderer(GisLayer.Type               _mapLayerType,   Class<? extends GisLayerRenderer<MR>> _mapLayerDrawerClass) {
		layerRenderers.put(_mapLayerType, newInstanceLayer(_mapLayerDrawerClass));
	}
	public final void 					registerLayerRenderer(ID                          _mapLayerId,     Class<? extends GisLayerRenderer<MR>> _mapLayerDrawerClass) {
		layerRenderers.put(_mapLayerId, newInstanceLayer(_mapLayerDrawerClass));
	}

	@SuppressWarnings("unchecked") // TODO:: sealed class
	public final GisObjectRenderer<MR> 	getObjectRenderer(Class<? extends Gis.Object> _mapObjectClass) {
		Class<? extends Gis.Object> current  = _mapObjectClass;
		GisObjectRenderer<MR>       renderer = null;

		renderer = objectRenderers.get(current);

		if(current == null)
			return null;

		if(renderer != null)
			return renderer;

		Collection<Class<?>> avoidClasses = Arrays.asList(Gis.Object.class, /*Gis.Node.class,*/ Gis.Curve.class, Gis.Area.class);

		for(Class<?> interfClass : current.getInterfaces()) {
			if(interfClass == null || avoidClasses.contains(interfClass))
				break;

			renderer = getObjectRenderer((Class<? extends Gis.Object>) interfClass);

			if(renderer != null)
				return renderer;
		}

		Class<?> superClass = current.getSuperclass();
		while( superClass != null && Gis.Object.class.isAssignableFrom( superClass ) ) {
			renderer = getObjectRenderer((Class<? extends Gis.Object>) superClass);

			if(renderer != null)
				return renderer;
			
			superClass = superClass.getSuperclass();
		}

		return null;
	}

	public final GisLayerRenderer<MR> 	getLayerRenderer(GisLayer _layer) {
		GisLayerRenderer<MR> lrenderer;

		if( (lrenderer =  getLayerRenderer(_layer.getId())) != null) {
			return lrenderer;
			
		} else if( (lrenderer =  getLayerRenderer(_layer.getClass())) != null) {
			return lrenderer;
			
		} else if( (lrenderer =  getLayerRenderer(_layer.getType())) != null) {
			return lrenderer;
			
		}

		return null;
	}

	public final GisLayerRenderer<MR> 	getLayerRenderer(Class<? extends GisLayer> _mapLayerClass) {
		return layerRenderers.get(_mapLayerClass);
	}
	public final GisLayerRenderer<MR> 	getLayerRenderer(GisLayer.Type _mapEltType) {
		return layerRenderers.get(_mapEltType);
	}
	public final GisLayerRenderer<MR> 	getLayerRenderer(ID _mapEltId) {
		return layerRenderers.get(_mapEltId);
	}

	private final GisObjectRenderer<MR> newInstanceObject(Class<? extends GisObjectRenderer<MR>> _rendererClass) {
		try {
			Constructor<? extends GisObjectRenderer<MR>> constructor = _rendererClass.getConstructor();
			GisObjectRenderer<MR>               		 instance    = (GisObjectRenderer<MR>) constructor.newInstance();

			return instance;
		} catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	private final GisLayerRenderer<MR>  newInstanceLayer(Class<? extends GisLayerRenderer<MR>> _rendererClass) {
		try {
			Constructor<? extends GisLayerRenderer<MR>> constructor = _rendererClass.getConstructor();
			GisLayerRenderer<MR>               			instance    = (GisLayerRenderer<MR>) constructor.newInstance();

			return instance;
		} catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
