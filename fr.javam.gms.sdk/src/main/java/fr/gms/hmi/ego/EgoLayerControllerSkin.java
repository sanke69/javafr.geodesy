package fr.gms.hmi.ego;

import java.util.Collection;

import fr.gms.api.ego.EgoVehicle;
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

public class EgoLayerControllerSkin implements Skin<EgoLayerController> {
	private EgoLayerController control;

	private TitledBorder				main;
	private VBox						layout;
	private ChoiceBox<EgoVehicle>		egoSelection;
	private CheckComboBox<EgoComponent>	egoComponentSelection;

	public EgoLayerControllerSkin(EgoLayerController _control) {
		super();
		control = _control;

		init();

		main    = new TitledBorder(_control.getLayer().getName(), layout);
	}

	@Override
	public EgoLayerController 				getSkinnable() {
		return control;
	}

	@Override
	public Node 							getNode() {
		return main;
	}

	private void 							init() {
		layout                = new VBox();
		egoSelection          = buildTrajectorySelector();
		egoComponentSelection = buildDisplayableDataSelector();

//		layout.getChildren().addAll(eHorizonSelection);
		layout.getChildren().addAll(egoSelection, egoComponentSelection);
//		layout.getChildren().addAll(eHorizonDataSelection);
	}
	@Override
	public void 							dispose() {
		
	}

	private ChoiceBox<EgoVehicle> 			buildTrajectorySelector() {
		egoSelection = new ChoiceBox<EgoVehicle>();

		Collection<EgoVehicle> eHorizons = getSkinnable().getLayer().getContent().getAllItems(EgoVehicle.class);
		
		egoSelection.setItems(FXCollections.observableArrayList( eHorizons ));
		egoSelection.setConverter(new StringConverter<EgoVehicle>() {
			@Override
			public String toString(EgoVehicle user) {
				if(user == null) {
					return null;
				} else {
					return user.toString();
				}
			}

			@Override
			public EgoVehicle fromString(String id) {
				return null;
			}
		});
		egoSelection.getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> {
			getSkinnable().getEgoMap().get(_new).set(true);
//			getSkinnable().getLayer().setActiveEHorizon(_new.getId());

			
//			getSkinnable().getMapControl().refresh();
		});
		egoSelection.getSelectionModel().selectFirst();
		egoSelection.prefWidthProperty().bind(((Region) getSkinnable().getParent()).widthProperty().add(-45.0));

		return egoSelection;
	}
	private CheckComboBox<EgoComponent> 	buildDisplayableDataSelector() {
		egoComponentSelection = new CheckComboBox<EgoComponent>(FXCollections.<EgoComponent>observableArrayList(EgoComponent.values()));
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

		egoComponentSelection.skinProperty().addListener(skinListener);
		// TODO:: Retrieve information from EHorizon to know which option is activated
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.HISTORY).set(true);
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.INFLUENCE_AREA).set(true);
//		displayDataSelection.getItemBooleanProperty(EHorizonComponent.FORWARD_ROAD_TREE).set(true);

		egoComponentSelection.prefWidthProperty().bind(((Region) getSkinnable().getParent()).widthProperty().add(-45.0));

		return egoComponentSelection;
	}

}
