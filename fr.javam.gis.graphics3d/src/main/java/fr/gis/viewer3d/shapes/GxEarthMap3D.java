package fr.gis.viewer3d.shapes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.mercator.Mercator;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.GisLayer;
import fr.gis.api.GisService;
import fr.gis.graphics.control3d.GeodesicController;
import fr.gis.mercator.MercatorViewport;
import fr.java.data.Data;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.shapes.Ellipsoid3D;
import fr.java.maths.Points;
import fr.java.maths.geometry.Space;
import fr.java.maths.geometry.space.types.SimpleDimension3D;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProvider;
import fr.javafx.stage.StageExt;
import fr.threedijnns.gx;
import fr.threedijnns.api.interfaces.nodes.GxRenderable;
import fr.threedijnns.api.interfaces.nodes.space.GxNode3D;
import fr.threedijnns.api.interfaces.nodes.space.GxObject3D;
import fr.threedijnns.api.lang.enums.EngineOption;
import fr.threedijnns.api.lang.enums.FaceType;
import fr.threedijnns.api.lang.enums.PolygonMode;
import fr.threedijnns.api.lang.enums.PrimitiveType;
import fr.threedijnns.objects.base.GxObject3DBase;
import fr.threedijnns.objects.space.wrapper.quadrics.GxEllipsoid3D;
import fr.threedijnns.utils.HeightMap;

public class GxEarthMap3D extends GxObject3DBase implements GxObject3D {
	public static final  Path 	earthTextureFile = Paths.get( System.getProperty("resources_path") + "model/universe/data/MW_SUN_EARTH/map/Earth_3x5400x2700.png");

	public static final int    MIN_LEVEL     =    4;
	public static final int    MAX_LEVEL     =   19;
	public static final double MIN_LONGITUDE = -180.0;
	public static final double MAX_LONGITUDE =  180.0;
	public static final double MIN_LATITUDE  = - 85.051129;
	public static final double MAX_LATITUDE  =   85.051129;

	private static final double K = 1e-3; 							// a scale factor
	private static final double EARTH_RADIUS = K * 6_371_000, 		// in meters
								MAP_EPSILON  = K * 50, 				// in meters
								CAMERA_DIST  = K * 1_500_000; 		// in meters


	
	
	
	Ellipsoid3D 			model;
	GisService 				service;
	Mercator.Viewport       viewport;

	GxEllipsoid3D 			earth;
	GxTile3D[]  			tiles;
//GxRenderable
    protected double		rho_min, rho_max, phi_min, phi_max;
    protected int 			stacks, slices;
    protected boolean		updated;

    
    
    
    
    
    

	int level;
	int width, height;
	
	double lg, lt;
	
	boolean animated, sliceOnLong, sliceOnLat;

    
    
    
    
    
    
	public GxEarthMap3D(Ellipsoid3D _model, double _mapAltitude, GisService _mapService) {
		super();

		model    = _model;
		service  = _mapService;
		viewport = new MercatorViewport();

		stacks = 0;
		slices = 0;
		
		
		
		
		

		level      = MAX_LEVEL;
		width      = 10;
		height     = 10;
		
		animated = true;
		sliceOnLong = true;
		
		lg = 2.144804;
		lt = 48.793139;
		
		initializeEarthView();
	}

	public void set(double _rho_min, double _rho_max, double _phi_min, double _phi_max, int _stacks, int _slices, int _level) {
		rho_min  = _rho_min;
		rho_max  = _rho_max;
		phi_min  = _phi_min;
		phi_max  = _phi_max;
		level    = _level;

		if(stacks != _stacks | slices != _slices) {
			if(tiles != null)
				for(int k = 0; k < stacks * slices; ++k)
					tiles[k] = null;

			stacks = _stacks;
			slices = _slices;
			tiles  = new GxTile3D[stacks * slices];
			for(int k = 0; k < stacks * slices; ++k)
				tiles[k] = new GxTile3D();
		}
		
		updated = true;
	}

	@Override
	public void 		process() {
		if(!updated)
			return ;

		double dSlices  = (phi_max - phi_min) / slices;
		double dStacks  = (rho_max - rho_min) / stacks;

		TileProvider.Async<BufferedImage> tileProvider = service.getDefault(GisLayer.BASE, true)/*.map(ml -> (BaseMapLayer) ml)*/.get().getTileProvider();

		for(int i = 0; i < stacks; ++i) {
			for(int j = 0; j < slices; ++j) {
				Point3D TL = model.getSurfacePoint(rho_min + i * dStacks, phi_min + j * dSlices);
				Point3D TR = model.getSurfacePoint(rho_min + i * dStacks, phi_min + (j + 1) * dSlices);
				Point3D BL = model.getSurfacePoint(rho_min + (i + 1) * dStacks, phi_min + j * dSlices);
				Point3D BR = model.getSurfacePoint(rho_min + (i + 1) * dStacks, phi_min + (j + 1) * dSlices);

				double LG_c = (phi_min + (j + 0.5) * dSlices) * 180.0 / Math.PI;
				double LT_c = (rho_min + (i + 0.5) * dStacks) * 180.0 / Math.PI;

				TileCoordinate tileCoords = viewport.modelInTile(GeoCoordinates.newWGS84(LG_c, LT_c), level);

//				Data.Async<TileCoordinate, BufferedImage> tile = viewport.getTileSystem().get(tileCoords.getI(), tileCoords.getJ(), tileCoords.getLevel());
				Data.Async<TileCoordinate, BufferedImage> tile = tileProvider.get(tileCoords.getLevel(), tileCoords.getI(), tileCoords.getJ());

// TODO:: 
				tiles[i * slices + j].update(TL, TR, BL, BR);
				tiles[i * slices + j].updateTexture(tile);
				tiles[i * slices + j].process();
			}
		}
		updated = false;
	}

	@Override
	public void 		render() {
		if(tiles == null)
			return ;

		gx.disable(EngineOption.GX_LIGHTING);
		gx.setPolygonMode(PolygonMode.Realistic, FaceType.FrontAndBack);

		for(GxNode3D child : getChildren())
			((GxRenderable) child).render();

		for(GxTile3D tile : tiles)
			tile.render();

		gx.setPolygonMode(PolygonMode.Realistic, FaceType.FrontAndBack);
		gx.enable(EngineOption.GX_LIGHTING);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void setCenterLg(double _lg) {
		lg = _lg;
	}
	public double getCenterLg() {
		return lg;
	}

	public void setCenterLt(double _lt) {
		lt = _lt;
	}
	public double getCenterLt() {
		return lt;
	}
	
	public void setLevel(int _level) {
		if(_level >= MIN_LEVEL && _level <= MAX_LEVEL)
			level = _level;
	}
	public int getLevel() {
		return level;
	}

	public void setWidth(int _width) {
		if(_width < 0)
			return ;
		width = _width;
	}
	public int getWidth() {
		return width;
	}

	public void setHeight(int _height) {
		if(_height < 0)
			return ;
		height = _height;
	}
	public int getHeight() {
		return height;
	}


	public void animate() {
		double       RANGE_LONG = 360.0		 * Math.PI / 180., 
				     RANGE_LAT  = 170.102258 * Math.PI / 180.;

		new Thread(() -> {
//			Coordinate   center = Coordinates.newWGS84(2.144804, 48.793139);

			while(true) {
				GeoCoordinate.Spheric2D   center = GeoCoordinates.newWGS84(lg, lt);
				
				double          dLg    = 0.1; //RANGE_LONG / WebMercatorExt.tileCountAt(level);
				double          dLt    = 0.1; //RANGE_LAT  / WebMercatorExt.tileCountAt(level);
				TileCoordinate  inTile = viewport.modelInTile(center, level);

				GeoCoordinate.Tiled2D   TopLeftTile     = GeoCoordinates.newTiled(level, inTile.getI() - width, inTile.getJ() - height, 0, 0);
				GeoCoordinate.Tiled2D   BottomRightTile = GeoCoordinates.newTiled(level, inTile.getI() + width, inTile.getJ() + height, 256, 256);
//				GeoCoordinate.Spheric2D TopLeft         = WM.toKernel(level, inTile.getI() - width, inTile.getJ() - height, 0, 0);
//				GeoCoordinate.Spheric2D BottomRight     = WM.toKernel(level, inTile.getI() + width, inTile.getJ() + height, 256, 256);
				GeoCoordinate.Spheric2D TopLeft         = viewport.tileInModel(TopLeftTile);
				GeoCoordinate.Spheric2D BottomRight     = viewport.tileInModel(BottomRightTile);

				final double minLg   = TopLeft.getLongitude()     * Math.PI / 180d;
				final double maxLg   = BottomRight.getLongitude() * Math.PI / 180d;
				final double minLt   = TopLeft.getLatitude()      * Math.PI / 180d;
				final double maxLt   = BottomRight.getLatitude()  * Math.PI / 180d;
				final int    nbTileX = 2 * width + 1;
				final int    nbTileY = 2 * height + 1;

				if(animated) {
					gx.runLater(() -> set(minLt, maxLt, minLg, maxLg, nbTileX, nbTileY, level));
				}

//				center.plus(sliceOnLong ? dLg : 0, sliceOnLat ? dLt : 0, 0);

				try {
					Thread.sleep(25);
				} catch (InterruptedException e) { }
			}
		}).start();

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private void initializeVirtualWorld() {
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
		
		earth = new GxEllipsoid3D(earthEllipsoid, 128, 256, hMap);
		
		getChildren().add(earth);

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



	}
	private void initializeMap3D() {
		StageExt.create(new GeodesicController(this));
		animate();
	}

	private void initializeAnimation() {
		/**/
		new Thread(() -> {
			while(true) {
				earth.getFrame().rotateAxes(0.0, 0.01, 0.0);
				try { Thread.sleep(25); } catch (InterruptedException _e) { }
			}
		}).start();
		/**/
	}
	
	
	
	
	
	
	
	
	


	private class GxTile3D extends GxObject3DBase {
		private Point3D 		topLeft, 
								topRight, 
								bottomLeft, 
								bottomRight;
		private Data.Async<TileCoordinate, BufferedImage> 	tile;
		private boolean 		tileChanged/*, tileLoaded, tileUpdated*/;
	
		public GxTile3D() {
			super();
		}
	
		public void update(Point3D _tl, Point3D _tr, Point3D _bl, Point3D _br) {
			topLeft     = _tl;
			topRight    = _tr;
			bottomLeft  = _bl;
			bottomRight = _br;
		}
		public void updateTexture(Data.Async<TileCoordinate, BufferedImage> _tile) {
			if(tile != _tile) {
				tile = _tile;
				tileChanged = true;
	//			tileLoaded  = false;
	//			tileUpdated = false;
			}
		}
	
		@Override
		public void process() {
			if(tileChanged) {
				if(tile == null) {
					tileChanged = false;
					return ;
				}
	
				if( tile.isLoaded() ) {
					System.out.println("Tile is ready  " + tile.getCoordinates().getLevel() + "/" + tile.getCoordinates().getI() + "/" + tile.getCoordinates().getJ());
					gx.runLater( () -> {
						enableTexture(true);
						getTexture().update(tile.getContent(), null, null);
					}); // TODO:: No need of gx.runLater in a functional use case !!!
					tileChanged = false;
					return ;
				}
	
				if(!tile.isLoading()) {
					System.out.println("Loading tile..." + tile.getCoordinates().getLevel() + "/" + tile.getCoordinates().getI() + "/" + tile.getCoordinates().getJ());
					try {
						tile.load();
					} catch (Exception e) {}
				}
	
			}
		}
	
		@Override
		public void render() {
			if(isTextureEnabled() && hasTexture())
				getTexture().enable(0);
	
			gx.begin(PrimitiveType.QuadList);
				if(isTextureEnabled() && hasTexture())
					gx.texCoords	(0.0, 0.0);
	//				gx.texCoords	(text[0].getX(), text[0].getY());
				gx.vertex	(topLeft);
	
				if(isTextureEnabled() && hasTexture())
					gx.texCoords	(1.0, 0.0);
	//				gx.texCoords	(text[1].getX(), text[1].getY());
				gx.vertex	(topRight);
	
				if(isTextureEnabled() && hasTexture())
					gx.texCoords	(1.0, 1.0);
	//				gx.texCoords	(text[2].getX(), text[2].getY());
				gx.vertex	(bottomRight);
	
				if(isTextureEnabled() && hasTexture())
					gx.texCoords	(0.0, 1.0);
	//				gx.texCoords	(text[3].getX(), text[3].getY());
				gx.vertex	(bottomLeft);
			gx.end();
	
			if(isTextureEnabled() && hasTexture())
				getTexture().disable(0);
		}
	
	}

}
