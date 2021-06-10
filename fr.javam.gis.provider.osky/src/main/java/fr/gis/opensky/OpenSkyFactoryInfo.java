package fr.gis.opensky;

public class OpenSkyFactoryInfo {

	/** credentials to test authenticated API calls **/
	final String    				USERNAME;
	final String    				PASSWORD;
	final Integer[] 				SERIALS;

    public OpenSkyFactoryInfo() {
    	this(null, null);
    }
    public OpenSkyFactoryInfo(String _user, String _pswd, Integer... _serials) {
    	super();
    	USERNAME = _user;
    	PASSWORD = _pswd;
    	SERIALS  = _serials;
    }

    public boolean isRegistered() {
    	return USERNAME != null && PASSWORD != null;
    }
}
