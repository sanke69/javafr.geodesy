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

import java.util.HashSet;
import java.util.Set;

public class CountryRegionImpl implements CountryRegion {
    private String        name;
    private Set<Country> countries;

    public CountryRegionImpl(final String NAME, final Country... _countries) {
        name      = NAME;
        countries = new HashSet<Country>(_countries.length);
        for (Country country : _countries) { countries.add(country); }
    }

    @Override
    public String name() { return name; }

    @Override
    public Set<Country> getCountries() { return countries; }

}
