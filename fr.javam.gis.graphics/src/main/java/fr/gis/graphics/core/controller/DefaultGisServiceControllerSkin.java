package fr.gis.graphics.core.controller;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.graphics.core.controller.type.DefaultGisLayerTypeController;
import fr.javafx.scene.control.selection.ComboBoxCustom;
import javafx.collections.MapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class DefaultGisServiceControllerSkin implements Skin<DefaultGisServiceController> {
	private final DefaultGisServiceController 	skinnable;

	private ComboBoxCustom<GisProvider> providerSelector;

	private VBox 						vbox;
	private ScrollPane 				 	scroll;
	private Accordion  				 	accordion;

	public DefaultGisServiceControllerSkin(DefaultGisServiceController _skinnable) {
		super();
		skinnable = _skinnable;

		accordion = new Accordion();
		accordion . prefWidthProperty().bind(_skinnable.widthProperty());

		providerSelector = new ComboBoxCustom<GisProvider>( true, 
															new StringConverter<GisProvider>() {
																@Override public String      toString(GisProvider _provider) 	{ return _provider != null ? _provider.getName() : "<empty>"; }
																@Override public GisProvider fromString(String _name)  			{ return getSkinnable().availableProvidersProperty().stream().filter(ml -> ml.getName().compareToIgnoreCase(_name) == 0).findFirst().orElse(null); }
															}, 
														    getSkinnable().availableProvidersProperty());

		scroll = new ScrollPane(accordion);
		scroll . setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll . setHbarPolicy(ScrollBarPolicy.NEVER);

		vbox   = new VBox(providerSelector, scroll);

		// Keep up to date the TYPE CONTROLLERS
		skinnable . typeControllersProperty() . addListener((MapChangeListener<GisLayer.Type, DefaultGisLayerTypeController>) l -> {
			if(l.wasAdded()) {
				boolean added = false;
				int     index = l.getKey().getIndex();
				
				for(int i = 0; i < accordion.getPanes().size() && !added; ++i) {
					Node n = accordion.getPanes().get(i);

					if( n instanceof DefaultGisLayerTypeController) {
						int next_index = ((DefaultGisLayerTypeController) n).getType().getIndex();
						
						if(index < next_index) {
							accordion.getPanes().add( i, l.getValueAdded() );
							added = true;
						}
					}

				}
				
				if(!added)
					accordion.getPanes().add( l.getValueAdded() );

			} else if(l.wasRemoved())
				accordion.getPanes().remove( l.getValueRemoved() );
		});

		// Create the PROVIDER SELECTION BINDING
		getSkinnable().activeProvidersProperty().bind( providerSelector.listSelection() );
		providerSelector . setSelection(getSkinnable().availableProvidersProperty());

	}

	@Override
	public DefaultGisServiceController getSkinnable() {
		return skinnable;
	}

	@Override
	public Node 				getNode() {
		return vbox;
	}

	@Override
	public void 				dispose() {
		;
	}

}
