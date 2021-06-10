package fr.gis.openweather;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import fr.gis.sdk.objects.weather.CurrentWeatherData;
import fr.gis.sdk.objects.weather.CurrentWeatherDataObject;
import fr.gis.sdk.objects.weather.KeyObject;

public class WeatherDataFactory {

    private static final int POOL_SIZE = 5;
    private static final int MISSING_TRESHOLD = 10;
    private static final int LOADING_THREAD_SLEEP_INTERVAL = 100;
    private ExecutorService executorService;
    private WeatherFactory weatherfactory;

    private Map<Integer, Map<KeyObject, CurrentWeatherDataObject>> weatherDataObjectForZoomCache;
    private Map<Integer, Map<Point2D, CurrentWeatherData>> dataCoordinateForZoomCache;
    
    private Map<KeyObject, CurrentWeatherDataObject> currentWeatherDataObjectCache;
    private Map<Point2D, CurrentWeatherData> currentDataCoordinationCache;
    
    private BlockingQueue<KeyObject> loadingQueue;
    private final Thread loadingThread;
    private int currentZoom = -1;
    private static final WeatherDataFactory instance = new WeatherDataFactory();

    private WeatherDataFactory() {
        weatherDataObjectForZoomCache = new HashMap<>();
        dataCoordinateForZoomCache = new HashMap<>();
        currentWeatherDataObjectCache = new HashMap<>();
        currentDataCoordinationCache = new HashMap<>();
        loadingQueue = new LinkedBlockingQueue<>();
        weatherfactory = new WeatherFactory();

        executorService = Executors.newFixedThreadPool(POOL_SIZE, new ThreadFactory() {
            private int count = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "tile-pool-" + count++);
                t.setPriority(Thread.MIN_PRIORITY);
                t.setDaemon(true);
                return t;
            }
        });

        loadingThread = new Thread(new Runnable() {
            int emptyQueueCounter = 0;

            @Override
            public void run() {
                while (true) {
                    KeyObject head = loadingQueue.poll();
                    if (head == null) {
                        try {
                            emptyQueueCounter++;
                            if (emptyQueueCounter > 4) {
                                emptyQueueCounter = 0;
                                synchronized (loadingThread) {
                                    loadingThread.wait();
                                }
                            } else {
                                Thread.sleep(LOADING_THREAD_SLEEP_INTERVAL);
                            }
                        } catch (InterruptedException ex) {
                            //noop
                        }
                    } else {
                        CurrentWeatherDataObject weatherObjectData = currentWeatherDataObjectCache.get(head);
                        load(head, weatherObjectData);
                    }
                }
            }
        });
    }

    public CurrentWeatherDataObject getCurrentWeatherDataForArea(double startLongitude, double startLatitude, double endLongitude, double endLatitude, int zoom) {

        if (this.currentZoom != zoom) {
            this.currentZoom = zoom;
            
            currentWeatherDataObjectCache = weatherDataObjectForZoomCache.get(zoom);
            if (currentWeatherDataObjectCache == null) {
                currentWeatherDataObjectCache = new HashMap<>();
                weatherDataObjectForZoomCache.put(zoom, currentWeatherDataObjectCache);
            }
            
            currentDataCoordinationCache = dataCoordinateForZoomCache.get(zoom);
            if (currentDataCoordinationCache == null) {
                currentDataCoordinationCache = new HashMap<>();
                dataCoordinateForZoomCache.put(zoom, currentDataCoordinationCache);
            }
            
            loadingQueue.clear();
        }

        if (!loadingThread.isAlive()) {
            loadingThread.start();
        } else {
            synchronized (loadingThread) {
                loadingThread.notify();
            }
        }
        List<CurrentWeatherData> dataFromCache = findInCoordCache(startLongitude, startLatitude, endLongitude, endLatitude, zoom);
        if (!dataFromCache.isEmpty()) {
            CurrentWeatherDataObject weatherDataObject = new CurrentWeatherDataObject();
            weatherDataObject.setData(dataFromCache);
            weatherDataObject.setLoaded();
            return weatherDataObject;
        }

        KeyObject keyObject = new KeyObject(zoom, startLongitude, startLatitude, endLongitude, endLatitude);
        CurrentWeatherDataObject weatherDataObject = currentWeatherDataObjectCache.get(keyObject);
        if (weatherDataObject != null && weatherDataObject.isOutdated()) {
            currentWeatherDataObjectCache.remove(keyObject);
            weatherDataObject = null;
        }

        if (weatherDataObject == null) {
            weatherDataObject = new CurrentWeatherDataObject();
            currentWeatherDataObjectCache.put(keyObject, weatherDataObject);
            startLoading(keyObject);
        } else {
            weatherDataObject.setLoaded();
        }
        return weatherDataObject;
    }

    private void startLoading(KeyObject keyObject) {
        if (!loadingQueue.contains(keyObject)) {
            loadingQueue.add(keyObject);
            if (loadingQueue.size() > 2) {
                loadingQueue.poll();
            }
        }
    }

    private void load(final KeyObject head, final CurrentWeatherDataObject weatherDataObject) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<CurrentWeatherData> data = weatherfactory.getCurrentWeatherDataForArea(head.getStartLongitude(), head.getStartLatitude(), head.getEndLongitude(), head.getEndLatitude(), head.getZoom());

                if (data != null && !data.isEmpty()) {
                    for (CurrentWeatherData weatherData : data) {
                        double latitude = weatherData.getLatitude();
                        double longitude = weatherData.getLongitude();
                        Point2D coordPoint = new Point.Double(latitude, longitude);
                        currentDataCoordinationCache.put(coordPoint, weatherData);
                    }
                } else {
                    double centerLongitude = head.getStartLongitude() + (head.getEndLongitude() - head.getStartLongitude()) / 2;
                    double centerLatitude = head.getStartLatitude() + (head.getEndLatitude() - head.getStartLatitude()) / 2;
                    data = weatherfactory.readCitiesData(centerLatitude, centerLongitude);
                }
                weatherDataObject.setData(data);
            }
        });
    }

    private List<CurrentWeatherData> findInCoordCache(double startLongitude, double startLatitude, double endLongitude, double endLatitude, int zoom) {
        Rectangle2D rectangle = new Rectangle.Double(Math.min(startLongitude, endLongitude), Math.min(startLatitude, endLatitude), Math.abs(startLongitude - endLongitude), Math.abs(startLatitude - endLatitude));
        List<CurrentWeatherData> foundInCache = new ArrayList<>();

        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;

        List<Point2D> outDatedCoords = new ArrayList<>();

        for (Point2D coord : currentDataCoordinationCache.keySet()) {
            CurrentWeatherData dataObject = currentDataCoordinationCache.get(coord);
            if (!dataObject.isOutdated()) {
                if (rectangle.contains(coord)) {
                    foundInCache.add(dataObject);
                    if (coord.getX() > maxLatitude) {
                        maxLatitude = coord.getX();
                    }

                    if (coord.getX() < minLatitude) {
                        minLatitude = coord.getX();
                    }

                    if (coord.getY() > maxLongitude) {
                        maxLongitude = coord.getY();
                    }

                    if (coord.getY() < minLongitude) {
                        minLongitude = coord.getY();
                    }
                }
            } else {
                outDatedCoords.add(coord);
            }
        }

        for (Point2D coord : outDatedCoords) {
            currentDataCoordinationCache.remove(coord);
        }

        if (!foundInCache.isEmpty()) {
            Rectangle2D coveredRectangle = new Rectangle.Double(minLatitude, minLongitude, maxLatitude - minLatitude, maxLongitude - minLongitude);

            double bottom = 100 * (coveredRectangle.getX() - rectangle.getX()) / rectangle.getWidth();
            double left = 100 * (coveredRectangle.getY() - rectangle.getY()) / rectangle.getHeight();

            double top = 100 * ((rectangle.getWidth() - ((coveredRectangle.getX() - rectangle.getX()) + coveredRectangle.getWidth()))) / rectangle.getWidth();
            double right = 100 * ((rectangle.getHeight() - ((coveredRectangle.getY() - rectangle.getY()) + coveredRectangle.getHeight()))) / rectangle.getHeight();

            if (right > MISSING_TRESHOLD) {
                Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX(), coveredRectangle.getY() + coveredRectangle.getHeight(), rectangle.getWidth(), (rectangle.getY() + rectangle.getHeight()) - (coveredRectangle.getY() + coveredRectangle.getHeight()));
                addToCache(loadRectangle, zoom);
            }

            if (left > MISSING_TRESHOLD) {
                Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), coveredRectangle.getY() - rectangle.getY());
                addToCache(loadRectangle, zoom);
            }

            if (bottom > MISSING_TRESHOLD) {
//                 Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX(), rectangle.getY(), coveredRectangle.getX() - rectangle.getX(), rectangle.getHeight());
                Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX(), coveredRectangle.getY(), coveredRectangle.getX() - rectangle.getX(), coveredRectangle.getHeight());
                addToCache(loadRectangle, zoom);
            }

            if (top > MISSING_TRESHOLD) {
//                 Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX() + coveredRectangle.getWidth(), rectangle.getY(), rectangle.getWidth() - (coveredRectangle.getWidth() + (coveredRectangle.getY() - rectangle.getY())), rectangle.getHeight());
                Rectangle2D loadRectangle = new Rectangle.Double(rectangle.getX() + coveredRectangle.getWidth(), coveredRectangle.getY(), rectangle.getWidth() - (coveredRectangle.getWidth() + (coveredRectangle.getY() - rectangle.getY())), coveredRectangle.getHeight());
                addToCache(loadRectangle, zoom);
            }
        }
        return foundInCache;
    }

    private void addToCache(Rectangle2D loadRectangle, int zoom) {
        KeyObject keyObject = new KeyObject(zoom, loadRectangle.getX(), loadRectangle.getY(), loadRectangle.getX() + loadRectangle.getWidth(), loadRectangle.getY() + loadRectangle.getHeight());
        CurrentWeatherDataObject weatherDataObject = new CurrentWeatherDataObject();
        currentWeatherDataObjectCache.put(keyObject, weatherDataObject);
        startLoading(keyObject);
    }

    public static WeatherDataFactory getInstance() {
        return instance;
    }
}
