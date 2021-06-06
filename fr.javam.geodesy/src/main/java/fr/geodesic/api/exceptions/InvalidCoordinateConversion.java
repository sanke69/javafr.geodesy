package fr.geodesic.api.exceptions;

public class InvalidCoordinateConversion extends RuntimeException {
	private static final long serialVersionUID = 3771572163986620874L;

	public InvalidCoordinateConversion() {
		super();
	}

	public InvalidCoordinateConversion(String _msg) {
		super(_msg);
	}

}
