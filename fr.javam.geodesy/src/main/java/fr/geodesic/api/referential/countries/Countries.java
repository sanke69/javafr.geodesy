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
package fr.geodesic.api.referential.countries;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

public final class Countries {

	public static final Map<String, String> english2iso2;
	public static final Map<String, String> french2iso2;

    static {
    	english2iso2 = new HashMap<String, String>();
    	french2iso2  = new HashMap<String, String>();

        for(Locale locale : Locale.getAvailableLocales()) {
            try {
            	String country_code = locale.getCountry();
            	String english_name = locale.getDisplayCountry(Locale.ENGLISH);
            	String french_name  = locale.getDisplayCountry(Locale.FRENCH);

            	english2iso2 . put(english_name, country_code);
            	french2iso2  . put(french_name, country_code);
            } catch(MissingResourceException mre) { }
        }
    }

    public static Country of(String _value) {
    	try {
    		return Country.valueOf(_value);
    	} catch(Exception e) {
    		return Country.UNKNOWN;
    	}
    }
    public static Country ofEnglish(String _name) {
    	try {
            String ccode = english2iso2.get(_name);

            if(ccode != null && !ccode.isBlank())
            	return of(ccode);
            else if(_name.length() == 2 && english2iso2.containsValue(_name))
            	return of(ccode = _name);
            else
        		return Country.UNKNOWN;

    	} catch(Exception e) {
    		return Country.UNKNOWN;
    	}
    }

}
