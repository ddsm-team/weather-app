package pl.lodz.p.ics.weatherapp.api.darksky.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.WeatherApiInterface;
import pl.lodz.p.ics.weatherapp.api.darksky.controllers.DarkSkyApiController;
import pl.lodz.p.ics.weatherapp.api.darksky.models.DarkSky;

@Service
public class DarkSkyApiService implements WeatherApiInterface {

    private DarkSkyApiController controller;

    @Override
    public WeatherApiData getData(Double latitude, Double longitude) {
        DarkSky darkSky = controller.getDarkSky();

        return new WeatherApiData(
                (darkSky.getCurrently().getTemperature() - 32.0) / 1.8,
                (darkSky.getCurrently().getTemperature() - 32.0) / 1.8,
                darkSky.getCurrently().getPressure(),
                darkSky.getCurrently().getHumidity(),
                darkSky.getCurrently().getWindSpeed() * 1.609344,
                darkSky.getCurrently().getWindBearing().doubleValue(),
                darkSky.getCurrently().getCloudCover()
        );
    }

    public DarkSkyApiController getController() {
        return controller;
    }

    public void setController(DarkSkyApiController controller) {
        this.controller = controller;
    }
}
