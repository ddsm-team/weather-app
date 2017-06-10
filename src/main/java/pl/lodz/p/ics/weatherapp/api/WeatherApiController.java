package pl.lodz.p.ics.weatherapp.api;

public abstract class WeatherApiController extends HttpRequestController
        implements Runnable {

    private Double latitude;
    private Double longitude;

    public WeatherApiController() { }

    public WeatherApiController(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
}
