package fr.gms.navigation.gnss.properties.values;

public class SatelliteInfo {

	private String id;
	private int elevation;
	private int azimuth;
	private int noise;

	public SatelliteInfo(String _id, int _elevation, int _azimuth, int _noise) {
		id        = _id;
		elevation = _elevation;
		azimuth   = _azimuth;
		noise     = _noise;
	}

	public int getAzimuth() {
		return azimuth;
	}

	public int getElevation() {
		return elevation;
	}

	public String getId() {
		return id;
	}

	public int getNoise() {
		return noise;
	}

	public void setAzimuth(int azimuth) {
		if (azimuth < 0 || azimuth > 360) {
			throw new IllegalArgumentException("Value out of bounds 0..360 deg");
		}
		this.azimuth = azimuth;
	}

	public void setElevation(int elevation) {
		if (elevation < 0 || elevation > 90) {
			throw new IllegalArgumentException("Value out of bounds 0..90 deg");
		}
		this.elevation = elevation;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNoise(int noise) {
		if (noise < 0 || noise > 99) {
			throw new IllegalArgumentException("Value out of bounds 0..99 dB");
		}
		this.noise = noise;
	}

}
