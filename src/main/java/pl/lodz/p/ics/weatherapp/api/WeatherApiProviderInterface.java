package pl.lodz.p.ics.weatherapp.api;

public interface WeatherApiProviderInterface {

    Double getTemperatureMin();

    Double getTemperatureMax();

    Double getPressure();

    Double getHumidity();

    Double getWindSpeed();

    Double getWindDirection();

    Double getCloudsDensity();

    Double getRainVolume();

    Double getSnowVolume();
}
