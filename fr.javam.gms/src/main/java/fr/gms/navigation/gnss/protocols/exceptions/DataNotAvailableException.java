package fr.gms.navigation.gnss.protocols.exceptions;

public class DataNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 3152226043626013354L;

	public DataNotAvailableException(String _mesg) {
		super(_mesg);
	}

}
