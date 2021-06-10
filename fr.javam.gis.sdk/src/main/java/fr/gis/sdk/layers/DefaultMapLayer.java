package fr.gis.sdk.layers;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.api.GisLayer;
import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public class DefaultMapLayer extends AbstractMapLayer implements GisLayer {

	final Map<ID, Gis.Object> 	cache;

	final ContentEditable       contentView = new ContentEditable() {

		@Override
		public Gis.Object              				get(ID _id) {
			return cache.get(_id);
		}
		@Override
		@SuppressWarnings("unchecked")
		public <T extends Gis.Object> T				get(ID _id, Class<T> _cast) throws IllegalCastException {
			Gis.Object candidate = cache.get(_id);

			if(candidate == null)
				return null;

			if(!_cast.isAssignableFrom(candidate.getClass()))
				throw new IllegalCastException();

			return (T) candidate;
		}

		@Override
		public Collection<Gis.Object>  				getAllItems() {
			return cache.values();
		}
		@Override
		@SuppressWarnings("unchecked")
		public <E extends Gis.Object> Collection<E> 	getAllItems(Class<E> _elt) {
			return (Collection<E>) cache.values().stream()
												 .filter(elt -> _elt.isAssignableFrom(elt.getClass()))
												 .map(elt -> (E) elt)
												 .collect(Collectors.toList());
		}

		@Override
		public Collection<ID> 						getAllIDs() {
			return cache.keySet();
		}

		@Override
		public Map<ID, Gis.Object> 					asMap() {
			return cache;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <E extends Gis.Object> Map<ID, E> 	asMap(Class<E> _elt) {
			return asMap().entrySet()   .stream()
								    	.filter(e -> _elt.isAssignableFrom(e.getValue().getClass()))
								        .collect(Collectors.toMap(
								            e -> e.getKey(),
								            e -> (E) e.getValue()
								        ));

		}

		@Override
		public void 								clear() {
			cache.clear();
		}

		@Override
		public void 								add(Gis.Object _item) {
			if(cache.containsKey(_item.getId()))
				System.err.println("[WARNING] An item with same ID previously existing...");
			cache.put(_item.getId(), _item);
		}
		@Override
		public void 								addAll(Gis.Object... _items) {
			for(Gis.Object item : _items)
				add(item);
		}
		@Override
		public void 								addAll(Collection<? extends Gis.Object> _items) {
			for(Gis.Object item : _items)
				add(item);
		}

		@Override
		public void 								remove(Object _item) {
			cache.remove(_item.getId());
		}
		@Override
		public void 								removeAll(Object... _items) {
			for(Gis.Object item : _items)
				cache.remove(item.getId());
		}
		@Override
		public void 								removeAll(Collection<? extends Object> _items) {
			for(Gis.Object item : _items)
				cache.remove(item.getId());
		}

	};

	public DefaultMapLayer(GisLayer.Type _type) {
		super(_type);
		cache = new ConcurrentHashMap<ID, Gis.Object>();
	}
	public DefaultMapLayer(String _name, GisLayer.Type _type) {
		super(_name, _type);
		cache = new ConcurrentHashMap<ID, Gis.Object>();
	}
	public DefaultMapLayer(String _name, GisLayer.Type _type, Collection<Gis.Object> _elements) {
		super(_name, _type);
		cache = new ConcurrentHashMap<ID, Gis.Object>( _elements.stream().collect(Collectors.toMap(Gis.Object::getId, Function.identity())) );
	}

	@Override
	public Content 				getContent() {
		return contentView;
	}
	@Override
	public ContentEditable 		getContentEditable() {
		return contentView;
	}

	@Override
	public String 				toString() {
		return getName();
	}

}
