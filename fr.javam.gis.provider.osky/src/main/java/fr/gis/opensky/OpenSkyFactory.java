package fr.gis.opensky;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;

import fr.geodesic.utils.GeoCoordinates;
import fr.gis.opensky.objects.MapPlane;
import fr.gis.opensky.objects.MapPlaneInfo;
import fr.java.math.geometry.BoundingBox;

public class OpenSkyFactory {
    private static final int AUTHENTICATE_PERIODS_MS   =  5000;
    private static final int UNAUTHENTICATE_PERIODS_MS = 10500;

	private static OpenSkyFactory instance;
	public static OpenSkyFactory getInstance() {
		if(instance == null) 
			instance = new OpenSkyFactory(new OpenSkyFactoryInfo());

		return instance;
	}
	public static OpenSkyFactory getInstance(String _username, String _password) {
		if(instance != null) 
			instance = new OpenSkyFactory(new OpenSkyFactoryInfo(_username, _password));

		return instance;
	}
	
	private OpenSkyFactoryInfo			infos;
	private OpenSkyApi 					api;

	Consumer<Collection<MapPlaneInfo>>  onUpdate;
    private Thread						updateThread;
    private String[] 					updateIcaos = null;
    private BoundingBox.TwoDims 		updateArea  = null;

	private OpenSkyFactory(OpenSkyFactoryInfo _info) {
		super();
		infos = _info;
		api   = api();
	}

	public Collection<MapPlaneInfo> requestStateVectors() {
		try {
			return processPlaneStates( api().getStates(0, null) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}
	public Collection<MapPlaneInfo> requestStateVector(String... _icaos) {
		try {
			return processPlaneStates( api().getStates(0, _icaos) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}
	public Collection<MapPlaneInfo> requestStateVectors(BoundingBox.TwoDims _region) {
		try {
			return processPlaneStates( api().getStates(0, null, new OpenSkyApi.BoundingBox(_region.getMinY(), _region.getMaxY(), _region.getMinX(), _region.getMaxX())) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}
	public Collection<MapPlaneInfo> requestStateVectors(long _timestamp) {
		try {
			return processPlaneStates( api().getStates((int) (_timestamp / 1_000), null) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}
	public Collection<MapPlaneInfo> requestStateVectors(long _timestamp, String... _icaos) {
		try {
			return processPlaneStates( api().getStates((int) (_timestamp / 1_000), _icaos) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}
	public Collection<MapPlaneInfo> requestStateVectors(long _timestamp, BoundingBox.TwoDims _region) {
		try {
			return processPlaneStates( api().getStates((int) (_timestamp / 1_000), null, new OpenSkyApi.BoundingBox(_region.getMinY(), _region.getMaxY(), _region.getMinX(), _region.getMaxX())) );

		} catch (IOException e) { e.printStackTrace(); }

		return Collections.emptyList();
	}

	public void 					setOnUpdate(Consumer<Collection<MapPlaneInfo>> _onUpdate) {
		onUpdate = _onUpdate;

		if(updateThread == null)
			initializeUpdater();
	}
	public void 					setUpdateArea(BoundingBox.TwoDims _region) {
		updateArea = _region;
	}
	public void 					setUpdateIcaos(String... _icaos) {
		updateIcaos = _icaos;
	}

    private OpenSkyApi 				api() {
//  	if(api != null)
//  		throw new IllegalAccessError();

		if (infos.USERNAME == null || infos.PASSWORD == null) {
			api = new OpenSkyApi();
		} else {
			api = new OpenSkyApi(infos.USERNAME, infos.PASSWORD);
		}

		return api;
    }
    private Thread 					initializeUpdater() {
    	final int REFRESH_PERIOD = infos.isRegistered() ? AUTHENTICATE_PERIODS_MS : UNAUTHENTICATE_PERIODS_MS;

    	updateThread = new Thread(() -> {
    		while(!Thread.currentThread().isInterrupted()) {
    			System.out.println(Instant.now());

    			Collection<MapPlaneInfo> mpis;

				if(updateArea != null)
					mpis = requestStateVectors(updateArea);
				else
					mpis = requestStateVectors();


				if(onUpdate != null)
					onUpdate.accept( mpis );

    			try { Thread.sleep(REFRESH_PERIOD); } catch (InterruptedException e) { }
    		}
    	});
    	updateThread.start();

    	return updateThread;
    }

    private Collection<MapPlaneInfo> processPlaneStates(OpenSkyStates _os) {
    	if(_os == null)
    		return Collections.emptyList();

    	Collection<MapPlaneInfo> newMap = new ArrayList<MapPlaneInfo>();

    	_os.getStates() .stream()
						.forEach(s -> {
							MapPlane plane = new MapPlane( s.getIcao24() );
							plane.setCallsign			( s.getCallsign() );
							plane.setSquawk				( s.getSquawk() );

							plane.setOriginCountry		( s.getOriginCountry() );

							plane.setOnGround			( s.isOnGround() );
							plane.setPosition			( GeoCoordinates.newWGS84(s.getLongitude() != null ? s.getLongitude() : Double.NaN, s.getLatitude() != null ? s.getLatitude() : Double.NaN) );
							plane.setGeoAltitude		( s.getGeoAltitude() != null ? s.getGeoAltitude() : Double.NaN );
							plane.setBaroAltitude		( s.getBaroAltitude() != null ? s.getBaroAltitude() : Double.NaN );

							plane.setHeading			( s.getHeading() != null ? s.getHeading() : Double.NaN );
							plane.setVelocity			( s.getVelocity() != null ? s.getVelocity() : Double.NaN );
							plane.setVerticalRate		( s.getVerticalRate() != null ? s.getVerticalRate() : Double.NaN );

							plane.setLastContact		( s.getLastContact() * 1000L  );
							plane.setLastPositionUpdate	( s.getLastPositionUpdate() != null ? s.getLastPositionUpdate() * 1000L : Double.NaN );

							newMap.add(plane);
						});

    	return newMap;
    }
   
}
