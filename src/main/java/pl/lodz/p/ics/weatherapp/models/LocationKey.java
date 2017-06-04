package pl.lodz.p.ics.weatherapp.models;

import java.io.Serializable;

public class LocationKey implements Serializable {

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationKey that = (LocationKey) o;

        return (latitude != null ? latitude.equals(that.latitude) : that.latitude == null)
                && (longitude != null ? longitude.equals(that.longitude)
                : that.longitude == null);
    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationKey{"
                + "latitude=" + latitude
                + ", longitude=" + longitude
                + '}';
    }
}
