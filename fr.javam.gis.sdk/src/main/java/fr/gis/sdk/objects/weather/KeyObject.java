package fr.gis.sdk.objects.weather;

public class KeyObject {
    
    int zoom;
    double startLongitude;
    double startLatitude;
    double endLongitude;
    double endLatitude;

    public KeyObject(int zoom, double startLongitude, double startLatitude, double endLongitude, double endLatitude) {
        this.zoom = zoom;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.zoom;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.startLongitude) ^ (Double.doubleToLongBits(this.startLongitude) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.startLatitude) ^ (Double.doubleToLongBits(this.startLatitude) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.endLongitude) ^ (Double.doubleToLongBits(this.endLongitude) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.endLatitude) ^ (Double.doubleToLongBits(this.endLatitude) >>> 32));
        return hash;
    }

    public int getZoom() {
        return zoom;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyObject other = (KeyObject) obj;
        if (this.zoom != other.zoom) {
            return false;
        }
        if (Double.doubleToLongBits(this.startLongitude) != Double.doubleToLongBits(other.startLongitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.startLatitude) != Double.doubleToLongBits(other.startLatitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.endLongitude) != Double.doubleToLongBits(other.endLongitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.endLatitude) != Double.doubleToLongBits(other.endLatitude)) {
            return false;
        }
        return true;
    }
    
}
