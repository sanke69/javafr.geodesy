package fr.gis.utils;

import java.util.Locale;

public class Distances {

	public enum UnitSystem {
		METRIC, IMPERIAL;
	}

	private static final int	HECTAR_TO_METER		= 10000;
	private static final int	KM_TRESHOLD			= 2000;
	private static final double	FEET				= 0.3048;		 // in meters
	private static final double	INCH				= 0.0256;		 // in meters
	private static final double	SQUARE_M_TO_FOOT	= 10.7643;
	private static final double	HA_TO_ACRES			= 2.471;
	private static final double	MILE_TO_M			= 1609.3;

	private static final String	scale_meter			= "m";
	private static final String	scale_kilometer		= "km";
	private static final String	scale_mile			= "mi";
	private static final String	scale_feet			= "ft";

	private static final String	scale_hectar		= "ha";
	private static final String	scale_acres			= "acres";
	private static final String	scale_square_meter	= "\u33A1";
	private static final String	scale_square_feet	= "square feet";

	private static final long[] SCALE_VALUES_METRIC = new long[] { 50, 100, 250, 500, 1000, 2000, 4000, 10000, 15000,
			30000, 50000, 100000, 250000, 500000, 1000000, 2000000, 4000000 };

	private static final long[] SCALE_VALUES_IMPERIAL = new long[] { 200, 500, 1000, 2000, 4000, 5280, 10560, 26400,
			52800, 105600, 158400, 264000, 792000, 1584000, 2640000, 5280000, 10560000 };

	public static long getScaleValue(int zoom) {
		UnitSystem unitSystem = getUnitSystem();

		switch (unitSystem) {
		case METRIC:
			return getScaleValueMetric(zoom);
		case IMPERIAL:
			return getScaleValueImperial(zoom);
		default:
			return getScaleValueMetric(zoom);
		}
	}

	public static UnitSystem getUnitSystem() {
		Locale defaultLocale = Locale.getDefault();
		String cc = defaultLocale.getCountry();

		if("US".equals(cc) || "GB".equals(cc) || "CA".equals(cc)) {
			return UnitSystem.IMPERIAL;
		} else {
			return UnitSystem.METRIC;
		}
	}

	public static String formatLength(double length) {
		UnitSystem unitSystem = getUnitSystem();
		String out = null;
		switch (unitSystem) {
		case IMPERIAL:
			out = formatLengthImperial(length);
			break;
		case METRIC:
			out = formatLengthMetric(length);
			break;
		default:
		}
		return out;
	}

	public static String formatLengthMetric(double length) {
		if(length > KM_TRESHOLD) {
			return String.format("%.3f %s", length / 1000, scale_kilometer);
		} else {
			return String.format("%.1f ", length, scale_meter);
		}
	}

	public static String formatLengthImperial(double length) {
		if(length > MILE_TO_M) {
			return String.format("%.3f %s", length / MILE_TO_M, scale_mile);
		} else {
			return String.format("%.1f %s", length / FEET, scale_feet);
		}
	}

	public static String formatArea(double area) {
		UnitSystem unitSystem = getUnitSystem();
		String out = null;
		switch (unitSystem) {
		case IMPERIAL:
			out = formatAreaImperial(area);
			break;
		case METRIC:
			out = formatAreaMetric(area);
			break;
		default:
		}
		return out;
	}

	public static String formatAreaMetric(double area) {
		if(area > 1) {
			return String.format("%.3f %s", area, scale_hectar);
		} else {
			return String.format("%.2f %s", area * HECTAR_TO_METER, scale_square_meter);
		}
	}

	public static String formatAreaImperial(double area) {
		if(area > (1 / HA_TO_ACRES)) {
			return String.format("%.3f %s", area * HA_TO_ACRES, scale_acres);
		} else {
			return String.format("%.2f %s", area * HECTAR_TO_METER * SQUARE_M_TO_FOOT, scale_square_feet);
		}
	}

	private static long getScaleValueMetric(int zoom) { // km/m
		if(zoom < 0) {
			throw new IllegalArgumentException("zoom must be positive");
		}
		if(zoom > SCALE_VALUES_METRIC.length) {
			throw new IllegalAccessError(String.format("maximum zoom value can be %d", SCALE_VALUES_METRIC.length));
		}

		return SCALE_VALUES_METRIC[zoom - 1];
	}

	private static long getScaleValueImperial(int zoom) { // mi/ft
		if(zoom < 0) {
			throw new IllegalArgumentException("zoom must be positive");
		}
		if(zoom > SCALE_VALUES_IMPERIAL.length) {
			throw new IllegalAccessError(String.format("maximum zoom value can be %d", SCALE_VALUES_IMPERIAL.length));
		}

		return SCALE_VALUES_IMPERIAL[zoom - 1];
	}

	public static double getUnitToMetersRatio() {
		UnitSystem systemUnit = getUnitSystem();

		switch (systemUnit) {
		case METRIC:
			return 1d;
		case IMPERIAL:
			return FEET;
		default:
			return 1d;
		}

	}

}