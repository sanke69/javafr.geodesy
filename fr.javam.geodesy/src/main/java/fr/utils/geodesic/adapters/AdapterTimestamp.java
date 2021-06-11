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
package fr.utils.geodesic.adapters;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.Timestamp;

public class AdapterTimestamp implements Timestamp {
	public static final long S_IN_MS = 1000;
	public static final long M_IN_MS = 60 * S_IN_MS;
	public static final long H_IN_MS = 60 * M_IN_MS;
	public static final long D_IN_MS = 24 * H_IN_MS; 
	
	public long epoch_ms;

	public AdapterTimestamp(long _t_ms) {
		epoch_ms = _t_ms;
	}
	public AdapterTimestamp(double _t_s) {
		epoch_ms = (long) (_t_s * S_IN_MS);
	}

	public Long delta(Timestamp _timestamp) {
		return epoch_ms - _timestamp.toEpochMilli();
	}

	public Timestamp plus(long _ms) {
		return new AdapterTimestamp(epoch_ms + _ms);
	}
	public Timestamp plusEquals(long _ms) {
		epoch_ms += _ms;
		return this;
	}

	public Timestamp minus(long _ms) {
		return new AdapterTimestamp(epoch_ms - _ms);
	}
	public Timestamp minusEquals(long _ms) {
		epoch_ms -= _ms;
		return this;
	}

	public boolean   before(Timestamp _timestamp) {
		return epoch_ms - _timestamp.toEpochMilli() < 0 ? true : false;
	}
	public boolean   after(Timestamp _timestamp) {
		return epoch_ms - _timestamp.toEpochMilli() > 0 ? true : false;
	}
	public boolean   between(Timestamp _from, Timestamp _to) {
		return epoch_ms - _from.toEpochMilli() >= 0 && epoch_ms - _to.toEpochMilli() <= 0 ? true : false;
	}

	public Long      toEpochMilli() {
		return epoch_ms;
	}

	public Timestamp clone() {
		return new AdapterTimestamp(epoch_ms);
	}
	
    public boolean   equals(Object obj) {
    	if(obj instanceof Integer)
    		return epoch_ms == ((Integer) obj).longValue();
    	if(obj instanceof Long)
    		return epoch_ms == ((Long) obj).longValue();
    	if(obj instanceof Timestamp)
    		return epoch_ms == ((Timestamp) obj).toEpochMilli();
    	
    	throw new NotYetImplementedException();
    }

	public String    toString() {
/**
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Locale.FRENCH )
			                     .withZone( ZoneId.systemDefault() );
/*/
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone( ZoneId.systemDefault() );
/**/
		return formatter.format( Instant.ofEpochMilli(epoch_ms) );
	}

}
