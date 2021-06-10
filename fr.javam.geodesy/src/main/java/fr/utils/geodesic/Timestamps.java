package fr.utils.geodesic;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import fr.java.lang.properties.Timestamp;
import fr.utils.geodesic.adapters.AdapterTimestamp;

public final class Timestamps {

	public static final Timestamp of(long _epoch_ms) {
		return new AdapterTimestamp(_epoch_ms);
	}
	public static final Timestamp of(Instant _instant) {
		return new AdapterTimestamp(_instant.toEpochMilli());
	}
	public static final Timestamp of(LocalDateTime _ldt) {
		return new AdapterTimestamp(_ldt.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
	}
	public static final Timestamp of(LocalDateTime _ldt, ZoneId _zid) {
		return new AdapterTimestamp(_ldt.atZone(_zid).toInstant().toEpochMilli());
	}

	public static final Timestamp epoch() {
		return new AdapterTimestamp(0);
	}
	public static final Timestamp endOfWorld() {
		return Timestamps.of(Long.MAX_VALUE);
	}

	public static final Timestamp now() {
		return new AdapterTimestamp(System.currentTimeMillis());
	}
	public static final Timestamp snk() {
		LocalDateTime ldt = LocalDateTime.of(1981, 4, 27, 5, 55, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Paris"));
		return new AdapterTimestamp(zdt.toInstant().toEpochMilli());
	}
	public static final Timestamp ina() {
		LocalDateTime ldt = LocalDateTime.of(1989, 9, 6, 0, 0, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Africa/Bamako"));
		return new AdapterTimestamp(zdt.toInstant().toEpochMilli());
	}

}