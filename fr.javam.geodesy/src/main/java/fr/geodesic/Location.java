package fr.geodesic;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.utils.GeoCoordinates;

@Deprecated
public class Location implements GeoLocalized {
    private              String                   name;
    private              GeoCoordinate            position;
    @Deprecated
    private              double                   latitude;
    @Deprecated
    private              double                   longitude;
    private              String                   info;

    public Location() {
        this("", 0, 0, "");
    }
    public Location(final GeoCoordinate _coordinate) {
        this("", _coordinate, "");
    }
    public Location(final double _latitude, final double _longitude) {
        this("", _latitude, _longitude, "");
    }
    public Location(final String __name) {
        this(__name, 0, 0, "");
    }
    public Location(final String _name, final GeoCoordinate _coordinate) {
        this(_name, _coordinate, "");
    }
    public Location(final String _name, final double _latitude, final double _longitude) {
        this(_name, _latitude, _longitude, "");
    }
    public Location(final String _name, final GeoCoordinate _coordinate, final String _info) {
    	super();
    	name      = _name;
    	position  = _coordinate;
        latitude  = _coordinate.asWGS84().getLatitude();
        longitude = _coordinate.asWGS84().getLongitude();
        info      = _info;
    }
    public Location(final String _name, final double _latitude, final double _longitude, final String _info) {
    	super();

    	name      = _name;
        latitude  = _latitude;
        longitude = _longitude;
    	position  = GeoCoordinates.newWGS84(_longitude, _latitude);
        info      = _info;
    }

    public String 			getName() 									{ return name; }
    public void 			setName(final String _name) 				{ name = _name; }

    public GeoCoordinate	getPosition() 								{ return position; }
    public void 			setPosition(final GeoCoordinate _position) 	{ position = _position; }

    public double 			getLatitude() { return latitude; }
    public void 			setLatitude(final double _latitude) 		{ latitude = _latitude; }

    public double 			getLongitude() { return longitude; }
    public void 			setLongitude(final double _longitude)	 	{ longitude = _longitude; }

    public String 			getInfo() 									{ return info; }
    public void 			setInfo(final String _info) 				{ info = _info; }

}
