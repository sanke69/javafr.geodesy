package fr.gis.graphics.control3d;

import fr.gis.viewer3d.shapes.GxEarthMap3D;
import fr.javafx.temp.properties.IntegerPropertyControl;
import fr.javafx.temp.properties.ScalarPropertyControl;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class GeodesicController extends VBox {

	GxEarthMap3D model;

	IntegerPropertyControl resolution;
	IntegerPropertyControl width, height;
	ScalarPropertyControl  lg, lt;
	
	public GeodesicController(GxEarthMap3D _model) {
		super();
		model = _model;

		VBox 		params_res = new VBox();
		VBox 		params_dim = new VBox();
		VBox 		params_pos = new VBox();
		
		TitledPane 	pane_res    = new TitledPane("Resolution", params_res);
		resolution  = new IntegerPropertyControl("Zoom Level [" + GxEarthMap3D.MIN_LEVEL + "-" + GxEarthMap3D.MAX_LEVEL + "]", GxEarthMap3D.MIN_LEVEL, GxEarthMap3D.MAX_LEVEL, model.getLevel(),  	(_obs, _old, _new) -> { model.setLevel(_new.intValue()); });
		params_res.getChildren().add(resolution);

		TitledPane 	pane_dim    = new TitledPane("Dimension", params_dim);
		width  = new IntegerPropertyControl("Width  [" + 1 + "-" + 10 + "]", 1, 10, model.getWidth(),  	(_obs, _old, _new) -> { model.setWidth(_new.intValue()); });
		height = new IntegerPropertyControl("Height [" + 1 + "-" + 10 + "]", 1, 10, model.getHeight(),  (_obs, _old, _new) -> { model.setHeight(_new.intValue()); });
		params_dim.getChildren().addAll(width, height);

		TitledPane 	pane_pos    = new TitledPane("Position", params_pos);
		lg  = new ScalarPropertyControl("LG  [" + GxEarthMap3D.MIN_LONGITUDE  + "-" + GxEarthMap3D.MAX_LONGITUDE + "]",  GxEarthMap3D.MIN_LONGITUDE,  GxEarthMap3D.MAX_LONGITUDE, model.getCenterLg(),  	(_obs, _old, _new) -> { model.setCenterLg(_new.intValue()); });
		lt = new ScalarPropertyControl("LT [" + GxEarthMap3D.MIN_LATITUDE + "-" + GxEarthMap3D.MAX_LATITUDE + "]", GxEarthMap3D.MIN_LATITUDE, GxEarthMap3D.MAX_LATITUDE, model.getCenterLt(),  (_obs, _old, _new) -> { model.setCenterLt(_new.intValue()); });
		params_pos.getChildren().addAll(lg, lt);
		

		getChildren().addAll(
				pane_res,
				pane_dim,
				pane_pos
				);
	}
	
}
