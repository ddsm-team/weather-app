package pl.lodz.p.ics.weatherapp.api.darksky.models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "time",
    "summary",
    "icon",
    "precipIntensity",
    "precipProbability",
    "precipType",
    "temperature",
    "apparentTemperature",
    "dewPoint",
    "humidity",
    "windSpeed",
    "windBearing",
    "visibility",
    "cloudCover",
    "pressure",
    "ozone",
    "precipAccumulation"
})
public class Datum {

    @JsonProperty("time")
    private Integer time;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("precipIntensity")
    private Double precipIntensity;
    @JsonProperty("precipProbability")
    private Double precipProbability;
    @JsonProperty("precipType")
    private String precipType;
    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("apparentTemperature")
    private Double apparentTemperature;
    @JsonProperty("dewPoint")
    private Double dewPoint;
    @JsonProperty("humidity")
    private Double humidity;
    @JsonProperty("windSpeed")
    private Double windSpeed;
    @JsonProperty("windBearing")
    private Integer windBearing;
    @JsonProperty("visibility")
    private Double visibility;
    @JsonProperty("cloudCover")
    private Double cloudCover;
    @JsonProperty("pressure")
    private Double pressure;
    @JsonProperty("ozone")
    private Double ozone;
    @JsonProperty("precipAccumulation")
    private Double precipAccumulation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Datum() {
    }

    public Datum(Integer time, String summary, String icon, Double precipIntensity,
                 Double precipProbability, String precipType, Double temperature,
                 Double apparentTemperature, Double dewPoint, Double humidity,
                 Double windSpeed, Integer windBearing, Double visibility,
                 Double cloudCover, Double pressure, Double ozone,
                 Double precipAccumulation) {
        super();
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windBearing = windBearing;
        this.visibility = visibility;
        this.cloudCover = cloudCover;
        this.pressure = pressure;
        this.ozone = ozone;
        this.precipAccumulation = precipAccumulation;
    }

    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("precipIntensity")
    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    @JsonProperty("precipIntensity")
    public void setPrecipIntensity(Double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    @JsonProperty("precipProbability")
    public Double getPrecipProbability() {
        return precipProbability;
    }

    @JsonProperty("precipProbability")
    public void setPrecipProbability(Double precipProbability) {
        this.precipProbability = precipProbability;
    }

    @JsonProperty("precipType")
    public String getPrecipType() {
        return precipType;
    }

    @JsonProperty("precipType")
    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    @JsonProperty("temperature")
    public Double getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("apparentTemperature")
    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    @JsonProperty("apparentTemperature")
    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    @JsonProperty("dewPoint")
    public Double getDewPoint() {
        return dewPoint;
    }

    @JsonProperty("dewPoint")
    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    @JsonProperty("humidity")
    public Double getHumidity() {
        return humidity;
    }

    @JsonProperty("humidity")
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("windSpeed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    @JsonProperty("windSpeed")
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @JsonProperty("windBearing")
    public Integer getWindBearing() {
        return windBearing;
    }

    @JsonProperty("windBearing")
    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    @JsonProperty("visibility")
    public Double getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("cloudCover")
    public Double getCloudCover() {
        return cloudCover;
    }

    @JsonProperty("cloudCover")
    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    @JsonProperty("pressure")
    public Double getPressure() {
        return pressure;
    }

    @JsonProperty("pressure")
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    @JsonProperty("ozone")
    public Double getOzone() {
        return ozone;
    }

    @JsonProperty("ozone")
    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    @JsonProperty("precipAccumulation")
    public Double getPrecipAccumulation() {
        return precipAccumulation;
    }

    @JsonProperty("precipAccumulation")
    public void setPrecipAccumulation(Double precipAccumulation) {
        this.precipAccumulation = precipAccumulation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
