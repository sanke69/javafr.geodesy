package fr.gms.hmi.ehorizon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoCoordinate.Spheric2D;
import fr.geodesic.api.GeoDynamics;
import fr.gis.api.Gis;
import fr.gis.api.Gis.Object;
import fr.gis.api.road.Road;
import fr.gis.graphics.api.render.items.GisObjectRenderer;
import fr.gis.graphics.api.render.options.GisRendererOption;
import fr.gis.viewer2d.GisServiceRenderer2D;
import fr.gis.viewer2d.GisServiceRenderer2D.AvailableCanvas;
import fr.gis.viewer2d.api.GisObjectRenderer2D;
import fr.gms.api.ego.EgoVehicle;
import fr.gms.api.sensor.EHorizon;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.lang.collections.RingList;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Dimension.TwoDims;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Angles;
import fr.java.maths.geometry.types.Dimensions;
import fr.java.maths.geometry.types.Points;

public class EHorizonObjectRenderer2D implements GisObjectRenderer2D<EHorizon> {

	@Override
	public boolean isDrawable(Object _object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawObject2D(Drawer _drawer, TwoDims _window, Function<Spheric2D, Point2D> _projector, EHorizon _object, GisRendererOption _options) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderObject(GisServiceRenderer2D _renderer, Gis.Object _object, GisRendererOption _options) {
		if( ! (_object instanceof EHorizon) )
			return ;

		EHorizon eHorion = (EHorizon) _object;

		GisRendererOption opts = new GisRendererOption();

		List<EHorizonComponent> components = new ArrayList<EHorizonComponent>();
		/*
		components.add(EHorizonComponent.HISTORY);
		components.add(EHorizonComponent.INFLUENCE_AREA);
		components.add(EHorizonComponent.FORWARD_ROAD_TREE);
		components.add(EHorizonComponent.TRAFIC_SIGNS);
		components.add(EHorizonComponent.BUILDINGS);
		components.add(EHorizonComponent.FORWARD_TRAJECTORY);
		components.add(EHorizonComponent.BACKWARD_TRAJECTORY);		
		*/
		opts.put("Components", components);

		render(_renderer, eHorion, opts);
	}

	public void render(GisServiceRenderer2D _renderer, EHorizon _elt, GisRendererOption _options) {
		GisObjectRenderer<GisServiceRenderer2D> buildingDrawer 	= _renderer.getFactory().getObjectRenderer(Gis.Building.class);
		GisObjectRenderer<GisServiceRenderer2D> segDrawer  		= _renderer.getFactory().getObjectRenderer(Road.Element.class);
		GisObjectRenderer<GisServiceRenderer2D> signDrawer 		= _renderer.getFactory().getObjectRenderer(Road.TraficSign.class);
		GisObjectRenderer<GisServiceRenderer2D> trajDrawer 		= _renderer.getFactory().getObjectRenderer(Gis.Path.class);

		if(_elt.getCenter() == null)
			return ;
		
		drawEHorizonRange(_renderer, _elt.getCenter(), null, _elt.getRadius());

		List<EHorizonComponent> components = (List<EHorizonComponent>) _options.get("Components");

		EgoVehicle veh = null;
		if(_elt instanceof EgoVehicle)
			veh = (EgoVehicle) _elt;
		
		
		double W = _renderer.getWidth(), H = _renderer.getHeight();
		
/*
		RadarDesign radar = new RadarDesign() {
			private static final long serialVersionUID = 1L;

			@Override public double 			getX() 				{ return W - 256 - 10; }
			@Override public double 			getY() 				{ return H - 256 - 10; }

			@Override public double 			getWidth() 			{ return 256; }
			@Override public double 			getHeight() 		{ return 256; }

			@Override public double 			getOpenAngle() 		{ return 360; }
			@Override public Interval<Double> 	getRange() 			{ return Interval.of(15d, 200d); }

		};
		radar.draw(_renderer.getDrawer());
*/
		if(components != null)
		for(EHorizonComponent component : components) {
			switch(component) {
/*
			case HISTORY:				drawEHorizonHistory(_renderer, _elt.getHistory());
										break;
/*
			case CURRENT_SEGMENT:		if(_elt.getMapMatchedPosition() != null) {
											RoadElement roadElt = _elt.getMapMatchedPosition().getRoadElement(); 

											segDrawer.renderObject(_renderer, roadElt, RendererOption.of("color", "orange"));
											for(RoadElement prev : roadElt.getPreviousElements())
												segDrawer.renderObject(_renderer, prev, RendererOption.of("color", "yellow"));
											for(RoadElement next : roadElt.getPreviousElements())
												segDrawer.renderObject(_renderer, next, RendererOption.of("color", "red"));
										}
										break;
*v /
			case INFLUENCE_AREA:		if(_elt.getSurroundingRoadElements() != null)
											for(Road.Element seg : _elt.getSurroundingRoadElements())
												segDrawer.renderObject(_renderer, seg, GisRendererOption.of("color", "red"));
										break;
										*/
			case TRAFIC_SIGNS:			if(_elt.getSurroundingTrafficSigns() != null) {
											for(Road.TraficSign tse : _elt.getSurroundingTrafficSigns()) {
												signDrawer.renderObject(_renderer, tse);
											}
										}
										break;
/*
			case FORWARD_ROAD_TREE:		if(_elt.getForwardRoadTree() != null)
											for(RoadElement seg : _elt.getForwardRoadTree())
												segDrawer.renderObject(_renderer, seg, RendererOption.of("color", "green"));
										break;
			case FORWARD_ROAD_VECTOR:	
										break;
			case FORWARD_TRAJECTORY:	List<GeoCoordinate> forwardTraj = _elt.getForwardTrajectory(/*500, 0.3, null* /);
										if(forwardTraj != null && trajDrawer != null) {
											trajDrawer.renderObject(_renderer, new PathElement(IDs.random(), forwardTraj.get(0).getDatum(), forwardTraj), RendererOption.of("color", "green"));

											for(GeoCoordinate coord : forwardTraj)
												radar.drawCartesian(_renderer.getDrawer(), veh.getLocalCoordinate(coord));
										}
										break;
			case BACKWARD_TRAJECTORY:	List<GeoCoordinate> backwardTraj = _elt.getBackwardTrajectory(/*100, 0.3, null* /);
										if(backwardTraj != null) {
											trajDrawer.renderObject(_renderer, new PathElement(IDs.random(), backwardTraj.get(0).getDatum(), backwardTraj), RendererOption.of("color", "orange"));

											for(GeoCoordinate coord : backwardTraj)
												radar.drawCartesian(_renderer.getDrawer(), veh.getLocalCoordinate(coord));
										}
										break;
*/
			case BUILDINGS:				Collection<Gis.Building> buildings = _elt.getSurroundingBuildings();
										if(buildings != null)
											for(Gis.Building building : buildings)
												buildingDrawer.renderObject(_renderer, building);
										break;
			default:					break;
			}
		}

	}

	protected void drawEHorizonRange(GisServiceRenderer2D _renderer, GeoCoordinate _center, GeoDynamics _dynamics, double _radius) {
		Drawer	            drawer = _renderer.getDrawer(AvailableCanvas.MAPDATA);
		Dimension.TwoDims window = _renderer.getViewport().getWindow();

		Function<GeoCoordinate.Spheric2D, Point2D> m2w = p -> Points.of(_renderer.getViewport().modelInWindow(p));

		Point2D           p = m2w.apply(_center.asWGS84());
		Dimension.TwoDims d = Dimensions.of(25, 75); //WM.getDistanceInPixel(_center, vp.getLevel(), 2 * _radius, 2 * _radius);

		// Influence Area
		drawer.setDrawStyle("-fx-stroke: green;");
		drawer.setLineWidth(1.0);
		drawer.drawEllipse(p.getX() - d.getWidth()/2.0, p.getY() - d.getHeight()/2.0, d.getWidth(), d.getHeight());

		// Current Position
		drawer.setDrawStyle("-fx-stroke: yellow;");
//		drawer.setFill(ColorsX.YELLOW);
//		drawer.setLineWidth(2.0);
		drawer.drawEllipse(p.getX()-5, p.getY()-5, 10, 10, 2d, Colors.YELLOW, Colors.YELLOW);

		drawer.setDrawStyle("-fx-stroke: red;");
		drawer.setLineWidth(1.0);
		drawer.drawEllipse(p.getX()-1, p.getY()-1, 2, 2);

		if(_dynamics != null) {
			// Heading
			double headRad = Angles.Degree2Radian( 90 - _dynamics.getHeading() );
			double dx      = 30.0 * Math.cos(headRad),
				   dy      = 30.0 * Math.sin(headRad);
			drawer.drawSegment(p.getX(), p.getY(), p.getX() + dx, p.getY() - dy);

			// Velocity Vector
			if(_dynamics.getVelocity() != null) {
				double K = 0.15;
				double vx      = K * (_dynamics.getVelocity().getX() * Math.cos(headRad) + _dynamics.getVelocity().getY() * Math.sin(headRad)),
					   vy      = K * (_dynamics.getVelocity().getX() * Math.sin(headRad) + _dynamics.getVelocity().getY() * Math.cos(headRad));
	
				drawer.setDrawStyle("-fx-stroke: red;");
				drawer.setLineWidth(2.0);
				drawer.drawSegment(p.getX(), p.getY(), p.getX() - vx, p.getY() + vy);
			}
		}
	}

	protected void drawEHorizonHistory(GisServiceRenderer2D _renderer, RingList<GeoCoordinate> _history) {
		Drawer	            drawer = _renderer.getDrawer(AvailableCanvas.MAPDATA);
		Dimension.TwoDims window = _renderer.getViewport().getWindow();

		Function<GeoCoordinate.Spheric2D, Point2D> m2w = p -> Points.of(_renderer.getViewport().modelInWindow(p));

		Color.Interpolable c = Colors.BLACK;

		for(int i = _history.size() - 1; i > 0; --i) {
			GeoCoordinate raw = _history.get(i);

			Point2D  p = m2w.apply(raw.asWGS84());

			// History Position
			drawer.setFill(c.interpolate(Colors.YELLOW, 1.0 - ((double) i / (_history.size() - 1.0))));
			drawer.setLineWidth(2.0);
			drawer.drawEllipse(p.getX()-5, p.getY()-5, 10, 10);

			drawer.setLineWidth(1.0);
			drawer.drawEllipse(p.getX()-1, p.getY()-1, 2, 2);

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

}
