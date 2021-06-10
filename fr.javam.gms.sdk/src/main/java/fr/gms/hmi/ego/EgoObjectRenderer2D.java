package fr.gms.hmi.ego;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.utils.GeoCoordinates;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.GisServiceRenderer2D.AvailableCanvas;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.gis.viewer2d.renderer.objects.gis.GisBuilding2D;
import fr.gis.viewer2d.renderer.objects.gis.GisDynamics2D;
import fr.gis.viewer2d.renderer.objects.road.RoadElement2D;
import fr.gis.viewer2d.renderer.objects.road.RoadTraficSign2D;
import fr.gms.api.ego.EgoVehicle;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.lang.collections.RingList;
import fr.java.math.algebra.NumberVector.Norm;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Angles;
import fr.java.maths.Dimensions;
import fr.java.maths.Points;
import fr.java.sdk.draw.design.CompassDesignAdapter;
import fr.java.sdk.draw.design.GaugeDesignAdapter;
import fr.java.sdk.draw.design.RadarDesignAdapter;

public class EgoObjectRenderer2D implements GisObjectRenderer2D<EgoVehicle> {
	protected GisBuilding2D        buildingRenderer   = new GisBuilding2D();
	protected GisDynamics2D        dynamicsRenderer   = new GisDynamics2D();
	protected RoadTraficSign2D     traficsignRenderer = new RoadTraficSign2D();
	protected RoadElement2D        roadEltRenderer    = new RoadElement2D();

	protected double               WIDTH = -1, HEIGHT = -1;
	protected Drawer			   DATA, OVERLAY;
	protected RadarDesignAdapter   RADAR = null;

	@Override
	public void 					renderObject(GisServiceRenderer2D _renderer, Gis.Object _object, GisRendererOption _options) {
		Dimension.TwoDims            window    = _renderer.getViewport().getWindow();
		Function<Spheric2D, Point2D> projector = p -> Points.of(_renderer.getViewport().modelInWindow(p));

		if(WIDTH == -1 || WIDTH != window.getWidth() || HEIGHT != window.getHeight()) {
			WIDTH   = window.getWidth();
			HEIGHT  = window.getHeight();
			DATA    = _renderer.getDrawer(AvailableCanvas.MAPDATA);
			OVERLAY = _renderer.getDrawer(AvailableCanvas.OVERLAY);
		}

		if( !isDrawable(_object) )
			return ;

		EgoVehicle vehicle = (EgoVehicle) _object;

		drawObject2D(DATA, window, projector, vehicle, _options);
	}

	@Override
	public boolean 					isDrawable(Object _object) {
		return (_object instanceof EgoVehicle);
	}

	@Override
	public void    					drawObject2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
//		EnumSet<EgoComponent> toRender = EnumSet.allOf(EgoComponent.class);
		EnumSet<EgoComponent> toRender = EnumSet.of(
				EgoComponent.STATE, 
				EgoComponent.HISTORY, 
				EgoComponent.INFLUENCE_AREA,

				EgoComponent.BUILDINGS, EgoComponent.MOBILES, EgoComponent.TRAFIC_SIGNS, EgoComponent.ROAD_ELEMENTS,

				EgoComponent.FORWARD_ROAD_TREE, EgoComponent.FORWARD_ROAD_VECTOR,

				// VEDECOM:: Must be switched to VdcEgoObjRenderer2D
				EgoComponent.FORWARD_TRAJECTORY, EgoComponent.BACKWARD_TRAJECTORY,

				EgoComponent.HUD_COMPASS, EgoComponent.HUD_SPEEDOMETER, EgoComponent.HUD_RADAR);
		
		for(EgoComponent component : toRender) {
			switch(component) {
			case STATE:					drawState2D						(DATA, _window, _projector, _vehicle, _options); break;
			case HISTORY:				drawHistory2D					(DATA, _window, _projector, _vehicle, _options); break;
			case INFLUENCE_AREA:		drawInfluenceArea2D				(DATA, _window, _projector, _vehicle, _options); break;

			case BUILDINGS:				drawSurroundingBuildings2D		(DATA, _window, _projector, _vehicle, _options); break;
			case MOBILES:				drawSurroundingMobiles2D		(DATA, _window, _projector, _vehicle, _options); break;
			case TRAFIC_SIGNS:			drawSurroundingTraficSigns2D	(DATA, _window, _projector, _vehicle, _options); break;
			case ROAD_ELEMENTS:			drawSurroundingMap2D			(DATA, _window, _projector, _vehicle, _options); break;

			case FORWARD_ROAD_TREE:		drawForwardTree2D				(DATA, _window, _projector, _vehicle, _options); break;
			case FORWARD_ROAD_VECTOR:

			case HUD_COMPASS:			drawHudCompass					(OVERLAY, _vehicle.getHeading()); break;
			case HUD_SPEEDOMETER:		drawHudSpeedOMeter				(OVERLAY, 3.6 * _vehicle.getVelocity().norm(Norm.Euclidian)); break;
			case HUD_RADAR:				RADAR = drawHudRadar			(OVERLAY); break;

			// VEDECOM:: Must be switched to VdcEgoObjRenderer2D
			case FORWARD_TRAJECTORY:	break;
			case BACKWARD_TRAJECTORY:	break;
			};
		}
	}

	protected void 					drawState2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Point2D pt = _projector.apply(_vehicle.getPosition().asWGS84());

		// Current Position
		double inner = 1, outer = 5;
		_drawer.drawEllipse(pt.getX()-outer, pt.getY()-outer, 2*outer, 2*outer, 
							1d, Colors.YELLOW, Colors.YELLOW);
		_drawer.drawEllipse(pt.getX()-inner, pt.getY()-inner, 2*inner, 2*inner, 
							1d, Colors.ORANGE);

		// Heading & Velocity
		double headRad = Angles.Degree2Radian( 90 - _vehicle.getHeading() );
		double velKmh  = _vehicle.getVelocity().getX() * 3.6;
		double dx      = velKmh * Math.cos(headRad),
			   dy      = velKmh * Math.sin(headRad);

		_drawer.drawSegment(pt.getX(), pt.getY(), pt.getX() + dx, pt.getY() - dy,
							3d, Colors.RED);
	}
	protected void 					drawHistory2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		RingList<GeoDynamics> _history = _vehicle.getHistory();

		for(int i = _history.size() - 1; i > 0; --i) {
			GeoCoordinate raw = _history.get(i);

			Point2D  p = _projector.apply(raw.asWGS84());

			// History Position
			Color historyColor = Colors.BLACK.interpolate(Colors.YELLOW, 1.0 - ((double) (i / (_history.size() - 1.0))));

			_drawer.drawEllipse(p.getX()-5, p.getY()-5, 10, 10, 
								2d, historyColor);
			_drawer.drawEllipse(p.getX()-1, p.getY()-1, 2, 2, 
								1d, historyColor);

			// Heading
			/*
			double headRad = Angles.Degree2Radian( 90 - raw.getHeading() );
			double dx      = 30.0 * Math.cos(headRad),
				   dy      = 30.0 * Math.sin(headRad);
			_map.strokeLine(p.getX(), p.getY(), p.getX() + dx, p.getY() - dy);
			*/
	
			// Velocity Vector
			/*
			double K = 0.15;
			double vx      = K * (raw.getVelocity().x * Math.cos(headRad) + raw.getVelocity().y * Math.sin(headRad)),
				   vy      = K * (raw.getVelocity().x * Math.sin(headRad) + raw.getVelocity().y * Math.cos(headRad));
			_map.setDrawStyle("-fx-stroke: blue;");
			_map.setLineWidth(2.0);
			_map.strokeLine(p.getX(), p.getY(), p.getX() - vx, p.getY() + vy);
			*/
		}
	}
	protected void 					drawInfluenceArea2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		double            infRadius = _vehicle.getEHorizon().getRadius();
		Point2D           pt        = _projector.apply(_vehicle.getPosition().asWGS84());
		Point2D           radius    = _projector.apply(GeoCoordinates.plusUTM(_vehicle.getPosition(), infRadius, infRadius).asWGS84());
		Dimension.TwoDims infArea   = Dimensions.of(Math.abs( radius.getX() - pt.getX() ), Math.abs( radius.getY() - pt.getY() ));

		// Influence Area
		_drawer.drawEllipse(pt.getX() - infArea.getWidth()/2.0, pt.getY() - infArea.getHeight()/2.0, infArea.getWidth(), infArea.getHeight(),
							1d, Colors.GREEN, Colors.of(69,69,69,69));
	}

	protected void 					drawSurroundingBuildings2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Gis.Building> buildings = _vehicle.getEHorizon().getSurroundingBuildings();
		if(buildings != null)
			for(Gis.Building building : buildings)
				buildingRenderer.drawObject2D(_drawer, _window, _projector, building, null);
	}
	protected void 					drawSurroundingMobiles2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Gis.Dynamics> dynamics = _vehicle.getEHorizon().getSurroundingMobiles();
		if(dynamics != null)
			for(Gis.Dynamics mobile : dynamics)
				dynamicsRenderer.drawObject2D(_drawer, _window, _projector, mobile, null);
	}
	protected void 					drawSurroundingTraficSigns2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Road.TraficSign> traficsigns = _vehicle.getEHorizon().getSurroundingTrafficSigns();
		if(traficsigns != null)
			for(Road.TraficSign traficsign : traficsigns)
				traficsignRenderer.drawObject2D(_drawer, _window, _projector, traficsign, null);
	}
	protected void 					drawSurroundingMap2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Road.Element> elements = _vehicle.getEHorizon().getSurroundingRoadElements();
		if(elements != null)
			for(Road.Element elt : elements)
				roadEltRenderer.drawObject2D(_drawer, _window, _projector, elt, GisRendererOption.of("color", "red"));
	}

	protected void 					drawForwardTree2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Road.Element> elements = _vehicle.getEHorizon().getForwardRoadTree();
		if(elements != null)
			for(Road.Element elt : elements)
				roadEltRenderer.drawObject2D(_drawer, _window, _projector, elt, GisRendererOption.of("color", "green"));
	}
	protected void 					drawForwardVector2D(Drawer _drawer, Dimension.TwoDims _window, Function<Spheric2D, Point2D> _projector, EgoVehicle _vehicle, GisRendererOption _options) {
		Collection<Road.Element> elements = _vehicle.getEHorizon().getForwardRoadVector();
		if(elements != null)
			for(Road.Element elt : elements)
				roadEltRenderer.drawObject2D(_drawer, _window, _projector, elt, GisRendererOption.of("color", "blue"));
	}

	protected CompassDesignAdapter 	drawHudCompass(Drawer _hud, double _heading_degree) {
		CompassDesignAdapter compass = new CompassDesignAdapter(WIDTH - 256 - 10, HEIGHT - 256 - 128 - 10, 128, 128);
		compass.setNorthPosition(0);
		compass.setClockWise(false);
		compass.setHandAngle(_heading_degree);
		compass.draw(_hud);

		return compass;
	}
	protected GaugeDesignAdapter   	drawHudSpeedOMeter(Drawer _hud, double _velocity_kmh) {
		GaugeDesignAdapter speedometer = new GaugeDesignAdapter(WIDTH - 256 + 128 - 10, HEIGHT - 256 - 128 - 10, 128, 128);
		speedometer.setAngleRange(270, 235);
		speedometer.setValueRange(0d, 200d);
		speedometer.setValue(_velocity_kmh);
		speedometer.draw(_hud);

		return speedometer;
	}
	protected RadarDesignAdapter   	drawHudRadar(Drawer _hud) {
		RadarDesignAdapter radar = new RadarDesignAdapter(WIDTH - 256 - 10, HEIGHT - 256 - 10, 256, 256);
		radar.radar().setOpenAngle(360);
		radar.radar().setRange(5, 250);
		radar.radar().setAngularGrid(45, 15);
		radar.radar().setRangeGrid(100, 25);
		radar.draw(_hud);

		return radar;
	}

}
