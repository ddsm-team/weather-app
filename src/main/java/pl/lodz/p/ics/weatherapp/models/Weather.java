package pl.lodz.p.ics.weatherapp.models;

import javax.persistence.*;

@Entity
@Table(name = "weather", schema = "wdaa")
public class Weather {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature_min")
    private Double temperatureMin;

    @Column(name = "temperature_max")
    private Double temperatureMax;

    @Column(name = "pressure")
    private Double pressure;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    private Double windDirection;

    @Column(name = "clouds_density")
    private Double cloudsDensity;

    public Weather() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Double windDirection) {
        this.windDirection = windDirection;
    }

    public Double getCloudsDensity() {
        return cloudsDensity;
    }

    public void setCloudsDensity(Double cloudsDensity) {
        this.cloudsDensity = cloudsDensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Weather weather = (Weather) o;

        return (temperatureMin != null ? temperatureMin.equals(weather.temperatureMin)
                : weather.temperatureMin == null)
                && (temperatureMax != null ? temperatureMax.equals(weather.temperatureMax)
                : weather.temperatureMax == null)
                && (pressure != null ? pressure.equals(weather.pressure)
                : weather.pressure == null)
                && (humidity != null ? humidity.equals(weather.humidity)
                : weather.humidity == null)
                && (windSpeed != null ? windSpeed.equals(weather.windSpeed)
                : weather.windSpeed == null)
                && (windDirection != null ? windDirection.equals(weather.windDirection)
                : weather.windDirection == null)
                && (cloudsDensity != null ? cloudsDensity.equals(weather.cloudsDensity)
                : weather.cloudsDensity == null);
    }

    @Override
    public int hashCode() {
        int result = temperatureMin != null ? temperatureMin.hashCode() : 0;
        result = 31 * result + (temperatureMax != null ? temperatureMax.hashCode() : 0);
        result = 31 * result + (pressure != null ? pressure.hashCode() : 0);
        result = 31 * result + (humidity != null ? humidity.hashCode() : 0);
        result = 31 * result + (windSpeed != null ? windSpeed.hashCode() : 0);
        result = 31 * result + (windDirection != null ? windDirection.hashCode() : 0);
        result = 31 * result + (cloudsDensity != null ? cloudsDensity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Weather{"
                + "temperatureMin=" + temperatureMin
                + ", temperatureMax=" + temperatureMax
                + ", pressure=" + pressure
                + ", humidity=" + humidity
                + ", windSpeed=" + windSpeed
                + ", windDirection=" + windDirection
                + ", cloudsDensity=" + cloudsDensity
                + '}';
    }
}
