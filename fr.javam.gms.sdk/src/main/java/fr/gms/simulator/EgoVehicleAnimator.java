package fr.gms.simulator;

import java.io.IOException;
import java.util.EventListener;
import java.util.Optional;
import java.util.function.BiConsumer;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.GisHistory;
import fr.gms.api.ego.EgoVehicle;
import fr.java.lang.enums.State;
import fr.java.lang.properties.ID;
import fr.java.patterns.stateable.StateListener;
import fr.java.player.Player;
import fr.java.player.PlayerListener;
import fr.java.sdk.player.players.ThreadedPlayer;
import fr.javafx.scene.control.player.PlayerControl;
import fr.javafx.stage.StageExt;

public class EgoVehicleAnimator<TYPE> implements Player<TYPE> {
//public class EgoVehicleAnimator<TYPE> extends PlayerAdapter<TYPE> {

	public static EgoVehicleAnimator<GeoCoordinate> createDefaultPlayer(final EgoVehicle _ego, BiConsumer<Long, GeoCoordinate> _onNext, boolean _withControl) {
	
		Player<GeoCoordinate>           player = new ThreadedPlayer<GeoCoordinate>(true, 1) {

			@Override
			protected Optional<GeoCoordinate> readNextFrame() throws IOException {
				if(_ego instanceof EgoVehicleModel.WithRailGuidedPlanner )
					return Optional.ofNullable( ((EgoVehicleModel.WithRailGuidedPlanner) _ego).getTrajectory().getGeometry(1) );
				return Optional.ofNullable( _ego.getPosition() );
			}
		};
		BiConsumer<Long, GeoCoordinate> onNext = (l, c) -> {
			if(_ego instanceof GisHistory) {
				System.out.println( c );

				((GisHistory) _ego).update( l, c, Double.NaN, null, null );
			}
			if(_onNext != null)
				_onNext.accept(l, c);
		};

		EgoVehicleAnimator<GeoCoordinate> animator = new EgoVehicleAnimator<GeoCoordinate>(player, onNext);

		if(_withControl)
			animator.showControl();

		return animator;
	}

	public static EgoVehicleAnimator<GeoCoordinate> createPathPlayer(final EgoVehicle _ego, final Gis.Path _path) {
		return createPathPlayer( _ego, _path, null, false);
	}
	public static EgoVehicleAnimator<GeoCoordinate> createPathPlayer(final EgoVehicle _ego, final Gis.Path _path, boolean _withControl) {
		return createPathPlayer( _ego, _path, null, _withControl);
	}
	public static EgoVehicleAnimator<GeoCoordinate> createPathPlayer(final EgoVehicle _ego, final Gis.Path _path, BiConsumer<Long, GeoCoordinate> _onNext) {
		return createPathPlayer( _ego, _path, _onNext, false);
	}
	public static EgoVehicleAnimator<GeoCoordinate> createPathPlayer(final EgoVehicle _ego, final Gis.Path _path, BiConsumer<Long, GeoCoordinate> _onNext, boolean _withControl) {
		Player<GeoCoordinate>           player = new ThreadedPlayer<GeoCoordinate>() {

			@Override
			protected Optional<GeoCoordinate> readNextFrame() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

		};
		BiConsumer<Long, GeoCoordinate> onNext = (l, c) -> {
			if(_ego instanceof GisHistory)
				((GisHistory) _ego).update( l, c, Double.NaN, null, null );

			if(_onNext != null)
				_onNext.accept(l, c);
		};

		EgoVehicleAnimator<GeoCoordinate> animator = new EgoVehicleAnimator<GeoCoordinate>(player, onNext);

		if(_withControl)
			animator.showControl();

		return animator;
	}

	Player<TYPE> player;
	StageExt 	 controller;
	long 		 timestamp   = 0,
				 delta_frame = (long)(1e3);

	EgoVehicleAnimator(final Player<TYPE> _player, final BiConsumer<Long, TYPE> _onNext) {
		this(_player, _onNext, (long) 1e3);
	}
	EgoVehicleAnimator(final Player<TYPE> _player, final BiConsumer<Long, TYPE> _onNext, long _delta_ms) {
		super();
		player      = _player;
		delta_frame = _delta_ms;

		registerListener((PlayerListener) (evt) -> {
			Optional<?> frame = evt.getFrame();

			if(!frame.isPresent())
				return ;

			if(_onNext != null)
				_onNext.accept(timestamp += delta_frame, (TYPE) frame.get());
/*
			switch(evt.getTypeEnum()) {
			case onStart:		timestamp = 0;
			case onNewFrame:	if(!frame.isPresent())
									return ;

								if(_onNext != null)
									_onNext.accept(timestamp += delta_frame, frame.get());

								break;
			case onStop:
			default:			break;
			}
*/
		});
	}

	public void 								showControl() {
		if(controller == null)
			controller = StageExt.create( new PlayerControl(this) );
		controller.show();
	}
	public void 								hideControl() {
		if(controller == null)
			return ;
		controller.hide();
	}

	@Override
	public void 								play() throws Exception {
		player.play();
	}
	@Override
	public void 								stop() throws Exception {
		player.stop();
	}

	@Override
	public ID 									getId() {
		return player.getId();
	}
	@Override
	public String 								getName() {
		return player.getName();
	}
	@Override
	public State 								getState() {
		return player.getState();
	}

	@Override
	public void 								setPlayRate(double _ratio) {
		player.setPlayRate(_ratio);
	}
	@Override
	public double 								getPlayRate() {
		return player.getPlayRate();
	}

	@Override
	public void 								setFramePerSecond(double _framePerSecond) {
		player.setFramePerSecond(_framePerSecond);
	}
	@Override
	public double 								getFramePerSecond() {
		return player.getFramePerSecond();
	}

	@Override
	public void 								registerListener(StateListener _listener) {
		player.registerListener(_listener);
	}
	@Override
	public void 								registerListener(PlayerListener _listener) {
		player.registerListener(_listener);
	}

	@Override
	public void 								unregisterListener(StateListener _listener) {
		player.unregisterListener(_listener);
	}
	@Override
	public void 								unregisterListener(PlayerListener _listener) {
		player.unregisterListener(_listener);
	}

	@Override
	public <T extends EventListener> T[] 		getListeners(Class<T> _class) {
		return player.getListeners(_class);
	}

}
