package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import java.util.ArrayList;
import java.util.List;

import fr.gms.navigation.gnss.properties.constants.RouteType;
import fr.gms.navigation.gnss.protocols.exceptions.DataNotAvailableException;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.RTETrame;

class RTETrameReader extends NmeaTrameReader implements RTETrame {

	// fields indices
	private static final int NUMBER_OF_SENTENCES 	= 0;
	private static final int SENTENCE_NUMBER 		= 1;
	private static final int STATUS 				= 2;
	private static final int ROUTE_ID 				= 3;
	private static final int FIRST_WPT 				= 4;

	RTETrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.RTE);
	}

	@Override public String getRouteId() {
		return getStringValue(ROUTE_ID);
	}

	@Override public int getSentenceCount() {
		return getIntValue(NUMBER_OF_SENTENCES);
	}

	@Override public int getSentenceIndex() {
		return getIntValue(SENTENCE_NUMBER);
	}

	@Override public int getWaypointCount() {
		return getWaypointIds().length;
	}

	@Override public String[] getWaypointIds() {

		List<String> temp = new ArrayList<String>();

		for (int i = FIRST_WPT; i < getFieldCount(); i++) {
			try {
				temp.add(getStringValue(i));
			} catch (DataNotAvailableException e) {
				// nevermind empty fields
			}
		}

		return temp.toArray(new String[temp.size()]);
	}

	@Override public boolean isActiveRoute() {
		return getCharValue(STATUS) == RouteType.ACTIVE.toChar();
	}

	@Override public boolean isFirst() {
		return (getSentenceIndex() == 1);
	}

	@Override public boolean isLast() {
		return (getSentenceIndex() == getSentenceCount());
	}

	@Override public boolean isWorkingRoute() {
		return getCharValue(STATUS) == RouteType.WORKING.toChar();
	}

}
