package pl.lodz.p.ics.weatherapp.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class WeatherApiController implements Runnable {

    private Double latitude;
    private Double longitude;

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

    public String sendRequest() throws IOException {
        HttpRequestBase request = getRequestObject();
        StringBuilder jsonResponseBuilder = new StringBuilder();

        Reader in = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            in = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
            BufferedReader rd = new BufferedReader(in);

            String line;
            while ((line = rd.readLine()) != null) {
                jsonResponseBuilder.append(line);
                jsonResponseBuilder.append("\n");
            }

            return jsonResponseBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return jsonResponseBuilder.toString();
    }

    public URI buildUri(String url, Map<String, String> params)
            throws URISyntaxException {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(url);
        if (params.size() > 0) {
            uriBuilder.append("?");
            for (Map.Entry<String, String> param : params.entrySet()) {
                uriBuilder.append(param.getKey());
                uriBuilder.append("=");
                uriBuilder.append(param.getValue());
                uriBuilder.append("&");
            }
            uriBuilder.deleteCharAt(uriBuilder.lastIndexOf("&"));
        }
        return new URI(uriBuilder.toString());
    }

    protected abstract HttpRequestBase getRequestObject();
}
