package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import java.util.List;

import fr.gms.navigation.gnss.properties.values.SatelliteInfo;

public interface GSVTrame {

	int 					getSatelliteCount();
	List<SatelliteInfo> 	getSatelliteInfo();

	int 					getSentenceCount();
	int 					getSentenceIndex();

	boolean 				isFirst();
	boolean 				isLast();

}
