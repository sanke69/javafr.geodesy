package fr.gms.navigation.gnss.protocols.nmea;

import java.util.regex.Pattern;

public interface Nmea {

	byte   BEGIN_CHAR         = '$';
	byte   BEGIN_CHAR_ALT     = '!';
	byte   FIELD_DELIMITER    = ',';
	byte[] TERMINATOR         = new byte[] { '\r', '\n' };
	byte   CHECKSUM_DELIMITER = '*';
	int    MAX_LENGTH         = 82;

	public static enum TrameEmitter {
		GP;

	}

	public enum TrameType {
		AAM("AAM", "Waypoint Arrival Alarm"),
		ALM("ALM", "GPS Almanac Data"),
		APA("APA", "Autopilot Sentence 'A'"),
		APB("APB", "Autopilot Sentence 'B'"),
		ASD("ASD", "Autopilot System Data"),
		BEC("BEC", "Bearing & Distance to Waypoint, Dead Reckoning"),
		BOD("BOD", "Bearing, Origin to Destination"),
		BWC("BWC", "Bearing & Distance to Waypoint, Great Circle"),
		BWR("BWR", "Bearing & Distance to Waypoint, Rhumb Line"),
		BWW("BWW", "Bearing, Waypoint to Waypoint"),
		CUR("CUR", "Water Current Layer"),
		DBT("DBT", "Depth Below Transducer"),
		DCN("DCN", "Decca Position"),
		DPT("DPT", "Depth"),
		DTM("DTM", "Datum Reference"),
		FSI("FSI", "Frequency Set Information"),
		GBS("GBS", "GNSS Satellite Fault Detection"),
		GGA("GGA", "Global Positioning System Fix Data"),
		GGL("GGL", "???"),
		GLC("GLC", "Geographic Position, Loran-C"),
		GLL("GLL", "Geographic Position, Latitude/Longitude"),
		GNS("GNS", "GNSS Fix Data"),
		GSA("GSA", "GNSS DOP and Active Satellites"),
		GST("GST", "GNSS Pseudo range Error Statistics"),
		GSV("GSV", "GNSS Satellites in View"),
		GXA("GXA", "TRANSIT Position"),
		HDG("HDG", "Heading, Deviation & Variation"),
		HDT("HDT", "Heading, True"),
		HMR("HMR", "Heading Monitor Receive"),
		HMS("HMS", "Heading Monitor Set"),
		HSC("HSC", "Heading Steering Command"),
		HTC("HTC", "Heading/Track Control Command"),
		HTD("HTD", "Heading/Track Control Data"),
		LCD("LCD", "Loran-C Signal Data"),
		MTA("MTA", "Air Temperature (to be phased out)"),
		MTW("MTW", "Water Temperature"),
		MWD("MWD", "Wind Direction"),
		MWV("MWV", "Wind Speed and Angle"),
		OLN("OLN", "Omega Lane Numbers"),
		OSD("OSD", "Own Ship Data"),
		R00("R00", "Waypoint active route (not standard)"),
		RMA("RMA", "Recommended Minimum Specific Loran-C Data"),
		RMB("RMB", "Recommended Minimum Navigation Information"),
		RMC("RMC", "Recommended Minimum Specific GPS/TRANSIT Data"),
		ROT("ROT", "Rate of Turn"),
		RPM("RPM", "Revolutions"),
		RSA("RSA", "Rudder Sensor Angle"),
		RSD("RSD", "RADAR System Data"),
		RTE("RTE", "Routes"),
		SFI("SFI", "Scanning Frequency Information"),
		STN("STN", "Multiple Data ID"),
		TRF("TRF", "Transit Fix Data"),
		TTM("TTM", "Tracked Target Message"),
		VBW("VBW", "Dual Ground/Water Speed"),
		VDR("VDR", "Set and Drift"),
		VHW("VHW", "Water Speed and Heading"),
		VLW("VLW", "Distance Traveled through the Water"),
		VPW("VPW", "Speed, Measured Parallel to Wind"),
		VTG("VTG", "Track Made Good and Ground Speed"),
		WCV("WCV", "Waypoint Closure Velocity"),
		WNC("WNC", "Distance, Waypoint to Waypoint"),
		WPL("WPL", "Waypoint Location"),
		XDR("XDR", "Transducer Measurements"),
		XTE("XTE", "Cross-Track Error, Measured"),
		XTR("XTR", "Cross-Track Error, Dead Reckoning"),
		ZDA("ZDA", "Time & Date"),
		ZFO("ZFO", "UTC & Time from Origin Waypoint"),
		ZTG("ZTG", "UTC & Time to Destination Waypoint");

		String header, description;
	
		private TrameType(String _header, String _description) {
			header      = _header;
			description = _description;
		}
		
		public String chars() {
			return header;
		}
		public String description() {
			return description;
		}

	}

	public interface Trame {

		public static String emitterAsString(String _nmea) {
			return _nmea.substring(1, 3);
		}
		public static String typeAsString(String _nmea) {
			return _nmea.substring(3, 6);
		}

		int 			getFieldCount();

		char			getStartByte();
		TrameEmitter	getEmitter();
		TrameType 		getType();

//		boolean 		isValid();

	}

	public static final class Checksum {

		public static String add(String nmea) {
			String str = nmea.substring(0, index(nmea));
			String sum = calculate(str);
			return String.format("%s%c%s", str, CHECKSUM_DELIMITER, sum);
		}
	
		public static String calculate(String nmea) {
			return xor(nmea.substring(1, index(nmea)));
		}
	
		public static String xor(String str) {
			int sum = 0;
			for (int i = 0; i < str.length(); i++) {
				sum ^= (byte) str.charAt(i);
			}
			return String.format("%02X", sum);
		}

		public static int index(String nmea) {
			return nmea.indexOf(CHECKSUM_DELIMITER) > 0 ? nmea.indexOf(CHECKSUM_DELIMITER) : nmea.length();
		}
	
	}

	public static final class Validator {
	
		private static final Pattern reChecksum   = Pattern.compile("^[$|!]{1}[A-Z0-9]{3,10}[,][\\x20-\\x7F]*[*][A-F0-9]{2}(\\r|\\n|\\r\\n|\\n\\r){0,1}$");
		private static final Pattern reNoChecksum = Pattern.compile("^[$|!]{1}[A-Z0-9]{3,10}[,][\\x20-\\x7F]*(\\r|\\n|\\r\\n|\\n\\r){0,1}$");
	
		private Validator() {}

		public static boolean isTrame(String nmea) {
			if (nmea == null || "".equals(nmea))
				return false;
	
			if(nmea.indexOf(BEGIN_CHAR) != 0)
				return false;
			if(nmea.indexOf(CHECKSUM_DELIMITER) == -1)
				return false;
			if(nmea.indexOf(new String(TERMINATOR)) != nmea.length() - 2)
				return false;

			if (Checksum.index(nmea) == nmea.length())
				return reNoChecksum.matcher(nmea).matches();
	
			return reChecksum.matcher(nmea).matches();
		}

		public static boolean isValid(String nmea) {
			return isValid(nmea, true);
		}
		public static boolean isValid(String nmea, boolean _withChecksum) {
			boolean isValid = false;
	
			if(Validator.isTrame(nmea)) {
				int i = nmea.indexOf(CHECKSUM_DELIMITER);
				if (i > 0) {
					String sum = nmea.substring(++i, nmea.length() - TERMINATOR.length);
					isValid = sum.equals(Checksum.calculate(nmea));
				} else {
					// no checksum
					isValid = true;
				}
			}
	
			return isValid;
		}

	}

}
