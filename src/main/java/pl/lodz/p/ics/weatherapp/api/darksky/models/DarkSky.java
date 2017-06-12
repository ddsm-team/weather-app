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
@JsonPropertyOrder({"latitude", "longitude", "timezone", "offset", "currently"})
public class DarkSky {

    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("currently")
    private Currently currently;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public DarkSky() {
    }

    public DarkSky(Double latitude, Double longitude, String timezone, Integer offset,
                   Currently currently) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.offset = offset;
        this.currently = currently;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonProperty("currently")
    public Currently getCurrently() {
        return currently;
    }

    @JsonProperty("currently")
    public void setCurrently(Currently currently) {
        this.currently = currently;
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
