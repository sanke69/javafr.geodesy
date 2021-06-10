package fr.gms.hmi.ehorizon;

import java.util.Collection;

import fr.gms.api.sensor.EHorizon;
import fr.gms.map.objects.sensor.EHorizonAdapter;
import fr.javafx.scene.control.checkcombobox.CheckComboBox;
import fr.javafx.scene.control.titledpane.TitledBorder;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class EHorizonLayerControllerSkin implements Skin<EHorizonLayerController> {
	private EHorizonLayerController 			control;

	private TitledBorder						main;
	private VBox								layout;

	private ChoiceBox<EHorizon>					eHorizonSelection;
	private CheckComboBox<EHorizonComponent>	eHorizonDataSelection;

	public EHorizonLayerControllerSkin(EHorizonLayerController _control) {
		super();
		control = _control;

		init();
		main    = new TitledBorder(_control.getLayer().getName(), layout);
	}

	@Override
	public EHorizonLayerController 				getSkinnable() {
		return control;
	}

	@Override
	public Node 								getNode() {
		return main;
	}

	private void 								init() {
		layout                = new VBox();
		eHorizonSelection     = buildTrajectorySelector();
		eHorizonDataSelection = buildDisplayableDataSelector();

//		layout.getChildren().addAll(eHorizonSelection);
		layout.getChildren().addAll(eHorizonSelection, eHorizonDataSelection);
//		layout.getChildren().addAll(eHorizonDataSelection);
	}
	@Override
	public void 								dispose() {
		// nothing to do
	}

	private ChoiceBox<EHorizon> 				buildTrajectorySelector() {
		eHorizonSelection = new ChoiceBox<EHorizon>();

		Collection<EHorizon> eHorizons = getSkinnable().getLayer().getContent().getAllItems(EHorizon.class);
		
		eHorizonSelection.setItems(FXCollections.observableArrayList( eHorizons ));
		eHorizonSelection.setConverter(new StringConverter<EHorizon>() {
			@Override
			public String toString(EHorizon user) {
				if(user == null) {
					return null;
				} else {
					return user.toString();
				}
			}

			@Override
			public EHorizonAdapter fromString(String id) {
				return null;
			}
		});
		eHorizonSelection.getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> {
//			getSkinnable().getLayer().setActiveEHorizon(_new.getId());

			
//			getSkinnable().getMapControl().refresh();
		});
		eHorizonSelection.getSelectionModel().selectFirst();
		eHorizonSelection.prefWidthProperty().bind(((Region) getSkinnable().getParent()).widthProperty().add(-45.0));

		return eHorizonSelection;
	}
	private CheckComboBox<EHorizonComponent> 	buildDisplayableDataSelector() {
		eHorizonDataSelection = new CheckComboBox<EHorizonComponent>(FXCollections.<EHorizonComponent>observableArrayList(EHorizonComponent.values()));
/*
		eHorizonDataSelection.setConverter(new StringConverter<EHorizonComponent>() {
			@Override
			public String toString(EHorizonComponent user) {
				if(user == null) {
					return null;
				} else {
					return user.label();
				}
			}

			@Override
			public EHorizonComponent fromString(String id) {
				return null;
			}
		});
*/
		ChangeListener<Skin<?>> skinListener = (skinObs, oldVal, newVal) -> {
		    if(oldVal == null && newVal != null) {/*
		        ComboBox<EHorizonComponent> combo = (ComboBox<EHorizonComponent>) ((CheckComboBoxSkin<EHorizonComponent>) newVal).getChildren().get(0);
		        combo.showingProperty().addListener((obs, hidden, showing) -> {
		            if(hidden) {
						List<EHorizonComponent> displayed = new ArrayList<EHorizonComponent>();
						for(int i = 0; i < eHorizonDataSelection.getItems().size(); ++i) {
							if(eHorizonDataSelection.getItemBooleanProperty(i).get())
								displayed.add(eHorizonDataSelection.getItems().get(i));
						}
						eHorizonSelection.getSelectionModel().selectedItemProperty().get().setDisplayedComponent(displayed);
					}
		        });*/
		    }
		};

		eHorizonDataSelection.skinProperty().addListener(skinListener);
		// TODO:: Retrieve information from EHorizon to know which option is activated
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.HISTORY).set(true);
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.INFLUENCE_AREA).set(true);
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.FORWARD_ROAD_TREE).set(true);

		eHorizonDataSelection.prefWidthProperty().bind(((Region) getSkinnable().getParent()).widthProperty().add(-45.0));

		return eHorizonDataSelection;
	}

}
