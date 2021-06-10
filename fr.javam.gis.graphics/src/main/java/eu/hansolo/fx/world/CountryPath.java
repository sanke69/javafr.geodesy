package eu.hansolo.fx.world;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.SVGPath;

import java.util.Locale;

public class CountryPath extends SVGPath {
    private final String  name;
    private final Locale  locale;
    private final Tooltip tooltip;

    public CountryPath(final String _name) {
        this(_name, null);
    }
    public CountryPath(final String _name, final String _content) {
        super();
        name    = _name;
        locale  = new Locale("", _name);
        tooltip = new Tooltip(locale.getDisplayCountry());
        Tooltip.install(this, tooltip);
        if(_content != null)
        	setContent(_content);
    }

    public String 	getName() 		{ return name; }

    public Locale 	getLocale() 	{ return locale; }
    public Tooltip 	getTooltip() 	{ return tooltip; }

}
