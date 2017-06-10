package pl.lodz.p.ics.weatherapp.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@IdClass(LocationKey.class)
public class Location {

    @Id
    @Column(name = "latitude")
    private Double latitude;

    @Id
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "location")
    private List<LocationWeather> locationWeathers;

    public Location() { }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Location location = (Location) o;

        return (latitude != null ? latitude.equals(location.latitude)
                : location.latitude == null)
                && (longitude != null ? longitude.equals(location.longitude)
                : location.longitude == null);
    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Location{"
                + "name='" + name + '\''
                + '}';
    }
}
