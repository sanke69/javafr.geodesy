package fr.gms.navigation.gnss.protocols.nmea;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.gms.navigation.gnss.protocols.nmea.Nmea.Trame;
import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameType;
import fr.gms.navigation.gnss.protocols.nmea.trames.NmeaTrameReader;
import fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers._Readers;
import fr.java.measure.Unit;

public class NmeaCodec {
	static Map<TrameType, Function<NmeaTrameReader, Trame>> registeredTrame = new HashMap<TrameType, Function<NmeaTrameReader, Trame>>();

	static {
		_Readers.registerReaders();
	}

	public static final void registerTrameReader(TrameType _type, Function<NmeaTrameReader, Trame> _parser) {
		registeredTrame.put(_type, _parser);
	}

	public static Unit decodeUnit(char _ch) {
		switch(_ch) {
		case 'C' : return Unit.CELCIUS;
		case 'F' : return Unit.FATHOMS;
		case 'f' : return Unit.FEET;
		case 'M' : return Unit.METRE;
		case 'K' : return Unit.KILOMETRE_PER_HOUR;
		case 'N' : return Unit.KNOT;
		}
		
		return null;
	}

	public Nmea.Trame decode(String _trame) {
		if(!Nmea.Validator.isValid(_trame)) {
			System.err.println("INVALID TRAME:: '" + _trame + "'");
			return null;
		}

		NmeaTrameReader trame = new NmeaTrameReader(_trame);

		Function<NmeaTrameReader, Trame> trameSupplier = registeredTrame.get(trame.getType());
		return trameSupplier != null ? trameSupplier.apply(trame) : trame;
	}

}
