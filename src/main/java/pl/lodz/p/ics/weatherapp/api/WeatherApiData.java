package pl.lodz.p.ics.weatherapp.api;

public class WeatherApiData {

    private Double temperatureMin;
    private Double temperatureMax;
    private Double pressure;
    private Double humidity;
    private Double windSpeed;
    private Double windDirection;
    private Double cloudsDensity;

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
}
