package eu.hansolo.fx.world;

import static javafx.scene.input.MouseEvent.MOUSE_ENTERED;
import static javafx.scene.input.MouseEvent.MOUSE_EXITED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import fr.geodesic.api.referential.countries.Country;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.WeakEventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class WorldGroup extends Group {

    public enum Resolution { 
    	HI_RES ("fr/geodesy/world/hires.properties"), 
    	LO_RES ("fr/geodesy/world/lores.properties");

    	String resource;

    	Resolution(String _resource) { resource = _resource; }

    };

    private static final String PATH = "/ssd/src/JavaFR/Modules.Extensions/Geodesy/fr.geodesy.graphics/src/main/resources/fr/geodesy/world/";

    private static final double                          PREFERRED_WIDTH  = 1009;
    private static final double                          PREFERRED_HEIGHT = 665;
    protected            Map<String, List<CountryPath>>  countryPaths;
    public  static		 Map<Country, Color>			 countryColor = new HashMap<Country, Color>();

    private              Properties                      resolutionProperties;

    public WorldGroup(final Resolution RESOLUTION) {
		super();
		fillColor            = Color.ANTIQUEWHITE;
        strokeColor          = Color.BLACK; //Color.DARKOLIVEGREEN;
        hoverColor           = Color.OLIVE;
        pressedColor         = Color.DARKSEAGREEN;
        selectedColor        = Color.LIGHTSEAGREEN;

    	resolutionProperties = readProperties(RESOLUTION.resource);

    	hoverEnabled         = new SimpleBooleanProperty(WorldGroup.this, "hoverEnabled", true);
        selectionEnabled     = new SimpleBooleanProperty(WorldGroup.this, "selectionEnabled", false);
        selectedCountry      = new SimpleObjectProperty<Country>(WorldGroup.this, "selectedCountry");
        
        /*
        zoomEnabled          = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                if (null == getScene()) return;
                if (get()) {
                    getScene().addEventFilter(ScrollEvent.ANY, _scrollEventHandler);
                } else {
                    getScene().removeEventFilter(ScrollEvent.ANY, _scrollEventHandler);
                }
            }
            @Override public Object getBean() { return World.this; }
            @Override public String getName() { return "zoomEnabled"; }
        };*/

    	countryPaths = createCountryPaths();
    	initInternalHandlers();
    	initGraphics();
    }

    private void initInternalHandlers() {

        _mouseEnterHandler   = evt -> handleMouseEvent(evt, mouseEnterHandler);
        _mousePressHandler   = evt -> handleMouseEvent(evt, mousePressHandler);
        _mouseReleaseHandler = evt -> handleMouseEvent(evt, mouseReleaseHandler);
        _mouseExitHandler    = evt -> handleMouseEvent(evt, mouseExitHandler);

    }
    private void initGraphics() {
        getStyleClass().add("world");

        Color fill   = Color.BLANCHEDALMOND;
        Color stroke = Color.BLACK;

        countryPaths.forEach((name, pathList) -> {
            Country country = Country.valueOf(name);
            pathList.forEach(path -> {
                path.setFill(countryColor.containsKey(country) ? fill : countryColor.get(country));
                path.setStroke(stroke);
                path.setStrokeWidth(0.2);
                path.setOnMouseEntered(new WeakEventHandler<>(_mouseEnterHandler));
                path.setOnMousePressed(new WeakEventHandler<>(_mousePressHandler));
                path.setOnMouseReleased(new WeakEventHandler<>(_mouseReleaseHandler));
                path.setOnMouseExited(new WeakEventHandler<>(_mouseExitHandler));
            });
            getChildren().addAll(pathList);
        });

    }

    public Map<String, List<CountryPath>> getCountryPaths() { return countryPaths; }

    private double[] getBounds(final Country... COUNTRIES) { return getBounds(Arrays.asList(COUNTRIES)); }
    private double[] getBounds(final Collection<Country> COUNTRIES) {
        double upperLeftX  = PREFERRED_WIDTH;
        double upperLeftY  = PREFERRED_HEIGHT;
        double lowerRightX = 0;
        double lowerRightY = 0;
        for (Country country : COUNTRIES) {
            List<CountryPath> paths = countryPaths.get(country.iso2());
            for (int i = 0; i < paths.size(); i++) {
                CountryPath path   = paths.get(i);
                Bounds      bounds = path.getLayoutBounds();
                upperLeftX  = Math.min(bounds.getMinX(), upperLeftX);
                upperLeftY  = Math.min(bounds.getMinY(), upperLeftY);
                lowerRightX = Math.max(bounds.getMaxX(), lowerRightX);
                lowerRightY = Math.max(bounds.getMaxY(), lowerRightY);
            }
        }
        return new double[]{ upperLeftX, upperLeftY, lowerRightX, lowerRightY };
    }

    private void setFillAndStroke() {
        countryPaths.keySet().forEach(name -> {
            Country country = Country.valueOf(name);
            setCountryFillAndStroke(country, !countryColor.containsKey( country ) ? getFillColor() : countryColor.get( country ), getStrokeColor());
        });
    }
    private void setCountryFillAndStroke(final Country COUNTRY, final Color FILL, final Color STROKE) {
        List<CountryPath> paths = countryPaths.get(COUNTRY.iso2());
        for (CountryPath path : paths) {
            path.setFill(FILL);
            path.setStroke(STROKE);
        }
    }

    private Map<String, List<CountryPath>> createCountryPaths() {
        Map<String, List<CountryPath>> countryPaths = new HashMap<>();
        resolutionProperties.forEach((key, value) -> {
            String            name     = key.toString();
            List<CountryPath> pathList = new ArrayList<>();
            for (String path : value.toString().split(";")) { pathList.add(new CountryPath(name, path)); }
            countryPaths.put(name, pathList);
        });
        return countryPaths;
    }

    private Properties readProperties(final String FILE_NAME) {
    	final Properties  PROPERTIES = new Properties();
    	String FILE_NAME0 = FILE_NAME.substring(FILE_NAME.lastIndexOf('/') + 1);
    	FILE_NAME0 = PATH + FILE_NAME0;
    	try(InputStream resourceStream = Files.newInputStream(Paths.get(FILE_NAME0))) {
            PROPERTIES.load(resourceStream);
        } catch (IOException exception) {
            System.out.println(exception);
        }
    	/*
        final ClassLoader LOADER     = this.getClass().getClassLoader(); //Thread.currentThread().getContextClassLoader();
        final Properties  PROPERTIES = new Properties();
        try(InputStream resourceStream = LOADER.getResourceAsStream(FILE_NAME0)) {
            PROPERTIES.load(resourceStream);
        } catch (IOException exception) {
            System.out.println(exception);
        }*/
        return PROPERTIES;
    }

//    class Behaviour {
        protected            EventHandler<MouseEvent>        _mouseEnterHandler;
        protected            EventHandler<MouseEvent>        _mousePressHandler;
        protected            EventHandler<MouseEvent>        _mouseReleaseHandler;
        protected            EventHandler<MouseEvent>        _mouseExitHandler;

        private              EventHandler<MouseEvent>        mouseEnterHandler;
        private              EventHandler<MouseEvent>        mousePressHandler;
        private              EventHandler<MouseEvent>        mouseReleaseHandler;
        private              EventHandler<MouseEvent>        mouseExitHandler;

	    private              BooleanProperty                 hoverEnabled;
	    private              BooleanProperty                 selectionEnabled;
	    private              ObjectProperty<Country>         selectedCountry;
    	private              Country                         formerSelectedCountry;
	    private       		 Color       					 fillColor;
	    private       		 Color      					 strokeColor;
	    private       		 Color      					 hoverColor;
	    private      	 	 Color     						 pressedColor;
	    private       		 Color    					     selectedColor;
	
	    public boolean isHoverEnabled() { return hoverEnabled.get(); }
	    public void setHoverEnabled(final boolean ENABLED) { hoverEnabled.set(ENABLED); }
	    public BooleanProperty hoverEnabledProperty() { return hoverEnabled; }
	
	    public boolean isSelectionEnabled() { return selectionEnabled.get(); }
	    public void setSelectionEnabled(final boolean ENABLED) { selectionEnabled.set(ENABLED); }
	    public BooleanProperty selectionEnabledProperty() { return selectionEnabled; }

	    public Color getFillColor() { return fillColor; }
	    public void setFillColor(final Color _color) { fillColor = _color; }
//	    public ObjectProperty<Color> fillColorProperty() { return (ObjectProperty<Color>) fillColor; }

	    public Color getStrokeColor() { return strokeColor; }
	    public void setStrokeColor(final Color _color) { strokeColor = _color; }
//	    public ObjectProperty<Color> strokeColorProperty() { return (ObjectProperty<Color>) strokeColor; }

	    public Color getHoverColor() { return hoverColor; }
	    public void setHoverColor(final Color _color) { hoverColor = _color; }
//	    public ObjectProperty<Color> hoverColorProperty() { return (ObjectProperty<Color>) hoverColor; }

	    public Color getSelectedColor() { return selectedColor; }
	    public void setSelectedColor(final Color _color) { selectedColor = _color; }
//	    public ObjectProperty<Color> selectedColorProperty() { return (ObjectProperty<Color>) selectedColor; }

	    public Color getPressedColor() { return pressedColor; }
	    public void setPressedColor(final Color _color) { pressedColor = _color; }
//	    public ObjectProperty<Color> pressedColorProperty() { return (ObjectProperty<Color>) pressedColor; }

	    public Country getSelectedCountry() { return selectedCountry.get(); }
	    public void setSelectedCountry(final Country COUNTRY) { selectedCountry.set(COUNTRY); }
	    public ObjectProperty<Country> selectedCountryProperty() { return selectedCountry; }

	
	    private void handleMouseEvent(final MouseEvent EVENT, final EventHandler<MouseEvent> HANDLER) {
	        final CountryPath       COUNTRY_PATH = (CountryPath) EVENT.getSource();
	        final String            COUNTRY_NAME = COUNTRY_PATH.getName();
	        final Country           COUNTRY      = Country.valueOf(COUNTRY_NAME);
	        final List<CountryPath> PATHS        = countryPaths.get(COUNTRY_NAME);
	
	        final EventType TYPE = EVENT.getEventType();
	        if (MOUSE_ENTERED == TYPE) {
	            if (isHoverEnabled()) {
	            Color color = isSelectionEnabled() && COUNTRY.equals(getSelectedCountry()) ? getSelectedColor() : getHoverColor();
	            for (SVGPath path : PATHS) { path.setFill(color); }
	            }
	        } else if (MOUSE_PRESSED == TYPE) {
	            if (isSelectionEnabled()) {
	                Color color;
	                if (null == getSelectedCountry()) {
	                    setSelectedCountry(COUNTRY);
	                    color = getSelectedColor();
	                } else {
	                    color = countryColor.containsKey( getSelectedCountry() ) ? getFillColor() : countryColor.get( getSelectedCountry() );
	                }
	                for (SVGPath path : countryPaths.get(getSelectedCountry().iso2())) { path.setFill(color); }
	            } else {
	                if (isHoverEnabled()) {
	                for (SVGPath path : PATHS) { path.setFill(getPressedColor()); }
	            }
	            }
	        } else if (MOUSE_RELEASED == TYPE) {
	            Color color;
	            if (isSelectionEnabled()) {
	                if (formerSelectedCountry == COUNTRY) {
	                    setSelectedCountry(null);
	                    color = countryColor.containsKey( COUNTRY ) ? getFillColor() : countryColor.get( COUNTRY );
	                } else {
	                    setSelectedCountry(COUNTRY);
	                    color = getSelectedColor();
	                }
	                formerSelectedCountry = getSelectedCountry();
	            } else {
	                color = getHoverColor();
	            }
	            if (isHoverEnabled()) {
	            for (SVGPath path : PATHS) { path.setFill(color); }
	            }
	        } else if (MOUSE_EXITED == TYPE) {
	            if (isHoverEnabled()) {
	            Color color = isSelectionEnabled() && COUNTRY.equals(getSelectedCountry()) ? getSelectedColor() : getFillColor();
	            for (SVGPath path : PATHS) {
	                path.setFill(!countryColor.containsKey( COUNTRY ) || COUNTRY == getSelectedCountry() ? color : countryColor.get( COUNTRY ));
	            }
	        }
	        }
	
	        if (null != HANDLER) HANDLER.handle(EVENT);
	    }

//    }
    
	    
	    
	static class Css extends WorldGroup {
	    private static final StyleablePropertyFactory<WorldGroup.Css> FACTORY          = new StyleablePropertyFactory<>(Region.getClassCssMetaData());
	    
	    private static final CssMetaData<WorldGroup.Css, Color>       BACKGROUND_COLOR = FACTORY.createColorCssMetaData("-background-color", s -> s.backgroundColor, Color.web("#3f3f4f"), false);
	    private        final StyleableProperty<Color>        backgroundColor;
	    private static final CssMetaData<WorldGroup.Css, Color>       FILL_COLOR = FACTORY.createColorCssMetaData("-fill-color", s -> s.fillColor, Color.web("#d9d9dc"), false);
	    private        final StyleableProperty<Color>        fillColor;
	    private static final CssMetaData<WorldGroup.Css, Color>       STROKE_COLOR = FACTORY.createColorCssMetaData("-stroke-color", s -> s.strokeColor, Color.BLACK, false);
	    private        final StyleableProperty<Color>        strokeColor;
	    private static final CssMetaData<WorldGroup.Css, Color>       HOVER_COLOR = FACTORY.createColorCssMetaData("-hover-color", s -> s.hoverColor, Color.web("#456acf"), false);
	    private        final StyleableProperty<Color>        hoverColor;
	    private static final CssMetaData<WorldGroup.Css, Color>       PRESSED_COLOR = FACTORY.createColorCssMetaData("-pressed-color", s -> s.pressedColor, Color.web("#789dff"), false);
	    private        final StyleableProperty<Color>        pressedColor;
	    private static final CssMetaData<WorldGroup.Css, Color>       SELECTED_COLOR = FACTORY.createColorCssMetaData("-selected-color", s-> s.selectedColor, Color.web("#9dff78"), false);
	    private        final StyleableProperty<Color>        selectedColor;
//	    private static final CssMetaData<WorldGroup.Css, Color>       LOCATION_COLOR = FACTORY.createColorCssMetaData("-location-color", s -> s.locationColor, Color.web("#ff0000"), false);
//	    private        final StyleableProperty<Color>        locationColor;
	    
	    Css(final Resolution _resolution) {
	    	super(_resolution);
	    	backgroundColor      = new StyleableObjectProperty<Color>(BACKGROUND_COLOR.getInitialValue(WorldGroup.Css.this)) {
//	            @Override protected void invalidated() { setBackground(new Background(new BackgroundFill(get(), CornerRadii.EMPTY, Insets.EMPTY))); }
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "backgroundColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return BACKGROUND_COLOR; }
	        };
	        fillColor            = new StyleableObjectProperty<Color>(FILL_COLOR.getInitialValue(WorldGroup.Css.this)) {
//	            @Override protected void invalidated() { setFillAndStroke(); }
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "fillColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return FILL_COLOR; }
	        };
	        strokeColor          = new StyleableObjectProperty<Color>(STROKE_COLOR.getInitialValue(WorldGroup.Css.this)) {
//	            @Override protected void invalidated() { setFillAndStroke(); }
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "strokeColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return STROKE_COLOR; }
	        };
	        hoverColor           = new StyleableObjectProperty<Color>(HOVER_COLOR.getInitialValue(WorldGroup.Css.this)) {
	            @Override protected void invalidated() { }
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "hoverColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return HOVER_COLOR; }
	        };
	        pressedColor         = new StyleableObjectProperty<Color>(PRESSED_COLOR.getInitialValue(this)) {
	            @Override protected void invalidated() {}
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "pressedColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return PRESSED_COLOR; }
	        };
	        selectedColor        = new StyleableObjectProperty<Color>(SELECTED_COLOR.getInitialValue(this)) {
	            @Override protected void invalidated() {}
	            @Override public Object getBean() { return WorldGroup.Css.this; }
	            @Override public String getName() { return "selectedColor"; }
	            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SELECTED_COLOR; }
	        };
	        
	    }
	}
	    
	    
	    
	    
	    
}
