package fr.gis.graphics.core.controller.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.gis.api.GisLayer;
import fr.gis.api.GisProvider;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.items.GisLayerController;
import fr.java.utils.Strings;
import fr.javafx.scene.control.selection.ComboBoxCustom;
import fr.javafx.scene.control.titledpane.TitledBorder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.MapChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.skin.TitledPaneSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class DefaultGisLayerTypeControllerSkin extends TitledPaneSkin {
	private static final double 		maxContainerHeight = 384d;

    BorderPane 							titlePane;
    Label      							titleLabel;
    Label      							titleLayerCount;
    CheckBox   							titleActivation;

    ScrollPane							contentScrollPane;
	VBox 								contentPane;

    TitledBorder						layerSelectorBorder;
	ComboBoxCustom<GisLayer>			layerSelectorComboBox;
	ListView<GisLayer>					layerSelectorDisplay;

	public DefaultGisLayerTypeControllerSkin(DefaultGisLayerTypeController skinnable, boolean _multiSelection) {
		super(skinnable);

		initTitle();
		initContainer();
		initLayerSelector(_multiSelection);

		contentPane . getChildren().add( layerSelectorBorder );

		skinnable   . setText( null );
		skinnable   . setGraphic( titlePane );
		skinnable   . setContent( contentScrollPane );

		initBindings();

		// Keep up to date the LAYER CONTROLLERS
		skinnable . layerControllersProperty() . addListener((MapChangeListener<GisLayer, GisLayerController>) l -> {
			System.out.println("udpdate...");
			if(l.wasAdded()) {
				GisLayerController control = l.getValueAdded();
				if( control instanceof Node )
					contentPane.getChildren().add( (Node) control );

			} else if(l.wasRemoved())
				contentPane.getChildren().remove( l.getValueRemoved() );
		});
	}

	public DefaultGisLayerTypeController 	getControl() {
		return (DefaultGisLayerTypeController) getSkinnable();
	}
	public GisService 				getService() {
		return ((DefaultGisLayerTypeController) getSkinnable()).getService();
	}

	@Override
	public void 					dispose() {
		
	}

	public void 					initTitle() {
		if(titlePane != null)
			throw new IllegalAccessError();

		titleLabel        = new Label( getControl().getType().getName() );
		titleLayerCount   = new Label();
		titleActivation   = new CheckBox();
		titlePane         = new BorderPane(null, 
										   null, 
										   new HBox(titleLayerCount, new Separator(Orientation.VERTICAL), titleActivation), 
										   null, 
										   titleLabel);
	}
	public void 					initContainer() {
		if(contentScrollPane != null)
			throw new IllegalAccessError();

		contentPane         = new VBox();
		contentScrollPane   = new ScrollPane(contentPane);
	}
	public void 					initLayerSelector(boolean _multiSelection) {
		layerSelectorComboBox = new ComboBoxCustom<GisLayer>(_multiSelection, 
															 stringConverter(), 
															 getControl().availableLayersProperty());
		layerSelectorDisplay  = new ListView<GisLayer>();
		layerSelectorBorder   = new TitledBorder("selection...", new VBox(layerSelectorComboBox, layerSelectorDisplay));

		class CustomListCell extends ListCell<GisLayer> {

			public CustomListCell() {
				super();
			}

			@Override
			public void updateItem(GisLayer item, boolean empty) {
				super.updateItem(item, empty);

				if(empty || item == null)
					setText(null);
				else
					setText(item.getName());
			}

		}

		layerSelectorDisplay.setCellFactory(lv -> new CustomListCell());

		final IntegerProperty height = new SimpleIntegerProperty(27);
		layerSelectorDisplay.prefHeightProperty().bindBidirectional(height);
		layerSelectorDisplay.minHeightProperty().bindBidirectional(height);
		layerSelectorDisplay.itemsProperty().addListener((_obs, _old, _new) -> {
		    double h = layerSelectorDisplay.getBoundsInLocal().getHeight();
		    height.setValue(h > 127 ? 127 : h);
		});

		DoubleBinding ctbw = contentPane.widthProperty()
										.subtract( layerSelectorBorder.leftMarginProperty().multiply(2d) )
										.subtract( layerSelectorBorder.rightMarginProperty().multiply(2d) )
										.subtract( 1d );

		layerSelectorComboBox . prefWidthProperty().bind( ctbw );
		layerSelectorDisplay  . prefWidthProperty().bind( ctbw );
	}

	public void 					initBindings() {
		titleLayerCount   . textProperty()      . bind( countBinding() );
		titleActivation   . selectedProperty()  . bindBidirectional(getControl().activationProperty());

		layerSelectorComboBox.setSelection(getControl().selectedLayersProperty());
		
		Bindings.bindContentBidirectional(getControl().selectedLayersProperty(), layerSelectorComboBox.listSelection());
        Bindings.bindContentBidirectional(getControl().selectedLayersProperty(), layerSelectorDisplay.itemsProperty().get());

        // UX Customization
		titlePane         . prefWidthProperty() . bind( getControl().widthProperty().subtract(42) );

		contentScrollPane . prefWidthProperty()  . bind( getControl().widthProperty() );
		contentScrollPane . setMaxHeight  (maxContainerHeight);
		contentScrollPane . setVbarPolicy(ScrollBarPolicy.NEVER);
		contentScrollPane . setHbarPolicy(ScrollBarPolicy.NEVER);

		contentPane       . prefWidthProperty() . bind( contentScrollPane.prefWidthProperty() );
	}


	private StringBinding 			countBinding() {
		return new StringBinding() {
			{ super.bind( getControl().availableLayersProperty().sizeProperty() ); }

			@Override
			protected String computeValue() {
				return String.valueOf("( " + getControl().availableLayersProperty().size() + " layers )");
			}

		};
	}

	final StringConverter<GisLayer> stringConverter() {
		return new StringConverter<GisLayer>() {
			Pattern	namePattern	       = Pattern.compile("(?<LAYER>\\w*)@(?<PROVIDER>\\w*)");
			String	nameFormat	       = "${LAYER}s@${PROVIDER}s";
	
			@Override
			public String toString(GisLayer _ml) 		{
				if( _ml == null )
					return "<empty>";
	
				Collection<GisProvider> providers = getService().getProviders();
				GisProvider             provider  = providers.stream().filter(p -> p.getLayers().contains(_ml)).findFirst().get();
	
				@SuppressWarnings("serial")
				HashMap<String, Object> nameData   = new HashMap<String, Object>() {{
					put("LAYER",    _ml.getName());
					put("PROVIDER", provider.getName());
				}};
		
				return Strings.format(nameFormat, nameData);
			}
	
			@Override
			public GisLayer fromString(String _name) 	{
				if( _name.compareTo("<empty>") == 0 )
					return null;
	
				String  nameProvider, nameLayer;
		
				Matcher fileMatcher = namePattern.matcher(_name);
				if(fileMatcher.find()) {
					nameLayer    = fileMatcher.group("LAYER");
					nameProvider = fileMatcher.group("PROVIDER");
				} else
					return null;
	
				GisProvider provider  = getService().getProviders()
												  .stream()
												  .filter(p -> p.getName().compareToIgnoreCase(nameProvider) == 0)
												  .findFirst().get();
				GisLayer    layer     = provider.getLayers()
												  .stream()
												  .filter(l -> l.getName().compareToIgnoreCase(nameLayer) == 0)
												  .findFirst().get();
	
				return layer;
			}
	
		};
	}

}
