package pl.lodz.p.ics.weatherapp.api;

public interface WeatherApiProviderInterface {

    double getTemperatureMin();

    double getTemperatureMax();

    double getPressure();

    double getHumidity();

    double getWindSpeed();

    double getWindDirection();

    double getCloudsDensity();

    double getRainVolume();

    double getSnowVolume();
}
