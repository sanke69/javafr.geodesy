package fr.gis.graphics.sdk.control;

import fr.gis.api.GisLayer;
import fr.gis.graphics.api.control.items.GisLayerController;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

public abstract class AbstractGisLayerController extends Control implements GisLayerController {
	private final GisLayer   layer;

	public AbstractGisLayerController(GisLayer _layer) {
		super();
		layer = _layer;
	}

	public GisLayer getLayer() {
		return layer;
	}

	@Override
	protected Skin<?> 	createDefaultSkin() {
		return new Skin<AbstractGisLayerController>() {

			@Override
			public AbstractGisLayerController getSkinnable() {
				return AbstractGisLayerController.this;
			}

			@Override
			public Node getNode() {
				return new Label(layer.getName());
			}

			@Override
			public void dispose() {
				
			}};
	}

}
