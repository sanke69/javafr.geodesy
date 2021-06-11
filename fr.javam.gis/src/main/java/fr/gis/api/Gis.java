package fr.gis.api;

import java.util.EnumSet;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.GeoDynamics;
import fr.geodesic.api.referential.Datum;
import fr.java.lang.collections.RingList;
import fr.java.lang.properties.Timestamp;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.BoundingBox;
import fr.java.patterns.capabilities.Identifiable;

public interface Gis {

	public enum User {
		EgoVehicle,
		Pedestrian, Bicycle,
		Motorcycle, Automotive, Truck,
		WildAnimal, Cattle,
		Boat,
		Plane,
		CRUISER_X,
		UNKNOWN;

		public static final EnumSet<User> MotorVehicles = EnumSet.of(Motorcycle, Automotive, Truck);

		public static final EnumSet<User> PedestrianWay = EnumSet.of(Pedestrian);
		public static final EnumSet<User> BicycleWay    = EnumSet.of(Bicycle);
		public static final EnumSet<User> MotorWay      = EnumSet.of(Motorcycle, Automotive, Truck);

		public static boolean isSharedArea(EnumSet<User> _users) {
			return _users.contains(Pedestrian) && _users.stream().map(usr -> MotorVehicles.contains(usr)).filter(b -> b).findAny().get();
		}
	
		public static boolean arePedestrianAuthorized(EnumSet<User> _users) {
			return _users.contains(Pedestrian);
		}
		public static boolean areBicycleAuthorized(EnumSet<User> _users) {
			return _users.contains(Bicycle);
		}
		public static boolean areAutomotiveAuthorized(EnumSet<User> _users) {
			return _users.contains(Automotive);
		}
		public static boolean areTruckAuthorized(EnumSet<User> _users) {
			return _users.contains(Truck);
		}

	}

	public interface Object extends Identifiable {

		public interface Editable extends Object {
			public void 			addProperty(String _name, java.lang.Object _value);
		}

		public java.lang.Object getProperty(String _name);
//		public <C> C 			getProperty(String _name, Class<C> _cast);
	
	}

	public interface Node  extends Object {
	
		public GeoCoordinate 		getPosition();
	
	}
	public interface Curve extends Object {
	
		public List<GeoCoordinate> 	getTops();
	
	}
	public interface Area  extends Object {
	
		public List<GeoCoordinate> 	getOutline();
	
		public BoundingBox.TwoDims 	getBounds();
	
	}

	// DYNAMICS
	public interface Dynamics extends Object {
	
		public Gis.User					getNature();
		public RingList<GeoDynamics> 	getHistory();
	
		public Timestamp				getTimestamp();// 		{ return getPosition().getTimestamp(); }
		public GeoCoordinate 			getPosition();// 		{ return getPosition(); }
		public Vector3D 				getVelocity();// 		{ return getPosition().getVelocity(); }
		public Vector3D 				getAcceleration();// 	{ return getPosition().getAcceleration(); }
	
	}

	public interface WeatherInfo extends Node { }

	public interface Stream      extends Curve { }

	public interface Building    extends Area { }
	public interface Relief      extends Area { 
		public enum Type {
			Lake				( 0, "Lac"),
			Sea					( 0, "Mer"),
			Water				( 0, "Eau"),
			Ground				( 1, "Sol"),
			Forest				( 2, "Foret"),
			Hill				( 3, "Colline"),
			Mountain			( 3, "Montagne"),
			Unknown				(-1, "Non défini");

			private final int    level;
			private final String desc;

		    private Type(int _value, String _desc) {
		    	level = _value;
		    	desc  = _desc;
		    }

		    public int getLevel() {
		        return this.level;
		    }
		    public String getDescription() {
		        return this.desc;
		    }

		}
	}

	// SPECIALIZATION FOR DYNAMICS
	public enum PathType {
		PedestrianWay, 
		CycleWay, 
		RoadWay, 

		RailWay,

		WaterWay, 
		GazWay, 
		OilWay, 
		ElectricityWay, 

		GroundTrajectory, 

		Unknown;
// couloir de vol, Voie navigable, ...
	};

	public enum Direction {
		UNKNOWN  ("Non défini"),
	    NONE     ("Impraticable"),
	    DIRECT   ("Sens Direct"),
	    INDIRECT ("Sens Indirect"),
	    BOTH     ("Double sens");

	    private final String desc;

	    private Direction(String _desc) {
	    	desc  = _desc;
	    }
	
	    public String toString() {
	        return "Direction::" + desc;
	    }
	
	    public static boolean areInverse(Direction _dir0, Direction _dir1) {
	    	if((_dir0 == DIRECT && _dir1 == INDIRECT) || (_dir0 == INDIRECT && _dir1 == DIRECT))
	    		return true;
	    	return false;
	    }
	    public static Direction getInverse(Direction _dir) {
	    	switch(_dir) {
			case DIRECT:	return INDIRECT;
			case INDIRECT:	return DIRECT;
			case BOTH:		return BOTH;
			case NONE:		return NONE;
			case UNKNOWN:	return NONE;
			default:		return NONE;    	
	    	}
	    }
	
	}


	public interface Path extends Object {

		public default boolean			isLoop()			{ return false; }

		public PathType					getType();
		public default Datum 			getDatum()			{ return getGeometry().get(0).getDatum(); }
		public List<GeoCoordinate> 		getGeometry();

		public default GeoCoordinate 	getStart()			{ return getGeometry().get(0); }
		public default GeoCoordinate 	getGeometry(int _i)	{ return getGeometry().get(_i); }
		public default GeoCoordinate 	getWayPoint(int _i)	{ return getGeometry().get(_i); }
		public default GeoCoordinate 	getEnd()			{ return getGeometry().get(getGeometry().size() - 1); }

		public default int 				getLength()			{ return getGeometry().size(); }

	}

	public interface Trajectory extends Object {

		public default Datum 			getDatum()			{ return getGeometry().get(0).getDatum(); }
		public List<GeoDynamics> 		getGeometry();

		public default GeoDynamics	 	getStart()			{ return getGeometry().get(0); }
		public default GeoDynamics 		getWayPoint(int _i)	{ return getGeometry().get(_i); }
		public default GeoDynamics 		getEnd()			{ return getGeometry().get(getGeometry().size() - 1); }

		public default int 				getLength()			{ return getGeometry().size(); }

	}

}
