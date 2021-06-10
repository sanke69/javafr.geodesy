package fr.gms.hmi.ehorizon;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.graphics.sdk.control.AbstractGisLayerController;
import fr.gms.api.sensor.EHorizon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Skin;

public class EHorizonLayerController extends AbstractGisLayerController {
	private MapProperty<EHorizon, BooleanProperty> eHorizons;

	public EHorizonLayerController(GisLayer _layer) {
		super(_layer);

		eHorizons = new SimpleMapProperty<EHorizon, BooleanProperty>(FXCollections.observableHashMap());

		for(Object eHorizon : getLayer().getContent().asMap(EHorizon.class).values()) {
			EHorizon eHorizonElt = (EHorizon) eHorizon;

			BooleanProperty eHorizonEltActivated = new SimpleBooleanProperty(true);
//			eHorizonEltActivated.addListener((_obs, _old, _new) -> getMapControl().refresh());

			eHorizons.get().put(eHorizonElt, eHorizonEltActivated);
		}
	}

	@Override
	protected Skin<?> 								createDefaultSkin() {
		return new EHorizonLayerControllerSkin(this);
	}

	@Override
	public GisRendererOption 						getRenderOptions() {
		return null;
	}

	public MapProperty<EHorizon, BooleanProperty> 	getEHorizonMap() {
		return eHorizons;
	}

	public Collection<EHorizon> 					getEHorizonActiveList() {
		return getEHorizonMap().get()
							   .entrySet()
							   		.stream()
							   		.filter(e -> e.getValue().get())
							   		.map(e -> e.getKey())
							   		.collect(Collectors.toList());
	}

}
