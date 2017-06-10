package pl.lodz.p.ics.weatherapp.api.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.WeatherApiInterface;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherApiAggregatorService implements WeatherApiInterface {

    private List<WeatherApiInterface> services = new ArrayList<>();

    public void addService(WeatherApiInterface service) {
        this.services.add(service);
    }

    @Override
    public WeatherApiData getData(Double latitude, Double longitude) {
        WeatherApiData aggregatedData = new WeatherApiData();
        Integer servicesCounter = 0;

        for (WeatherApiInterface service : this.services) {
            WeatherApiData data = null;
            try {
                data = service.getData(latitude, longitude);
            } catch (Exception e) {
                // We don't want users to know that something is not working on this level
                System.err.println("Service unavailable: " + service.toString());
                System.err.println(e.getMessage());
            }

            if (data == null) {
                continue;
            }

            aggregatedData = this.sumData(aggregatedData, data);
            servicesCounter++;
        }

        if (servicesCounter == 0) {
            return null;
        }

        return this.averageData(aggregatedData, servicesCounter);
    }

    private WeatherApiData sumData(WeatherApiData data1, WeatherApiData data2) {
        return new WeatherApiData(
                data1.getTemperatureMin() + data2.getTemperatureMin(),
                data1.getTemperatureMax() + data2.getTemperatureMax(),
                data1.getPressure() + data2.getPressure(),
                data1.getHumidity() + data2.getHumidity(),
                data1.getWindSpeed() + data2.getWindSpeed(),
                data1.getWindDirection() + data2.getWindDirection(),
                data1.getCloudsDensity() + data2.getCloudsDensity()
        );
    }

    private WeatherApiData averageData(WeatherApiData data, Integer divider) {
        return new WeatherApiData(
                data.getTemperatureMin() / divider.doubleValue(),
                data.getTemperatureMax() / divider.doubleValue(),
                data.getPressure() / divider.doubleValue(),
                data.getHumidity() / divider.doubleValue(),
                data.getWindSpeed() / divider.doubleValue(),
                data.getWindDirection() / divider.doubleValue(),
                data.getCloudsDensity() / divider.doubleValue()
        );
    }
}
