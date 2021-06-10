package eu.hansolo.fx.world.location;

import fr.geodesic.Location;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class LocationPin {
	final Location 						location;
	Node 								node;

    private EventHandler<MouseEvent> 	mouseEnterHandler;
    private EventHandler<MouseEvent> 	mousePressHandler;
    private EventHandler<MouseEvent> 	mouseReleaseHandler;
    private EventHandler<MouseEvent> 	mouseExitHandler;

	public LocationPin(Location _loc) {
		super();
		location = _loc;
		node     = null;
	}

	public Location 					getLocation() {
		return location;
	}

	public Node 						getGraphics() {
		return node;
	}
	public void 						setGraphics(Node _node) {
		node = _node;
	}

    public EventHandler<MouseEvent> 	getMouseEnterHandler() 											{ return mouseEnterHandler; }
    public void 						setMouseEnterHandler(final EventHandler<MouseEvent> _handler) 	{ mouseEnterHandler = _handler; }

    public EventHandler<MouseEvent> 	getMousePressHandler() 											{ return mousePressHandler; }
    public void 						setMousePressHandler(final EventHandler<MouseEvent> _handler) 	{ mousePressHandler = _handler; }

    public EventHandler<MouseEvent> 	getMouseReleaseHandler() 										{ return mouseReleaseHandler; }
    public void 						setMouseReleaseHandler(final EventHandler<MouseEvent> _handler) { mouseReleaseHandler = _handler;  }

    public EventHandler<MouseEvent> 	getMouseExitHandler() 											{ return mouseExitHandler; }
    public void 						setMouseExitHandler(final EventHandler<MouseEvent> _handler) 	{ mouseExitHandler = _handler; }

}
