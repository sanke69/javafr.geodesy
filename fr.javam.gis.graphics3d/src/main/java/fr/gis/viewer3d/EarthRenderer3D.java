package fr.gis.viewer3d;

import fr.gis.api.GisService;
import fr.gis.graphics.control3d.GeodesicController;
import fr.gis.viewer3d.shapes.GxEarthMap3D;
import fr.java.math.geometry.space.shapes.Ellipsoid3D;
import fr.java.maths.Points;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.Geometry;
import fr.java.maths.geometry.Space;
import fr.java.maths.geometry.space.camera.Projections3D;
import fr.java.maths.geometry.space.types.SimpleDimension3D;
import fr.javafx.controller.space.GxCamera3DController;
import fr.javafx.controller.space.behaviors.FreeFlightController;
import fr.javafx.rendering.GxViewportControl;
import fr.javafx.stage.StageExt;
import fr.threedijnns.gx;
import fr.threedijnns.api.interfaces.nodes.space.GxCamera3D;
import fr.threedijnns.engine.renderers.Rasterer;
import fr.threedijnns.gl.JOGL;
import fr.threedijnns.objects.plane.Scene2D;
import fr.threedijnns.objects.space.Scene3D;
import fr.threedijnns.objects.space.camera.GxDefaultCamera3D;
import fr.threedijnns.objects.space.wrapper.GxFrame3D;
import fr.threedijnns.utils.HeightMap;
import javafx.scene.control.Control;

public class EarthRenderer3D extends GxViewportControl {

	static {
		gx.initialize(JOGL.instance(JOGL.GL2ES1));
	}

	private static final double K = 1e-3; 							// a scale factor
	private static final double EARTH_RADIUS = K * 6_371_000, 		// in meters
								MAP_EPSILON  = K * 50, 				// in meters
								CAMERA_DIST  = K * 1_500_000; 		// in meters

	GisService				mapService;

	Scene2D 				scene2D;

	GxCamera3D 				camera;
	Scene3D 				scene3D;
//	GxEllipsoid3D 				earth;
	GxEarthMap3D       				map;

	GxCamera3DController	controller;

	public EarthRenderer3D(GisService _mapProvider) {
		this(_mapProvider, 1920, 1080);
	}
	public EarthRenderer3D(GisService _mapProvider, int _resWith, int _resHeight) {
		super(new Rasterer(), _resWith, _resHeight);
		setStyle("-fx-background-color: red;");
		
		mapService = _mapProvider;

		initializeScenes();

		initializeCamera();
		initializeVirtualWorld();

		initializeController();

		initializeAnimation();
	}
	
	public Control 				getControl() {
		return this;
	}

	public Scene2D 				getScene2D() {
		if(scene2D == null) {
			scene2D = new Scene2D(1920, 1080);
			scene2D.setBackgroundColor(0xFFFFFFFF);
		}
		return scene2D;
	}
	public Scene3D 				getScene3D() {
		if(scene3D == null) {
			scene3D = new Scene3D();
			scene3D.setCamera(getCamera3D());
			scene3D.addRenderable(new GxFrame3D(Geometry.Space.worldFrame));
		}
		return scene3D;
	}
	public GxCamera3D 			getCamera3D() {
		if(camera == null)
			camera = new GxDefaultCamera3D(Projections3D.perspective(90, 16.0f/9.0f, 0.5f, (float) 1e9));
		return camera;
	}


	public void initializeScenes() {
//		getRenderer().setScene2D( getScene2D() );
		getRenderer().setScene3D( getScene3D() );

	}

	private void initializeCamera() {
		getCamera3D().lookAt(Points.of(EARTH_RADIUS + CAMERA_DIST, 0, 0), Points.of(0, 0, 0), Vectors.of(0, 0, 1));
	}
	private void initializeController() {
		controller = new FreeFlightController(getControl(), getCamera3D()); // ObjectCenterController - FreeFlightController
	}

	private void initializeVirtualWorld() {
//		map   = new GxEarthMap3D(mapEllipsoid, mapService);

		initializeEarthView();
		initializeMap3D();
	}
	private void initializeEarthView() {
/** /
		Path bathymetry = Paths.get("/media/sanke/share.ssd/res/projects/VirtualUniverse/resources/universe/milky-way/sun/Earth/EarthBathymetryMap.png");
		Path altimetry  = Paths.get("/media/sanke/share.ssd/res/projects/VirtualUniverse/resources/universe/milky-way/sun/Earth/EarthAltimetryMap.png");
		
		HeightMap hMap = new HeightMap();
		try {
			hMap.loadImages(altimetry, bathymetry);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		hMap.setMaxima(-10_994 * 1e-2, 8_848 * 1e-2);
/*/
		HeightMap hMap = null;
/**/

		Ellipsoid3D earthEllipsoid = Space.newEllipsoid(Points.of(0,0,0), SimpleDimension3D.of(EARTH_RADIUS, EARTH_RADIUS, EARTH_RADIUS));
		Ellipsoid3D mapEllipsoid   = Space.newEllipsoid(Points.of(0,0,0), SimpleDimension3D.of(EARTH_RADIUS + MAP_EPSILON, EARTH_RADIUS + MAP_EPSILON, EARTH_RADIUS + MAP_EPSILON));
		
//		earth = new GxEllipsoid3D(earthEllipsoid, 128, 256, hMap);
		map   = new GxEarthMap3D(mapEllipsoid, 50, mapService);
		
//		earth.getChildren().add(map);
/*
		gx.runLater( () -> {
			BufferedImage earthTexture;
			try {
				earthTexture = ImageIO.read(earthTextureFile.toFile());

//				sphere.setPolygonMode(PolygonMode.OnlySkeleton);
				earth.enableTexture(true);
				earth.getTexture().update(earthTexture, null, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}); // TODO:: No need of gx.runLater in a functional use case !!!
*/

		getScene3D().addRenderable(map);

	}
	private void initializeMap3D() {
		StageExt.create(new GeodesicController(map));
		map.animate();
	}

	private void initializeAnimation() {
		/**/
		new Thread(() -> {
			while(true) {
		//		earth.getFrame().rotateAxes(0.0, 0.01, 0.0);
				try { Thread.sleep(25); } catch (InterruptedException _e) { }
			}
		}).start();
		/**/
	}
}
