package fr.gis.sdk.objects.gis;

import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.gis.api.Gis;
import fr.gis.sdk.objects.core.GisArea;
import fr.java.lang.properties.ID;

public class GisBuilding extends GisArea implements Gis.Building {
	float elevation;

	public GisBuilding(ID _id, List<GeoCoordinate> _shape) {
		super(_id, _shape);
	}
	public GisBuilding(ID _id, List<GeoCoordinate> _shape, float _elevation) {
		super(_id, _shape);
		elevation = _elevation;
	}

	public static class Builder {
		ID 					id;
		List<GeoCoordinate> geometry;
		float 			 	elevation;

		public Builder(ID _id) {
			super();
			id = _id;
			elevation = Float.NaN;
		}

		public Builder setGeometry(List<GeoCoordinate> _geometry) {
			geometry = _geometry;
			return this;
		}
		public Builder setElevation(float _elevation) {
			elevation = _elevation > 0 ? _elevation : Float.NaN;
			return this;
		}

		public GisBuilding build() {
			GisBuilding elt = new GisBuilding(id, geometry);
			elt.elevation = elevation != Float.NaN ? elevation : -1.0f;

			return elt;
		}

	}

}
