package fr.gis.sdk.layers;

import java.util.Collection;
import java.util.Map;

import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public class EmptyMapLayer extends AbstractMapLayer implements GisLayer {

	final Content       	contentView = new Content() {

		@Override
		public Gis.Object              				get(ID _id) { return null; }
		@Override
		public <T extends Gis.Object> T				get(ID _id, Class<T> _cast) throws IllegalCastException { return null; }

		@Override
		public Collection<Gis.Object>  				getAllItems() { return null; }
		@Override
		public <E extends Gis.Object> Collection<E> getAllItems(Class<E> _elt) { return null; }

		@Override
		public Collection<ID> 						getAllIDs() { return null; }

		@Override
		public Map<ID, Gis.Object> 					asMap() { return null; }

		@Override
		public <E extends Gis.Object> Map<ID, E> 	asMap(Class<E> _elt) { return null; }

	};

	public EmptyMapLayer(GisLayer.Type _type) {
		super(_type);
	}
	public EmptyMapLayer(String _name, GisLayer.Type _type) {
		super(_name, _type);
	}

	@Override
	public Content 				getContent() {
		return contentView;
	}
	@Override
	public ContentEditable 		getContentEditable() {
		throw new IllegalAccessError("EmptyLayer can't be edited");
	}

}
