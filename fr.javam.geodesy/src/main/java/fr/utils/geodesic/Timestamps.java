/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
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