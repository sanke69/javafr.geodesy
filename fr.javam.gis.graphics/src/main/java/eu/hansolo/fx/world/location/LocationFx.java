package eu.hansolo.fx.world.location;

import org.kordamp.ikonli.Ikon;

import fr.geodesic.Location;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class LocationFx extends Location {
    private static final int                      DEFAULT_ICON_SIZE = 12;

    private              Color                    color;

    @Deprecated
    private              Ikon                     iconCode;
    private              int                      iconSize;

    private              EventHandler<MouseEvent> mouseEnterHandler;
    private              EventHandler<MouseEvent> mousePressHandler;
    private              EventHandler<MouseEvent> mouseReleaseHandler;
    private              EventHandler<MouseEvent> mouseExitHandler;


    // ******************** Constructors **************************************
    public LocationFx() {
        this("", 0, 0, "", null, null, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String __name) {
        this(__name, 0, 0, "", null, null, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String __name, final int ICON_size) {
        this(__name, 0, 0, "", null, null, ICON_size);
    }
    public LocationFx(final double _latitude, final double _longitude) {
        this("", _latitude, _longitude, "", null, null, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final double _latitude, final double _longitude, final int ICON__size) {
        this("", _latitude, _longitude, "", null, null, ICON__size);
    }
    public LocationFx(final double _latitude, final double _longitude, final Ikon _icon_code) {
        this("", _latitude, _longitude, "", null, _icon_code, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final double _latitude, final double _longitude, final Ikon _icon_code, final int ICON__size) {
        this("", _latitude, _longitude, "", null, _icon_code, ICON__size);
    }
    public LocationFx(final double _latitude, final double _longitude, final Color _color ,final Ikon _icon_code) {
        this("", _latitude, _longitude, "", _color, _icon_code, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final double _latitude, final double _longitude, final Color _color ,final Ikon _icon_code, final int ICON__size) {
        this("", _latitude, _longitude, "", _color, _icon_code, ICON__size);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude) {
        this(_name, _latitude, _longitude, "", null, null, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final int ICON__size) {
        this(_name, _latitude, _longitude, "", null, null, ICON__size);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Ikon _icon_code) {
        this(_name, _latitude, _longitude, "", null, _icon_code, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Ikon _icon_code, final int ICON__size) {
        this(_name, _latitude, _longitude, "", null, _icon_code, ICON__size);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Color _color) {
        this(_name, _latitude, _longitude, "", _color, null, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Color _color, final int ICON__size) {
        this(_name, _latitude, _longitude, "", _color, null, ICON__size);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Color _color, final Ikon _icon_code) {
        this(_name, _latitude, _longitude, "", _color, _icon_code, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final Color _color, final Ikon _icon_code, final int ICON__size) {
        this(_name, _latitude, _longitude, "", _color, _icon_code, ICON__size);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final String _info, final Color _color, final Ikon _icon_code) {
        this(_name, _latitude, _longitude, _info, _color, _icon_code, DEFAULT_ICON_SIZE);
    }
    public LocationFx(final String _name, final double _latitude, final double _longitude, final String _info, final Color _color, final Ikon _icon_code, final int ICON__size) {
    	super(_name, _latitude, _longitude, _info);
        color     = _color;
        iconCode  = _icon_code;
        iconSize  = ICON__size;
    }


    public Color 						getColor() { return color; }
    public void 						setColor(final Color _color) { color = _color; }

    public Ikon 						getIconCode() { return iconCode; }
    public void 						setIconCode(final Ikon _icon_code) { iconCode = _icon_code; }

    public int 							getIconSize() 													{ return iconSize; }
    public void 						setIconSize(final int _size) 									{ iconSize = clamp(6, 24, _size); }

    public EventHandler<MouseEvent> 	getMouseEnterHandler() 											{ return mouseEnterHandler; }
    public void 						setMouseEnterHandler(final EventHandler<MouseEvent> _handler) 	{ mouseEnterHandler = _handler; }

    public EventHandler<MouseEvent> 	getMousePressHandler() 											{ return mousePressHandler; }
    public void 						setMousePressHandler(final EventHandler<MouseEvent> _handler) 	{ mousePressHandler = _handler; }

    public EventHandler<MouseEvent> 	getMouseReleaseHandler() 										{ return mouseReleaseHandler; }
    public void 						setMouseReleaseHandler(final EventHandler<MouseEvent> _handler) { mouseReleaseHandler = _handler;  }

    public EventHandler<MouseEvent> 	getMouseExitHandler() 											{ return mouseExitHandler; }
    public void 						setMouseExitHandler(final EventHandler<MouseEvent> _handler) 	{ mouseExitHandler = _handler; }

    private int clamp(final int MIN_VALUE, final int MAX_VALUE, final int VALUE) {
        if (VALUE < MIN_VALUE) return MIN_VALUE;
        if (VALUE > MAX_VALUE) return MAX_VALUE;
        return VALUE;
    }

}
