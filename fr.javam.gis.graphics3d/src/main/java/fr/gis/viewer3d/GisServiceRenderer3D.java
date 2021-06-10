package fr.gis.viewer3d;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.mercator.Mercator;
import fr.gis.api.Gis;
import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.graphics.api.control.GisServiceController;
import fr.gis.graphics.api.render.GisServiceRenderer;
import fr.gis.graphics.api.render.items.GisLayerRenderer;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.graphics.core.GisRendererFactory;
import fr.gis.graphics.core.controller.DefaultGisServiceController;
import fr.gis.mercator.GudermannProjector;
import fr.gis.mercator.MercatorTileSystem;
import fr.java.lang.enums.PixelFormat;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.plane.Viewport2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.algebra.vectors.DoubleVector3D;
import fr.java.maths.geometry.space.camera.Projections3D;
import fr.javafx.controller.space.behaviors.FreeFlightController;
import fr.javafx.rendering.GxViewportControl;
import fr.media.image.utils.BufferedImages;
import fr.threedijnns.gx;
import fr.threedijnns.api.attributes.Texture2D;
import fr.threedijnns.api.interfaces.nodes.space.GxCamera3D;
import fr.threedijnns.api.lang.stream.handlers.StreamHandler2D;
import fr.threedijnns.engine.renderers.GxRenderer;
import fr.threedijnns.engine.renderers.Rasterer;
import fr.threedijnns.gl.JOGL;
import fr.threedijnns.objects.space.Scene3D;
import fr.threedijnns.objects.space.camera.GxDefaultCamera3D;
import fr.threedijnns.objects.space.shapes.vbo.GxRectangle3D;
import fr.threedijnns.objects.space.wrapper.GxFrame3D;

public class GisServiceRenderer3D extends GxViewportControl implements GisServiceRenderer<GisServiceRenderer3D> {

	static {
		gx.initialize(JOGL.instance(JOGL.GL2ES1));
	}

	GisService 																			provider;

	BoundingBox.TwoDims																	model;
	DefaultGisServiceController  														parent;
	GxViewportControl  																	canvas;
	GxRenderer																			renderer;
	Projector.Levelable<GeoCoordinate.Spheric2D, Coordinate.TwoDims> 					projector;

	FreeFlightController 																controller;

	private Viewport2D.Editable<?, ?> 	 												viewport;

	private Map<Class<? extends Gis.Object>, GisObjectRenderer<GisServiceRenderer3D>> 	objectRenderers;
	private Map<Class<? extends GisLayer>, GisLayerRenderer<GisServiceRenderer3D>> 		layerRenderers;

	public GisServiceRenderer3D(GisService _parent) {
		this(_parent, 1920, 1080);
	}
	public GisServiceRenderer3D(GisService _parent, int resWith, int _resHeight) {
		super(new Rasterer(), resWith, _resHeight);
		
		canvas = new GxViewportControl(renderer = new Rasterer(), resWith, _resHeight);
/*
		_parent.layoutBoundsProperty().addListener((_obs, _old, _new) -> {
			double w = _new.getWidth();
			double h = _new.getHeight();

			canvas . prefWidthProperty().unbind();
			canvas . prefHeightProperty().unbind();
			canvas . setMinSize(w, h);
			canvas . setPrefSize(w, h);
			canvas . setMaxSize(w, h);
		});
*/
		canvas . setMinSize(680, 480);
		canvas . setPrefSize(680, 480);
		canvas . setMaxSize(680, 480);
		
		
		this.snapshotRequest = new AtomicLong();
		this.snapshotCurrent = -1L;

		initMapTexture();
		initThreeDjinn();
		
		new Thread(() -> {
			while(true) {
				snapshotRequest.incrementAndGet();
				final long snapshotRequestID = snapshotRequest.get();
				if ( snapshotCurrent < snapshotRequestID && streamTexture != null ) {
					streamTexture.snapshot();
					snapshotCurrent = snapshotRequestID;
				}
				if(streamTexture != null)
					streamTexture.tick();

				try { Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}).start();
	}


	public BoundingBox.TwoDims												getModel() {
		if(model == null)
			model = Mercator.GeoSphere;
		return model;
	}
	public Projector.Levelable<GeoCoordinate.Spheric2D, Coordinate.TwoDims>	getProjector() {
		return new GudermannProjector(new MercatorTileSystem(), () -> 0);
	}
	public GisService 														getMapProvider() {
		return provider;
	}
	
	
	
	/**
	 * THREEDJINN SCENE
	 */
	GxCamera3D  				camera;

	Scene3D 					scene;
	GxRectangle3D				map;

	BufferedImage 				mapTexture;
	Texture2D		 			streamTexture;

	public GxCamera3D 			getCamera() {
		return camera;
	}


	private final AtomicLong 	snapshotRequest;
	private       long       	snapshotCurrent;

	@Override
	public GisRendererFactory<GisServiceRenderer3D> getFactory() {
		return null;
	}

	@Override
	public void 							refresh() {
		
	}

	private void 							initMapTexture() {
		mapTexture = BufferedImages.createBgrImage(8192, 8192);
	}
	private StreamHandler2D 				initMapTextureStream() {
		return new StreamHandler2D() {

			public int getWidth() {
				return (int) GisServiceRenderer3D.this.canvas.getWidth();
			}

			public int getHeight() {
				return (int) GisServiceRenderer3D.this.canvas.getHeight();
			}

			public PixelFormat getPixelFormat() {
				return PixelFormat.PXF_BGRA8;
			}
/**/
			public void process(final int width, final int height, final ByteBuffer buffer, final int stride, final Semaphore signal) {
				System.out.println(width + "x" + height);
				int nbChannels = stride / width;

				for(int j = 0; j < height; ++j) {
					for(int i = 0; i < width; ++i) {
						buffer.put(nbChannels * (j * width + i),     (byte) 0);
						buffer.put(nbChannels * (j * width + i) + 1, (byte) (255 * Math.random()));
						buffer.put(nbChannels * (j * width + i) + 2, (byte) 0);
						buffer.put(nbChannels * (j * width + i) + 3, (byte) 255);
					}
				}

				signal.release();
			}	
/*/
			private WritableImage image;

			public void process(final int width, final int height, final ByteBuffer buffer, final int stride, final Semaphore signal) {
				// This method runs in the background rendering thread
				Platform.runLater(() -> {
					System.out.println("processing....");
					if(image == null || image.getWidth() != width || image.getHeight() != height)
						image = new WritableImage(width, height);

					MapRenderer3D.this.canvas.snapshot(
										(snapshotResult) -> {
											snapshotResult.getImage().getPixelReader().getPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, stride);
											signal.release();
											return null;
										}, new SnapshotParameters(), image);
				});
			}
/**/
		};
	}
	private void 							initThreeDjinn() {
		camera = new GxDefaultCamera3D(Projections3D.perspective(90, 16.0f/9.0f, 0.5f, (float) 1e3));
		camera.lookAt(new DoubleVector3D(10, 10, 10), new DoubleVector3D(0, 0, 0), new DoubleVector3D(0, 0, 1));

		scene = new Scene3D();
		scene.setCamera(camera);

		scene.addRenderable(new GxFrame3D());

		map = new GxRectangle3D(40.0f, 30.0f, 2, 2);
		map.enableTexture(true);

		scene.addRenderable(map);

		streamTexture = new Texture2D();
		map.setTexture(streamTexture);
		streamTexture.bind(initMapTextureStream());

		canvas.getRenderer().setScene3D(scene);
		

		controller = new FreeFlightController(this, camera);
		
		getRenderer().setScene3D(scene);
	}
	
	@Override
	public Optional<GisServiceController> getController() {
		// TODO Auto-generated method stub
		return null;
	}

}
