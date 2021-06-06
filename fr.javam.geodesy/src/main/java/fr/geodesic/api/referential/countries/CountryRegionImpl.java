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
