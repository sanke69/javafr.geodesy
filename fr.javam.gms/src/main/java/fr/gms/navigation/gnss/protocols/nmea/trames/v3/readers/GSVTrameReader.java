package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import java.util.ArrayList;
import java.util.List;

import fr.gms.navigation.gnss.properties.values.SatelliteInfo;
import fr.gms.navigation.gnss.protocols.exceptions.DataNotAvailableException;
import fr.gms.navigation.gnss.protocols.nmea.Nmea;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.GSVTrame;

class GSVTrameReader extends NmeaTrameReader implements GSVTrame {

	// field indices
	private static final int NUMBER_OF_SENTENCES = 0;
	private static final int SENTENCE_NUMBER = 1;
	private static final int SATELLITES_IN_VIEW = 2;

	// satellite id fields
	private static final int[] ID_FIELDS = { 3, 7, 11, 15 };

	// satellite data fields, relative to each id field
	private static final int ELEVATION = 1;
	private static final int AZIMUTH = 2;
	private static final int NOISE = 3;

	GSVTrameReader(NmeaTrameReader _nmeaTrame) {
		super(_nmeaTrame, Nmea.TrameType.GSV);
	}

	public int getSatelliteCount() {
		return getIntValue(SATELLITES_IN_VIEW);
	}

	public List<SatelliteInfo> getSatelliteInfo() {

		List<SatelliteInfo> satellites = new ArrayList<SatelliteInfo>(4);

		for (int idf : ID_FIELDS) {
			try {
				String id = getStringValue(idf);
				int elev = getIntValue(idf + ELEVATION);
				int azm = getIntValue(idf + AZIMUTH);
				int snr = getIntValue(idf + NOISE);
				satellites.add(new SatelliteInfo(id, elev, azm, snr));
			} catch (DataNotAvailableException e) {
				// nevermind missing satellite info
			} catch (IndexOutOfBoundsException e) {
				// less than four satellites, give up
				break;
			}
		}

		return satellites;
	}

	public int getSentenceCount() {
		return getIntValue(NUMBER_OF_SENTENCES);
	}
	public int getSentenceIndex() {
		return getIntValue(SENTENCE_NUMBER);
	}

	public boolean isFirst() {
		return (getSentenceIndex() == 1);
	}
	public boolean isLast() {
		return (getSentenceIndex() == getSentenceCount());
	}

}
