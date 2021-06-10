package fr.gis.sdk.objects.gis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.geodesic.api.GeoCoordinate;
import fr.geodesic.api.referential.Datum;
import fr.gis.api.Gis;
import fr.gis.api.Gis.PathType;
import fr.gis.sdk.objects.GisObject;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;

public class GisPath extends GisObject implements Gis.Path {
	private List<GeoCoordinate>	geometry;
	private Datum 				datum;
	private PathType			type;

	public GisPath() {
		this(IDs.random(), PathType.Unknown, Datum.WGS84, Collections.emptyList());
	}
	public GisPath(Gis.Path _src) {
		this(_src.getId(), _src.getType(), _src.getDatum(), _src.getGeometry());
		System.err.println("GisPath:: WHY???");
	}
	public GisPath(GeoCoordinate... _nodes) {
		this(IDs.random(), PathType.Unknown, _nodes[0].getDatum(), _nodes);
	}
	public GisPath(ID _id, GeoCoordinate... _nodes) {
		this(_id, PathType.Unknown, _nodes[0].getDatum(), _nodes);
	}
	public GisPath(PathType _type, GeoCoordinate... _nodes) {
		this(IDs.random(), _type, _nodes[0].getDatum(), _nodes);
	}
	public GisPath(ID _id, PathType _type, GeoCoordinate... _nodes) {
		this(_id, _type, _nodes[0].getDatum(), _nodes);
	}

	public GisPath(List<GeoCoordinate> _nodes) {
		this(IDs.random(), PathType.Unknown, _nodes.get(0).getDatum(), _nodes);
	}
	public GisPath(ID _id, List<GeoCoordinate> _nodes) {
		this(_id, PathType.Unknown, _nodes.get(0).getDatum(), _nodes);
	}
	public GisPath(PathType _type, List<GeoCoordinate> _nodes) {
		this(IDs.random(), _type, _nodes.get(0).getDatum(), _nodes);
	}
	public GisPath(ID _id, PathType _type, List<GeoCoordinate> _nodes) {
		this(_id, _type, _nodes.get(0).getDatum(), _nodes);
	}

	public GisPath(Datum _datum, GeoCoordinate... _nodes) {
		this(IDs.random(), PathType.Unknown, _datum, _nodes);
	}
	public GisPath(PathType _type, Datum _datum, GeoCoordinate... _nodes) {
		this(IDs.random(), _type, _datum, _nodes);
	}

	public GisPath(Datum _datum, List<GeoCoordinate> _nodes) {
		this(IDs.random(), PathType.Unknown, _datum, _nodes);
	}
	public GisPath(PathType _type, Datum _datum, List<GeoCoordinate> _nodes) {
		this(IDs.random(), _type, _datum, _nodes);
	}

	public GisPath(ID _id, Datum _datum, GeoCoordinate... _nodes) {
		this(_id, PathType.Unknown, _datum, _nodes);
	}

	public GisPath(ID _id, Datum _datum, List<GeoCoordinate> _nodes) {
		this(_id, PathType.Unknown, _datum, _nodes);
	}

	public GisPath(ID _id, PathType _type, Datum _crs, GeoCoordinate... _nodes) {
		this(_id, PathType.Unknown, _nodes[0].getDatum(), Arrays.asList(_nodes));
	}
	public GisPath(ID _id, PathType _type, Datum _crs, List<GeoCoordinate> _nodes) {
		super(_id);
		type     = PathType.Unknown;
		datum    = _crs;
		geometry = _nodes;
//		geometry = new ArrayList<GeoCoordinate>(_nodes);
	}

	protected void				setType(PathType _type) {
		type = _type;
	}
	@Override
	public PathType 			getType() {
		return type;
	}

	protected void				setDatum(Datum _datum) {
		datum = _datum;
	}
	@Override
	public Datum 				getDatum() {
		return datum;
	}

	protected void				setGeometry(List<GeoCoordinate> _nodes) {
		geometry = _nodes;
	}
	public List<GeoCoordinate> 	getGeometry() {
		return geometry;
	}

}
