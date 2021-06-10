package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import fr.gms.navigation.gnss.properties.constants.DataStatus;
import fr.gms.navigation.gnss.properties.constants.Direction;

public interface APBTrame {
	static final int SIGNAL_STATUS            = 0;
	static final int CYCLE_LOCK_STATUS        = 1;
	static final int XTE_DISTANCE             = 2;
	static final int XTE_STEER_TO             = 3;
	static final int XTE_UNITS                = 4;
	static final int CIRCLE_STATUS            = 5;
	static final int PERPENDICULAR_STATUS     = 6;
	static final int BEARING_ORIGIN_DEST      = 7;
	static final int BEARING_ORIGIN_DEST_TYPE = 8;
	static final int DEST_WAYPOINT_ID         = 9;
	static final int BEARING_POS_DEST         = 10;
	static final int BEARING_POS_DEST_TYPE    = 11;
	static final int HEADING_TO_DEST          = 12;
	static final int HEADING_TO_DEST_TYPE     = 13;

	/** Kilometers */
	public static final char KM = 'K';

	/** Nautical miles */
	public static final char NM = 'N';

	public interface Editable {

		void setArrivalCircleEntered(boolean isEntered);
		void setBearingOriginToDestination(double bearing);
		void setBearingOriginToDestionationTrue(boolean isTrue);
		void setBearingPositionToDestination(double bearing);
		void setBearingPositionToDestinationTrue(boolean isTrue);
		void setCrossTrackError(double distance);
		void setCrossTrackUnits(char unit);
		void setCycleLockStatus(DataStatus status);
		void setDestinationWaypointId(String id);
		void setHeadingToDestination(double heading);
		void setHeadingToDestinationTrue(boolean isTrue);
		void setPerpendicularPassed(boolean isPassed);
		void setStatus(DataStatus status);
		void setSteerTo(Direction direction);

	}
	
	
	double 		getBearingPositionToDestination();
	double 		getBearingOriginToDestination();
	double 		getCrossTrackError();
	char		getCrossTrackUnits();
	DataStatus 	getCycleLockStatus();
	String 		getDestionationWaypointId();
	double 		getHeadingToDestionation();
	DataStatus 	getStatus();
	Direction 	getSteerTo();
	boolean 	isArrivalCircleEntered();
	boolean 	isBearingOriginToDestionationTrue();
	boolean 	isBearingPositionToDestinationTrue();
	boolean 	isHeadingToDestinationTrue();
	boolean 	isPerpendicularPassed();

}
