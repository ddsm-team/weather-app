package pl.lodz.p.ics.weatherapp.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "location_weather", schema = "wdaa")
public class LocationWeather {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "location_lat", referencedColumnName = "latitude"),
            @JoinColumn(name = "location_lon", referencedColumnName = "longitude")
    })
    private Location location;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Weather weather;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public LocationWeather() { }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Timestamp getTimestamp() {
        return new Timestamp(timestamp.getTime());
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = new Timestamp(timestamp.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationWeather that = (LocationWeather) o;

        return (location != null ? location.equals(that.location) : that.location == null)
                && (weather != null ? weather.equals(that.weather) : that.weather == null)
                && (timestamp != null ? timestamp.equals(that.timestamp)
                : that.timestamp == null);
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationWeather{"
                + "location=" + location
                + ", weather=" + weather
                + ", timestamp=" + timestamp
                + '}';
    }
}
