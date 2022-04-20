package ru.kpfu.stud.garipov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.stud.garipov.aspect.Loggable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

@RestController
public class WeatherController {

    @Operation(summary = "Returns weather in city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather in city",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/weather")
    @Loggable
    public String weather(@RequestParam Optional<String> city) {
        return getWeather(city);
    }

    private String getWeather(Optional<String> city) {
        StringBuilder content = new StringBuilder();
        try {
            URL getUrl = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&" +
                    "appid=50da205a9c76cfaf41a554bc57768910", city.orElse("Kazan")));
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            )) {
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
