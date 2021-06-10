package fr.gis.api.road;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;

public interface Road {

	public enum AdministrativeStatus {
		Public	 ("Public"),
		Private	 ("Privé");

	    private final String desc;

	    private AdministrativeStatus(String _desc) {
	    	desc = _desc;
	    }

		public String 	toString() {
	        return "Status::" + desc;
	    }

	}

	public enum Category {
		Motorway			("Autoroute"),
		MajorRoad			("Route Nationale"),
		OtherMajorRoad		("Route Départementale"),
		SecondaryRoad     	("Route InterCommunale"),
		LocalConnectingRoad ("Route Communale"),
		LocalRoadLvl1		("Route Locale de Niveau 1 - Chemin Communale"),
		LocalRoadLvl2		("Route Locale de Niveau 2"),
		LocalRoadLvl3		("Route Locale de Niveau 3 - Piste Forestière"),
		OtherRoad			("Autre"),
		BicybleWay          ("Piste cyclable"),
		WalkWay             ("Sentier piéton"),
		Unknown				("Non défini");

		private final String desc;

	    private Category(String _desc) {
	    	desc  = _desc;
	    }

	    public String getDescription() {
	        return this.desc;
	    }

	}

	public enum SubCategory {
		PartOfMotorway				("Quasi-autoroute"),
		PartOfMultiCarriageWay		("Route à 2 chaussées"),
		PartOfSingleCarriageWay		("Route à 1 chaussée"),
		PartOfRoundAbout  			("Rond Point"),
		PartOfParkingPlace 			("Zone de parking"),
		PartOfParkingGarage			("Zone de garage"),
		UnstructureTrafficSquare	("Zone de trafic non structurée"),
		PartOfSlipRoad				("Bretelle"),
		PartOfServiceRoad			("Route de service"),
		ExitEntranceCarPark			("Entrée de parking"),
		PartOfPedestrianZone 		("Quasi-autoroute"),
		PartOfWalkWay				("Chemin piéton"),
		PartOfBicycleWay			("Piste cyclable"),
		SpecialTrafficFigures		("Quasi-autoroute"),
		RoadForAuthorities			("Route pour autorités"),
		NotApplicable				("Non défini");

		private final String desc;

		private SubCategory(String _desc) {
			desc = _desc;
		}

	    public String getDescription() {
	        return desc;
	    }

	}

	public enum Condition {
		NotApplicable 		("Non défini"),
		PavedRoad 			("Route pavé"),
		UnpavedRoad 		("Route pavé avec nid de poules"), // TODO:: 
		WorkInProgressRoad	("Route en travaux"),
		RoadInPoorCondition	("Route dégradée");
		
		private final String desc;
		
		private Condition(String _desc) {
			desc  = _desc;
		}

		public String getDescription() {
	        return desc;
	    }

	}

	public enum FeatureType {
		Road				("Route"),

		UserLane			("Voie de circulation"),
		ServiceLane			("Route de service"),
		EmergencyLane		("Bande d'arrêt d'urgence"),

		PhysicalDivider		("PhysicalDivider"),
		BusBay				("Stationnement bus"),
		WaitingBay			("Dépose bus"),
		BusStop				("Arrêt de bus"),
		Parking				("Parking"),
		UTurnAcroossDivider	("Rond-Point"),
		SlipRoad			("Rond-Point"),
		BicybleStopArea		("Stationnement deux roues"),
		Bridge				("Pont"),
		Viaduct				("Viaduc"),
		Aqueduct			("Aqueduc"),
		Tunnel				("Tunnel"),
		TrafficIsland		("Rond-Point"),
		RoadSide			("Rocade"),
		Sidewalk			("Chemin de randonnée"),
		BicyclePath			("Piste cyclable"),
		NotDefined			("Non défini");
	
		private final String desc;
	
	    private FeatureType(String _desc) {
	    	desc  = _desc;
	    }

		public String getDescription() {
	        return this.desc;
	    }

	}

	// TRAFFIC SIGNS
	public interface TraficSign extends Gis.Node {
		public static final String	MSL_VALUE		= "MandatorySpeedLimit";
		@Deprecated
		public static final String	TRAFICLIGHT_ID	= "TraficLightID";
	
		public enum Type {
			MANDATORY_SPEED_LIMIT, 
			TRAFIC_LIGHT, 
			YIELD,
			STOP, 
			CROSS_WALK;
		
		}
	
		Road.TraficSign.Type			getType();
	
		Optional<RoadCoordinate> 	getMapInfos();
	
	}

	// MARKINGS
	public enum MarkingType {
		Continuous	("Ligne Continue");
		
		private final String desc;
		
		private MarkingType(String _desc) {
			desc  = _desc;
		}

		public String getDescription() {
	        return desc;
	    }

	}

	// LANE
	public interface Lane {
		public double getOffset(int _segmentId); // 
	}

	// ELEMENT
	public interface Element extends Gis.Curve {

		public interface Linkable extends Element {

			public void 			addPreviousElement(Element _elt);
			public void 			addPreviousElements(Element... _elts);
			
			public void 			addNextElement(Element _elt);
			public void 			addNextElements(Element... _elts);

		}

		public Road.Category 		getCategory();
		public Road.SubCategory 	getNature();
		public Gis.Direction 		getDrivingWay();
		public int 					getLaneCount();
		public EnumSet<Gis.User>	getAllowedUsers();

		public double 				getLength();

		public double 				getHeading();
		public double 				getHeading(int _idSeg);

		public GeoCoordinate 		getStart();
		public GeoCoordinate 		getEnd();

		// Road Graph Properties
		public Set<Element> 		getPreviousElements();
		public Set<Element> 		getNextElements();

	}

}
