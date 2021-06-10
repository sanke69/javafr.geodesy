package fr.gms.api;

import fr.gis.api.GisLayer;

public interface GmsLayer {
	public static final GisLayer.Type PATHS           = StandardGmsLayerType.PATHS;
	public static final GisLayer.Type TRAJECTORIES    = StandardGmsLayerType.TRAJECTORIES;
	public static final GisLayer.Type EHORIZONS       = StandardGmsLayerType.EHORIZONS;
	public static final GisLayer.Type EGO_VEHICLE     = StandardGmsLayerType.EGO_VEHICLE;

	// ROAD
	public static final GisLayer.Type MM_PATHS        = StandardGmsLayerType.MM_PATHS;
	public static final GisLayer.Type MM_TRAJECTORIES = StandardGmsLayerType.MM_TRAJECTORIES;
}
