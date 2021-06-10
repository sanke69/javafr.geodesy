package fr.gms.hmi.ego;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.graphics.sdk.control.AbstractGisLayerController;
import fr.gms.api.ego.EgoVehicle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Skin;

public class EgoLayerController extends AbstractGisLayerController {
	private MapProperty<EgoVehicle, BooleanProperty> egoActivationMap;

	public EgoLayerController(GisLayer _layer) {
		super(_layer);

		egoActivationMap = new SimpleMapProperty<EgoVehicle, BooleanProperty>(FXCollections.observableHashMap());

		for(EgoVehicle egoVehicle : getLayer().getContent().asMap(EgoVehicle.class).values()) {
			BooleanProperty egoVehicleActivation = new SimpleBooleanProperty(true);

			egoActivationMap.get().put(egoVehicle, egoVehicleActivation);
		}
	}

	@Override
	protected Skin<?> 								createDefaultSkin() {
		return new EgoLayerControllerSkin(this);
	}

	@Override
	public GisRendererOption 						getRenderOptions() {
		return null;
	}

	public MapProperty<EgoVehicle, BooleanProperty> getEgoMap() {
		return egoActivationMap;
	}

	public Collection<EgoVehicle> 					getActiveEgoVehicles() {
		return getEgoMap().entrySet()
							   .stream()
							   .filter(e -> e.getValue().get())
							   .map(e -> e.getKey())
							   .collect(Collectors.toList());
	}

	

}
