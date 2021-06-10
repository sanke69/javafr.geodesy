package fr.gis.opensky;

public class OpenSkyTest {

	public static void main(String[] args) {
		OpenSkyFactory.getInstance().setOnUpdate(planes -> {
			planes.forEach(plane -> {
										StringBuilder sb = new StringBuilder();
										sb.append("ID           : " + plane.getIcao24() + "[call_sign= " + plane.getCallsign() + ", squawk= " + plane.getSquawk() + " ]");
						//				sb.append("From         : " + plane.getOriginCountry() + "\n");
						//				sb.append("Position     : " + plane.getLatitude() + ", " + plane.getLongitude() + " [b.alt: " + plane.getBaroAltitude() + " m, g.alt: " + plane.getGeoAltitude() + " m]\n");
						
										System.out.println(sb.toString());
									});
		});

		while(true) {
			System.out.print(".");
			
			try { Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}
	}

}
