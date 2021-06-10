package fr.gis.graphics.core.hud;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MapInfoControlVisual extends VBox implements Skin<MapInfoControl> {
	final private MapInfoControl control;

	private Label CursorInfo;
	private Label CursorCoordinate;

	private Label WebMercatorViewportInfo;
	private Label WebMercatorViewport;

	private Label CursorGeoInfo;
	private Label CursorGeoCoordinate;

	public MapInfoControlVisual(MapInfoControl _control) {
		control = _control;

		setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
		setMaxWidth(320);
		setMaxHeight(240);

		CursorInfo = new Label("Mouse: ");
		CursorCoordinate = new Label("(???, ???)");

		WebMercatorViewportInfo = new Label("WebMercatorViewport: ");
		WebMercatorViewport = new Label("(Cx:???, Cy:???, W:???, H:???, L:???)");

		CursorGeoInfo = new Label("Coordinate: ");
		CursorGeoCoordinate = new Label("(???, ???)");

		CursorInfo.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");
		CursorCoordinate.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");

		WebMercatorViewportInfo.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");
		WebMercatorViewport.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");

		CursorGeoInfo.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");
		CursorGeoCoordinate.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 12 \"serif\"; -fx-padding: 0 0 0 0; -fx-text-alignment: center");

		maxHeightProperty().bind(
				CursorInfo.heightProperty()
				.add(CursorCoordinate.prefHeightProperty())
				.add(WebMercatorViewportInfo.heightProperty())
				.add(WebMercatorViewport.heightProperty())
				.add(CursorGeoInfo.heightProperty())
				.add(CursorGeoCoordinate.prefHeightProperty()));

		VBox vInfos = new VBox();
		vInfos.setSpacing(10);
		vInfos.setPadding(new Insets(0, 20, 10, 20));
//		vInfos.getChildren().addAll(CursorInfo, CursorCoordinate, WebMercatorViewportInfo, WebMercatorViewport, CursorGeoInfo, CursorGeoCoordinate);
		vInfos.getChildren().addAll(CursorInfo, CursorCoordinate, CursorGeoInfo, CursorGeoCoordinate);
		getChildren().addAll(vInfos);

		_control.longitudeProperty().addListener((_obs, _old, _new) -> update());
		_control.latitudeProperty().addListener((_obs, _old, _new) -> update());
		
//		_control.getMapDisplay().setOnMouseMoved(this::handleMouseMovement);
	}

	public void handleMouseMovement(MouseEvent _mouse) {
		DecimalFormat fmt = new DecimalFormat("00.##");
		CursorCoordinate.setText("(" + fmt.format(_mouse.getX()) + ", " + fmt.format(_mouse.getY()) + ")");

		int    lg  = (int) control.getLongitude();
		int    mLg = (int) ((control.getLongitude() - lg) * 60.0);
		double sLg = (control.getLongitude() - lg - (double)mLg/60.0) * 3600.0;
		mLg = Math.abs(mLg);
		sLg = Math.abs(sLg);

		int    lt  = (int) control.getLatitude();
		int    mLt = (int) ((control.getLatitude() - lt) * 60.0);
		double sLt = Math.abs((control.getLatitude() - lt - (double)mLt/60.0) * 3600.0);
		mLt = Math.abs(mLt);
		sLt = Math.abs(sLt);

		CursorGeoCoordinate.setText("(" + lg + "°" + mLg + "'" + fmt.format(sLg) + "\"" + ", " + lt + "°" + mLt + "'" + fmt.format(sLt) + "\"" + ")");
	}

	@Override
	public MapInfoControl getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		;
	}

	private void update() {
		double lg = control.getLongitude();
		double lt = control.getLatitude();

		DecimalFormat fmt = new DecimalFormat("0000.##");
		CursorCoordinate.setText("Lg= " + fmt.format(lg) + "  " + "Lt= " + fmt.format(lt));

//		WebMercator.Viewport vp = control.getMapDisplay().getViewportBounds();
//		WebMercatorViewport.setText("(Cx:" + "?" /*fmt.format(vp.getCenterX())*/ + ", Cy:" + "?" /*fmt.format(vp.getCenterY())*/ + ", W:" + vp.getWidth() + ", H:" + vp.getHeight() + ", L:" + vp.getLevel() + ")");
	}
}
