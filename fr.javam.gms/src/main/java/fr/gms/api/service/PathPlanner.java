package fr.gms.api.service;

import fr.gis.api.Gis;
import fr.gms.api.ego.EgoVehicle;

public interface PathPlanner {

	public Gis.Path	compute(EgoVehicle _ego);

}
