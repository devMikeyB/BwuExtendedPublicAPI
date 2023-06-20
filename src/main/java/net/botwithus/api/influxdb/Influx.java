package net.botwithus.api.influxdb;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class Influx {
    private String endpoint = "https://3ef5lt32b9.execute-api.us-east-1.amazonaws.com/default/AbyssInfluxDB";
    private HttpURLConnection http = null;
    private URI uri = null;

    public Influx(){
        initialize();
    }

    private void initialize() {
        try {
            uri = new URI(endpoint);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void post(String jsonPayload) {
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(uri).
                    POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .header("Content-type", "application/json").
                    build();
            var response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.out.println("Influx Data Response: " + response.body());
        } catch (Exception e) {
            System.out.println(e.getMessage() + " | " + Arrays.toString(e.getStackTrace()));
        }
    }
}
