package fr.gis.api;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.capabilities.Nameable;

public interface GisLayer extends Identifiable, Nameable {
	public static final Comparator<? super GisLayer> comparator = (i, j) -> i.getName().compareToIgnoreCase(j.getName());

	public enum Standard implements GisLayer.Type {
		BASE				(StandardGisLayerType.BASE),

		RELIEFS				(StandardGisLayerType.RELIEFS),
		BUILDINGS			(StandardGisLayerType.BUILDINGS),

		MOBILES				(StandardGisLayerType.MOBILES),

		ROAD_GEOMETRY		(StandardGisLayerType.ROAD_GEOMETRY),
		ROAD_TOPOLOGY		(StandardGisLayerType.ROAD_TOPOLOGY),
		ROAD_METADATA		(StandardGisLayerType.ROAD_METADATA),
		ROAD_TRAFICSIGNS	(StandardGisLayerType.ROAD_TRAFICSIGNS);

		GisLayer.Type delegated;

		Standard(GisLayer.Type _delegated) { delegated = _delegated; }

		@Override public String 	getName()       { return delegated.getName(); }
		@Override public int 		getIndex()      { return delegated.getIndex(); }

		@Override public boolean 	isRenderable()  { return delegated.isRenderable(); }
		@Override public boolean 	isUserDefined() { return delegated.isUserDefined(); }

	}
	
	public static final Type BASE             = StandardGisLayerType.BASE;

	public static final Type RELIEFS          = StandardGisLayerType.RELIEFS;
	public static final Type BUILDINGS        = StandardGisLayerType.BUILDINGS;

	public static final Type MOBILES          = StandardGisLayerType.MOBILES;

	public static final Type ROAD_GEOMETRY    = StandardGisLayerType.ROAD_GEOMETRY;
	public static final Type ROAD_TOPOLOGY    = StandardGisLayerType.ROAD_TOPOLOGY;
	public static final Type ROAD_METADATA    = StandardGisLayerType.ROAD_METADATA;
	public static final Type ROAD_TRAFICSIGNS = StandardGisLayerType.ROAD_TRAFICSIGNS;

	public static interface Type {
		public static final Comparator<? super Type> comparator = (i, j) -> i.getIndex() - j.getIndex();

		public String 							getName();
		public int 								getIndex();
//		public Set<Class<? extends MapObject>> 	getElementClasses();

//		public boolean 							allowMultiLayers();
		public boolean 							isRenderable();
		public boolean 							isUserDefined();

		public default boolean 					hasTileProvider() { return false; }

	}

	public interface Content {

		public Collection<ID> 						getAllIDs();

		public Collection<Gis.Object>  				getAllItems();
		public <E extends Gis.Object> Collection<E> 	getAllItems(Class<E> _elt);

		public Gis.Object        					get(ID _id);
		public <E extends Gis.Object> E				get(ID _id, Class<E> _cast) throws IllegalCastException;

		public Map<ID, Gis.Object> 					asMap();
		public <E extends Gis.Object> Map<ID, E> 	asMap(Class<E> _elt);

	}
	public interface ContentEditable extends Content {

		public void 								clear();

		public void 								add(Gis.Object _item);
		public void 								addAll(Gis.Object... _items);
		public void 								addAll(Collection<? extends Gis.Object> _items);

		public void 								remove(Gis.Object _item);
		public void 								removeAll(Gis.Object... _items);
		public void 								removeAll(Collection<? extends Gis.Object> _items);

	}

	public Type 					getType();

	public Content 					getContent();
	public default ContentEditable 	getContentEditable() { return null; }

	public default GisTileProvider 	getTileProvider() { return null; }

}
