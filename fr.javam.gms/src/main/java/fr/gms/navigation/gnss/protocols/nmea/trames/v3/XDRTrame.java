package fr.gms.navigation.gnss.protocols.nmea.trames.v3;

import java.util.List;

import fr.java.patterns.measurable.Measurement;

public interface XDRTrame {

	List<Measurement> getMeasurements();

}
