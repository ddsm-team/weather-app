package pl.lodz.p.ics.weatherapp.api;

public class WeatherApiData {

    private Double temperatureMin;
    private Double temperatureMax;
    private Double pressure;
    private Double humidity;
    private Double windSpeed;
    private Double windDirection;
    private Double cloudsDensity;

    public WeatherApiData() {
        this.temperatureMin = 0.0;
        this.temperatureMax = 0.0;
        this.pressure = 0.0;
        this.humidity = 0.0;
        this.windSpeed = 0.0;
        this.windDirection = 0.0;
        this.cloudsDensity = 0.0;
    }

    public WeatherApiData(Double temperatureMin, Double temperatureMax, Double pressure,
                          Double humidity, Double windSpeed, Double windDirection,
                          Double cloudsDensity) {
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.cloudsDensity = cloudsDensity;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public Double getCloudsDensity() {
        return cloudsDensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeatherApiData that = (WeatherApiData) o;

        return (temperatureMin != null ? temperatureMin.equals(that.temperatureMin)
                : that.temperatureMin == null)
                && (temperatureMax != null ? temperatureMax.equals(that.temperatureMax)
                : that.temperatureMax == null)
                && (pressure != null ? pressure.equals(that.pressure)
                : that.pressure == null)
                && (humidity != null ? humidity.equals(that.humidity)
                : that.humidity == null)
                && (windSpeed != null ? windSpeed.equals(that.windSpeed)
                : that.windSpeed == null)
                && (windDirection != null ? windDirection.equals(that.windDirection)
                : that.windDirection == null)
                && (cloudsDensity != null ? cloudsDensity.equals(that.cloudsDensity)
                : that.cloudsDensity == null);
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
        return "WeatherApiData{"
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
